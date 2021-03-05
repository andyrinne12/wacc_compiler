package back_end.instructions.branch;

import back_end.instructions.ArmInstruction;
import back_end.instructions.Condition;

public class B extends ArmInstruction {

  private final String label;

  public B(Condition cond, String label) {
    super("B", cond);
    this.label = label;
  }

  @Override
  public String instrPrint() {
    return String.format("%s%s %s", name, cond, label);
  }
}
