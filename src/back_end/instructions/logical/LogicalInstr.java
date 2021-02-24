package back_end.instructions.logical;

import back_end.instructions.ArmInstruction;
import back_end.instructions.Condition;
import back_end.operands.Operand;

public abstract class LogicalInstr extends ArmInstruction {

  protected final Operand rd;
  protected final Operand rn;
  protected final String setMark;

  public LogicalInstr(String name, Condition cond, boolean set, Operand rd, Operand rn) {
    super(name, cond);
    this.setMark = set ? "S" : "";
    this.rd = rd;
    this.rn = rn;
  }
}
