package front_end.types;

public class BOOLEAN extends TYPE {

  public BOOLEAN() {
  }

  public TYPE getType() {
    return this;
  }

  @Override
  public String toString() {
    return "bool";
  }

  @Override
  public boolean equalsType(TYPE type) {
    return type == this;
  }
}
