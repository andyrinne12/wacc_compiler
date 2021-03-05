package back_end;

import back_end.instructions.Directive;
import back_end.operands.immediate.ImmString;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CodeGen {

  public static List<ImmString> textData = new ArrayList<>();
  public static List<FunctionBody> funcBodies = new ArrayList<>();

  private static int dataCounter = 0;
  private static int labelCounter = 0;
  private static ImmString intFormat;
  private static ImmString strFormat;
  private static ImmString emptyFormat;

  public static ImmString addData(String text) {
    ImmString data = new ImmString(text, dataCounter);
    textData.add(data);
    dataCounter++;
    return data;
  }

  public static void checkIntFormat() {
    if (intFormat == null) {
      intFormat = new ImmString("%d\\0", dataCounter);
      textData.add(intFormat);
      dataCounter++;
    }
  }

  public static void checkStrFormat() {
    if (strFormat == null) {
      strFormat = new ImmString("%.*s\\0", dataCounter);
      textData.add(strFormat);
      dataCounter++;
    }
  }

  public static void checkEmptyFormat() {
    if (emptyFormat == null) {
      emptyFormat = new ImmString("\\0", dataCounter);
      textData.add(emptyFormat);
      dataCounter++;
    }
  }

  public static String getLabel() {
    String newLabel = "L" + labelCounter;
    labelCounter++;
    return newLabel;
  }

  public static void writeToFile(String fileName) {
    PrintWriter out;
    try {
      out = new PrintWriter(fileName);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return;
    }

    out.println(Directive.DIR_DATA);
    for (ImmString data : textData) {
      out.println(data.getHeader());
      out.println();
    }

    out.println(Directive.DIR_TEXT);
    out.println(Directive.DIR_GLOBAL);

    out.println();

    for (FunctionBody func : funcBodies) {
      out.println(func);
    }

    out.close();
  }
}
