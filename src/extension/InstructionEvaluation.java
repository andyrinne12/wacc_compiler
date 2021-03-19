package extension;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class InstructionEvaluation {

  /*
   * Optimisation techniques that simplify the generated assembly code. The compiler is able to
   * analyse the generated code and remove redundant instructions. The created file is overwritten
   * with the optimised assembly code.
   */

  public static void optimiseInstructions(String file) throws IOException {
    File f = new File(file);
    File temp = new File(f.getAbsolutePath() + ".tmp");

    BufferedReader br = new BufferedReader(new FileReader(file));

    String currLine;
    String prevLine = null;
    FileWriter fw = new FileWriter(temp, false);
    PrintWriter pw = new PrintWriter(fw);

    while ((currLine = br.readLine()) != null) {

      String[] parts = currLine.split(" ", 2);

      /*
       * If there is a STR and then a LDR with the same register from the same address, the LDR is
       * redundant and can be deleted.
       *  STR r4, [SP]   in writeFST test
       *  LDR r4, [SP]
       */
      if (parts[0].equals("\t\tLDR") && prevLine != null) {
        String[] prevLineParts = prevLine.split(" ", 2);
        if (prevLineParts.length > 1 && parts[1].equals(prevLineParts[1])
            && prevLineParts[0].equals("\t\tSTR")) {
          currLine = br.readLine();
        }
      }

      /*
       * Double pop stack pointer:
       *   POP {sp}
       *   POP {sp}  => only one pop instruction is kept
       */
      if (parts[0].equals("\t\tPOP") && prevLine != null) {
        String[] prevLineParts = prevLine.split(" ", 2);
        if (prevLineParts.length > 0 && prevLineParts[0].equals("\t\tPOP")
            && parts[1].equals(prevLineParts[1]) && parts[1].equals("{sp}")) {
          currLine = br.readLine();
        }
      }

      /*
       * LDR r2, =0
       * ADDS r1, r1, r2
       *   => the commands are deleted
       */
      if (parts[0].equals("\t\tLDR") && parts[1].endsWith("=0")) {
        String next = br.readLine();
        String[] newParts = next.split(" ", 2);
        String[] regs = newParts[1].split(", ");
        if (regs.length == 3) {
          if (newParts[0].equals("\t\tADDS")
              && regs[0].equals(regs[1])
              && regs[2].equals(parts[1].substring(0, 2))) {
            currLine = br.readLine();
          }
        }
        pw.print(currLine + "\n");
        currLine = next;
      }

      prevLine = currLine;
      pw.print(currLine + "\n");
      pw.flush();

    }

    pw.close();
    br.close();

    if (!f.delete()) {
      System.err.println("File hasn't been deleted");
    }

    if (!temp.renameTo(f)) {
      System.err.println("File hasn't been renamed");
    }
  }
}
