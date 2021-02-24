package back_end.instructions.logical;

import back_end.instructions.Condition;
import back_end.instructions.arithmetic.ArithmeticInstr;
import back_end.operands.Operand;

public class TST extends ArithmeticInstr {

  private final Operand operand2;

  public TST(Condition cond, boolean set, Operand rn, Operand operand2) {
    super("TST", cond, set, null, rn);
    this.operand2 = operand2;
  }

  @Override
  public String instrPrint() {
    return String.format("%s%s%s %s, %s", name, cond, setMark, rn, operand2);
  }
}
