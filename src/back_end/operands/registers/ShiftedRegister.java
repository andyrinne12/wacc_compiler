package back_end.operands.registers;

import back_end.operands.InstrOperand;
import back_end.operands.Operand;
import back_end.operands.immediate.ImmInt;

public class ShiftedRegister extends InstrOperand {

  private final Register reg;
  private final Shift shift;
  private final Operand op;

  public ShiftedRegister(Register reg, Shift shift, Operand op) {
    this.reg = reg;
    this.shift = shift;
    this.op = op;
  }

  @Override
  public String instrPrint() {
    String arg2;
    if (op instanceof ImmInt) {
      ImmInt offset = (ImmInt) op;
      int value = offset.getValue();
      arg2 = "#" + value;
    } else if (op instanceof Register) {
      arg2 = op.instrPrint();
    } else {
      arg2 = "WRONG";
    }
    return String.format("%s, %s %s", reg, shift, arg2);
  }
}
