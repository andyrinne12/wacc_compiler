package back_end.operands.registers;

import back_end.operands.InstrOperand;

public class ShiftedRegister extends InstrOperand {

  private final Register reg;
  private final Shift shift;
  private final int offset;

  public ShiftedRegister(Register reg, Shift shift, int offset) {
    this.reg = reg;
    this.shift = shift;
    this.offset = offset;
  }

  @Override
  public String instrPrint() {
    if (offset == 0) {
      return String.format("[%s]", reg);
    } else {
      return String.format("[%s, #%d]", reg, offset);
    }
  }
}
