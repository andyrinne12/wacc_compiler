package back_end.instructions;

public abstract class ArmInstruction implements Instruction {

  protected final String name;
  protected final Condition cond;

  public ArmInstruction(String name, Condition cond) {
    this.name = name;
    this.cond = cond;
  }

  public ArmInstruction(String name) {
    this.name = name;
    this.cond = Condition.NONE;
  }

  @Override
  public String toString() {
    return "\t" + instrPrint();
  }

  @Override
  public abstract String instrPrint();
}
