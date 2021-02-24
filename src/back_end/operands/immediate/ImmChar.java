package back_end.operands.immediate;

public class ImmChar extends ImmValue {

  private final char value;

  public ImmChar(char value) {
    this.value = value;
  }

  @Override
  public String instrPrint() {
    return "='" + value + "'";
  }
}
