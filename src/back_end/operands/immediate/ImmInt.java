package back_end.operands.immediate;

public class ImmInt extends ImmValue {

  private final int value;
  private boolean isChar = false;

  public ImmInt(int value) {
    this.value = value;
  }

  public ImmInt(boolean value) {
    this.value = value ? 1 : 0;
  }

  public ImmInt(char chr) {
    this.value = chr;
    isChar = true;
  }

  @Override
  public String instrPrint() {
    if (isChar) {
      return "#\'" + (char) value + "\'";
    }
    else {
      return "=" + value;
    }
  }

  public int getValue() {
    return value;
  }
}
