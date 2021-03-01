package back_end.instructions.store;

import back_end.instructions.Condition;
import back_end.instructions.arithmetic.ArithmeticInstr;
import back_end.operands.Operand;

public class STR extends ArithmeticInstr {

  private final Operand address;

  public STR(Condition cond,
      Operand rd, Operand address) {
    super("STR", cond, false, rd, null);
    this.address = address;
  }

  public STR(Operand rd, Operand address) {
    this(Condition.NONE, rd, address);
  }

  @Override
  public String instrPrint() {
    return String.format("%s%s %s, %s", name, cond, rd, address);
  }
}
