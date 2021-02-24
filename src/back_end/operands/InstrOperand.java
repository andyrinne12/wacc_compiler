package back_end.operands;

public abstract class InstrOperand implements Operand {

  @Override
  public String toString() {
    return instrPrint();
  }

  @Override
  public abstract String instrPrint();
}
