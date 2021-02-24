package back_end.instructions;

public abstract class ArmInstruction implements Instruction {

  protected final Condition cond;

  public ArmInstruction(Condition cond) {
    this.cond = cond;
  }

  @Override
  public String toString() {
    return instrPrint();
  }

  @Override
  public abstract String instrPrint();
}
