package back_end.operands.immediate;

public class ImmInt extends ImmValue {

  private final int value;

  public ImmInt(int value) {
    this.value = value;
  }

  public ImmInt(boolean value) {
    this.value = value ? 1 : 0;
  }

  public ImmInt(char chr) {
    this.value = chr;
  }

  @Override
  public String instrPrint() {
    return "#" + value;
  }

  public String ldrPrint() {
    return "=" + value;
  }

  public int getValue() {
    return value;
  }
}
