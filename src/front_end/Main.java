package front_end;

import antlr.WACCLexer;
import antlr.WACCParser;
import back_end.CodeGen;
import back_end.operands.registers.RegisterManager;
import extension.InstructionEvaluation;
import front_end.AST.ASTNode;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {

  public final static int NO_OPTION = 0;      // Run the full compile pipeline
  public final static int TOKENIZE = 1;       // Convert the input file into a token stream
  public final static int PARSE = 2;          // Create the parse tree and perform syntactic check
  public final static int SEMANTIC_CHECK = 3; // Perform semantic check and build AST
  public final static int ASSEMBLE = 4;       // Generate assembly code for the program
  public final static int RUN = 5;            // Run the generated program using qemu
  public static final boolean WARNINGS_DISABLED = true;

  public static int EXIT_CODE = 0;

  public static void main(String[] args) throws IOException {

    String filename = "";
    int option = 0;

    if (args.length > 2) {
      System.out.println("Invalid input arguments :(");
    } else if (args.length == 0) {
      System.out.println("Error! Please provide an input file to compile :)");
    } else if (args.length == 1) {
      filename = args[0];
    } else {
      filename = args[1];
      switch (args[0]) {
        case "-t":
          option = TOKENIZE;
          break;
        case "-p":
          option = PARSE;
          break;
        case "-s":
          option = SEMANTIC_CHECK;
          break;
        case "-a":
          option = ASSEMBLE;
          break;
        case "-x":
          option = RUN;
          break;
        default:
          throw new IllegalArgumentException(
              "Error! Invalid first argument :( Please provide one of the \"-t\" \"-p\" \"s\"");
      }
    }

    // create a CharStream that reads from the input file FILENAME
    CharStream inputStream;
    try {
      inputStream = CharStreams.fromFileName(filename);
    } catch (IOException e) {
      throw new IllegalArgumentException("Error! Input file could not be found :( -> " + filename);
    }

    // create a lexer that feeds off of input CharStream
    WACCLexer lexer = new WACCLexer(inputStream);
    lexer.addErrorListener(new CustomErrorHandler());

    // create a buffer of tokens pulled from the lexer
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    // create a parser that feeds off the tokens buffer
    WACCParser parser = new WACCParser(tokens);
    parser.addErrorListener(new CustomErrorHandler());

    // Vocabulary for interpreting symbol types
    Vocabulary vocabulary = parser.getVocabulary();

    if (option == TOKENIZE) {
      System.out.println("Converted token stream:");
      tokens.fill();
      System.out.println(tokens.getTokens().stream()
          .map(token -> "[" + vocabulary.getSymbolicName(token.getType()) + ", " + token.getText()
              + "] ").collect(
              Collectors.joining()));
      System.exit(EXIT_CODE);
    }

    ParseTree tree = parser.prog(); // begin parsing at prog rule

    if (option == PARSE) {
      System.out.println("Parse tree:");
      System.out.println(tree.toStringTree(parser)); // print LISP-style tree
      System.exit(EXIT_CODE);
    }

    if (EXIT_CODE != 0) {
      System.exit(EXIT_CODE);
    }

    /* Semantic analyzer */
    Visitor semanticVisitor = new Visitor();
    ASTNode prog = semanticVisitor.visit(tree);
    prog.check(); /* Perform the actual checking after building it */

    if (option == SEMANTIC_CHECK || EXIT_CODE != 0) {
      System.exit(EXIT_CODE);
    }

//    CodeGen.checkStrFormat();
//    CodeGen.checkIntFormat();
//    CodeGen.checkEmptyFormat();

    prog.assemble(null, RegisterManager.getLocalRegs());

    String[] parts = filename.split("/");
    String tempFilename = parts[parts.length - 1].split("\\.")[0];
    String assemblyFileName = tempFilename + ".s";
    CodeGen.writeToFile(assemblyFileName);
    InstructionEvaluation.optimiseInstructions(assemblyFileName);

    if (option == ASSEMBLE || option == NO_OPTION) {
      InputStream input = null;
      try {
        input = new FileInputStream(assemblyFileName);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        System.exit(EXIT_CODE);
      }
      try {
        System.out.write(input.readAllBytes());
      } catch (IOException e) {
        e.printStackTrace();
      }
      System.exit(EXIT_CODE);
    }

    ProcessBuilder pb = new ProcessBuilder();
    String command = "arm-linux-gnueabi-gcc -o EXEName -mcpu=arm1176jzf-s -mtune=arm1176jzf-s " +
        assemblyFileName +
        " && qemu-arm -L /usr/arm-linux-gnueabi/ EXEName && rm EXEName";
    pb.command("bash", "-c", command);
    try {
      Process process = pb.start();

      StringBuilder output = new StringBuilder();

      BufferedReader reader = new BufferedReader(
          new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line).append("\n");
      }

      int exitVal = process.waitFor();
      System.out.println(output.toString());
      System.out.println("Exit: " + exitVal);
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }

    System.exit(EXIT_CODE);
  }
}
