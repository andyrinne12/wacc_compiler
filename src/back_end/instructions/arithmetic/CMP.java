package back_end.instructions.arithmetic;

import back_end.operands.Operand;

public class CMP extends ArithmeticInstr {

  private final Operand operand2;

  public CMP(Operand rn, Operand operand2) {
    super("CMP", false, null, rn);
    this.operand2 = operand2;
  }

  @Override
  public String instrPrint() {
    return String.format("%s%s %s, %s", name, cond, rn, operand2);
  }
}
