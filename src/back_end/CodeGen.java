package back_end;

import back_end.instructions.Directive;
import back_end.operands.immediate.ImmString;
import back_end.operands.registers.Register;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGen {

  public static List<ImmString> textData = new ArrayList<>();
  public static List<FunctionBody> funcBodies = new ArrayList<>();
  public static Map<String, Register> lastFuncs = new HashMap<>();

  private static int dataCounter = 0;
  private static int labelCounter = 0;
  private static ImmString intFormat;
  private static ImmString strFormat;
  private static ImmString emptyFormat;
  private static ImmString trueFormat;
  private static ImmString falseFormat;
  private static ImmString referenceFormat;

  public static ImmString addData(String text) {
    //discard the leading and trailing double-quotes or single quotes in the string.
    char firstChar = text.charAt(0);
    String cleanedText = text;
    if (firstChar == '\"' || firstChar == '\'') {
      cleanedText = text.substring(1, text.length() - 1);
    }

    ImmString data = new ImmString(cleanedText, dataCounter);
    textData.add(data);
    dataCounter++;
    return data;
  }

  public static ImmString checkTrueFormat() {
    if (trueFormat == null) {
      trueFormat = new ImmString("true\\0", dataCounter);
      textData.add(trueFormat);
      dataCounter++;
    }
    return trueFormat;
  }

  public static ImmString checkFalseFormat() {
    if (falseFormat == null) {
      falseFormat = new ImmString("false\\0", dataCounter);
      textData.add(falseFormat);
      dataCounter++;
    }
    return falseFormat;
  }

  public static ImmString checkIntFormat() {
    if (intFormat == null) {
      intFormat = new ImmString("%d\\0", dataCounter);
      textData.add(intFormat);
      dataCounter++;
    }
    return intFormat;
  }

  public static ImmString checkStrFormat() {
    if (strFormat == null) {
      strFormat = new ImmString("%.*s\\0", dataCounter);
      textData.add(strFormat);
      dataCounter++;
    }
    return strFormat;
  }

  public static ImmString checkRefFormat() {
    if (referenceFormat == null) {
      referenceFormat = new ImmString("%p\\0", dataCounter);
      textData.add(referenceFormat);
      dataCounter++;
    }
    return referenceFormat;
  }

  public static ImmString checkEmptyFormat() {
    if (emptyFormat == null) {
      emptyFormat = new ImmString("\\0", dataCounter);
      textData.add(emptyFormat);
      dataCounter++;
    }
    return emptyFormat;
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
