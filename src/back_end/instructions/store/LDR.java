package back_end.instructions.store;

import back_end.instructions.Condition;
import back_end.instructions.arithmetic.ArithmeticInstr;
import back_end.operands.Operand;
import back_end.operands.immediate.ImmInt;

public class LDR extends ArithmeticInstr {

  private final Operand address;

  public LDR(Condition cond,
      Operand rd, Operand address) {
    super("LDR", cond, false, rd, null);
    this.address = address;
  }

  public LDR(Operand rd, Operand address) {
    this(Condition.NONE, rd, address);
  }


  @Override
  public String instrPrint() {
    if(address instanceof ImmInt){
      ImmInt immInt = (ImmInt) address;
      return String.format("%s%s %s, %s", name, cond, rd, immInt.ldrPrint());
    }
    return String.format("%s%s %s, %s", name, cond, rd, address);
  }
}
