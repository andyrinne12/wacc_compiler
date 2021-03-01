package back_end.instructions.arithmetic;

import back_end.instructions.ArmInstruction;
import back_end.instructions.Condition;
import back_end.operands.Operand;

public abstract class ArithmeticInstr extends ArmInstruction {

  protected final Operand rd;
  protected final Operand rn;
  protected final String setMark;

  public ArithmeticInstr(String name, Condition cond, boolean set, Operand rd, Operand rn) {
    super(name, cond);
    this.setMark = set ? "S" : "";
    this.rd = rd;
    this.rn = rn;
  }

  public ArithmeticInstr(String name, boolean set, Operand rd, Operand rn) {
    this(name, Condition.NONE, set, rd, rn);
  }
}
