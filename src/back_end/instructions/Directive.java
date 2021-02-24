package back_end.instructions;

public class Directive implements Instruction {

  public static Directive DIR_DATA = new Directive("data");
  public static Directive DIR_TEXT = new Directive("text");
  public static Directive DIR_GLOBAL = new Directive("global main");
  public static Directive DIR_LTORG = new Directive("ltorg");

  private final String name;

  public Directive(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return instrPrint();
  }

  @Override
  public String instrPrint() {
    return String.format("\t.%s", name);
  }
}
