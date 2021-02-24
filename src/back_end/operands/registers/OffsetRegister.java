package back_end.operands.registers;

import back_end.operands.Operand;

public class OffsetRegister implements Operand {

  private final Register reg;
  private final int offset;

  public OffsetRegister(Register reg, int offset) {
    this.reg = reg;
    this.offset = offset;
  }

  @Override
  public String instrPrint() {
    return "[" + reg + ", #" + offset + "]";
  }
}
