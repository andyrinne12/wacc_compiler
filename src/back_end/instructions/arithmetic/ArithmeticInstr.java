package back_end.instructions.arithmetic;

import back_end.instructions.ArmInstruction;
import back_end.instructions.Condition;

public abstract class ArithmeticInstr extends ArmInstruction {

  protected final boolean set;

  public ArithmeticInstr(Condition cond, boolean set) {
    super(cond);
    this.set = set;
  }
}
