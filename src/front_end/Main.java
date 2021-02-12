package front_end;

import antlr.WACCLexer;
import antlr.WACCParser;
import front_end.AST.ASTNode;
import java.io.IOException;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {

  public final static int NO_OPTION = 0;      // Run the full compile pipeline
  public final static int TOKENIZE = 1;       // Convert the input file into a token stream
  public final static int PARSE = 2;          // Create the parse tree and perform syntactic check
  public final static int SEMANTIC_CHECK = 3; // Perform semantic check and build AST

  public static int EXIT_CODE = 0;

  public static void main(String[] args) {

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

    // Semantic analyzer
    Visitor semanticVisitor = new Visitor();
    ASTNode prog = semanticVisitor.visit(tree);

    System.exit(EXIT_CODE);
  }

}

class CustomErrorHandler extends BaseErrorListener {

  @Override
  public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
      int charPositionInLine, String msg, RecognitionException e) {
    Main.EXIT_CODE = 100;
 //   System.err.println("SYNTAX ERROR:" + line + ": " + charPositionInLine);
  }
}
