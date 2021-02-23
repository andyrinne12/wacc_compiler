package back_end.operands.immediate;

public class ImmInt extends ImmValue {

  private final int value;

  public ImmInt(int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "=" + value;
  }
}
