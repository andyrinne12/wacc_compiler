package back_end.instructions.arithmetic;

import back_end.instructions.Condition;
import back_end.operands.Operand;

public class ADD extends ArithmeticInstr {

  private final Operand operand2;

  public ADD(Condition cond, boolean set, Operand rd, Operand rn, Operand operand2) {
    super("ADD", cond, set, rd, rn);
    this.operand2 = operand2;
  }

  @Override
  public String instrPrint() {
    return String.format("%s%s%s %s, %s, %s", name, cond, setMark, rd, rn, operand2);
  }
}
