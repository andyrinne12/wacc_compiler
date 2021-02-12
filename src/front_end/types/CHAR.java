package front_end.types;

public class CHAR extends TYPE {

  public CHAR() {
  }

  public TYPE getType() {
    return this;
  }

  @Override
  public String toString() {
    return "char";
  }

  @Override
  public boolean equalsType(TYPE type) {
    return type == this;
  }
}

