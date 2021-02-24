package back_end.instructions.arithmetic;

import back_end.instructions.Condition;
import back_end.operands.Operand;

public class SMULL extends ArithmeticInstr {

  private final Operand rdLo;
  private final Operand rdHi;
  private final Operand rm;

  public SMULL(String name, Condition cond, boolean set,
      Operand rdLo, Operand rdHi, Operand rn, Operand rm) {
    super(name, cond, set, null, rn);
    this.rdLo = rdLo;
    this.rdHi = rdHi;
    this.rm = rm;
  }

  @Override
  public String instrPrint() {
    return String.format("%s%s%s %s, %s, %s, %s", name, cond, setMark, rdLo, rdHi, rn, rm);
  }
}
