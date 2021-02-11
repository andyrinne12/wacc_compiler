package front_end.types;

public class CHAR extends TYPE {

  public CHAR() {
  }

  public TYPE getType() {
    return this;
  }

  @Override
  public boolean equalsType(TYPE type) {
    return (type instanceof CHAR);
  }
}

