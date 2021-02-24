package back_end.instructions.arithmetic;

import back_end.instructions.Condition;
import back_end.operands.Operand;

public class SUB extends ArithmeticInstr {

  private final Operand operand2;

  public SUB(Condition cond, boolean set, Operand rd, Operand rn, Operand operand2) {
    super("SUB", cond, set, rd, rn);
    this.operand2 = operand2;
  }

  @Override
  public String instrPrint() {
    return String.format("%s%s%s %s, %s, %s", name, cond, setMark, rd, rn, operand2);
  }
}
