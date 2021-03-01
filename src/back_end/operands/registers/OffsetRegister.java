package back_end.operands.registers;

import back_end.operands.InstrOperand;

public class OffsetRegister extends InstrOperand {

  private final Register reg;
  private final int offset;
  private final String rw;

  public OffsetRegister(Register reg, int offset, boolean rewrite) {
    this.reg = reg;
    this.offset = offset;
    this.rw = rewrite ? "!" : "";
  }

  public OffsetRegister(Register reg, int offset) {
    this(reg, offset, false);
  }

  public OffsetRegister(Register reg) {
    this(reg, 0);
  }

  @Override
  public String instrPrint() {
    if (offset == 0) {
      return String.format("[%s]", reg);
    } else {
      return String.format("[%s, #%s]%s", reg, offset, rw);
    }
  }
}
