package back_end.instructions.logical;

import back_end.instructions.Condition;
import back_end.instructions.arithmetic.ArithmeticInstr;
import back_end.operands.Operand;

public class MOV extends ArithmeticInstr {

  private final Operand operand2;

  public MOV(Condition cond, boolean set, Operand rd, Operand operand2) {
    super("MOV", cond, set, rd, null);
    this.operand2 = operand2;
  }

  public MOV(Operand rd, Operand operand2) {
    this(Condition.NONE, false, rd, operand2);
  }

  @Override
  public String instrPrint() {
    return String.format("%s%s%s %s, %s", name, cond, setMark, rd, operand2);
  }
}
