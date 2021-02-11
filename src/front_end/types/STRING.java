package front_end.types;

public class STRING extends TYPE {

  public STRING() {
  }

  public TYPE getType() {
    return this;
  }

  @Override
  public String toString() {
    return "String";
  }

  @Override
  public boolean equalsType(TYPE type) {
    return type instanceof STRING;
  }
}
