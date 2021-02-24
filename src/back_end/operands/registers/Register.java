package back_end.operands.registers;

import back_end.operands.InstrOperand;

public class Register extends InstrOperand {

  private final int index; // used to represent a register number.

  public Register(int index) {
    this.index = index;
  }

  public int getIndex() {
    return index;
  }

  @Override
  public String instrPrint() {
    if (index == 15) {
      return "pc";
    } else if (index == 14) {
      return "lr";
    } else if (index == 13) {
      return "sp";
    } else {
      return "r" + index;
    }
  }
}
