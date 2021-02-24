package back_end.instructions;

public class Label implements Instruction {

  private final String label;

  public Label(String label) {
    this.label = label;
  }

  @Override
  public String instrPrint() {
    return String.format("%s:", label);
  }

  @Override
  public String toString() {
    return instrPrint();
  }
}
