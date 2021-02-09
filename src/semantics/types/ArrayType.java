package semantics.types;

public class ArrayType implements Type{

  private final Type elemType;

  public ArrayType(Type elemType) {
    this.elemType = elemType;
  }

  public Type getElemType() {
    return elemType;
  }

  @Override
  public boolean equalsType(Type t) {
    //TODO: Implement
    return false;
  }
}
