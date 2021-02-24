package back_end.operands.registers;

import back_end.operands.InstrOperand;

public class ShiftedRegister extends InstrOperand {

  private final Register reg;
  private final Shift shift;
  private final Shift offset;

  public ShiftedRegister(Register reg, Shift shift, Shift offset) {
    this.reg = reg;
    this.shift = shift;
    this.offset = offset;
  }

  @Override
  public String instrPrint() {
    return reg + ", " + shift + " #" + offset;
  }
}
