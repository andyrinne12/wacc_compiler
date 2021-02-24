package back_end.instructions.branch;

import back_end.instructions.ArmInstruction;
import back_end.instructions.Condition;

public class BL extends ArmInstruction {

  private final String label;

  public BL(Condition cond, String label) {
    super("BL", cond);
    this.label = label;
  }

  @Override
  public String instrPrint() {
    return String.format("%s%s %s", name, cond, label);
  }
}
