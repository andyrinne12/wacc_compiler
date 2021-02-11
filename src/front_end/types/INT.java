package front_end.types;

public class INT extends TYPE {

  private int max;
  private int min;

  public INT() {
    max = Integer.MAX_VALUE;
    min = Integer.MIN_VALUE;
  }

  public TYPE getType() {
    return this;
  }

  @Override
  public String toString() {
    return "int";
  }

  @Override
  public boolean equalsType(TYPE type) {
    return type instanceof INT;
  }
}
