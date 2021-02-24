package back_end.operands.registers;

import back_end.operands.Operand;

public class OffsetRegister implements Operand {

  private final Register reg;
  private final int offset;
  private final String rw;

  public OffsetRegister(Register reg, int offset, boolean rewrite) {
    this.reg = reg;
    this.offset = offset;
    this.rw = rewrite ? "!" : "";
  }

  @Override
  public String instrPrint() {
    return String.format("[%s, %s]%s", reg, offset, rw);
  }
}
