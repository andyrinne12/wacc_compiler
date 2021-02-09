package semantics.types;

public class PairType implements Type{

  private final Type firstType;
  private final Type secondType;

  public PairType(Type firstType, Type secondType) {
    this.firstType = firstType;
    this.secondType = secondType;
  }

  public Type getFirstType() {
    return firstType;
  }

  public Type getSecondType() {
    return secondType;
  }

  @Override
  public boolean equalsType(Type t) {
    //TODO: Implement
    return false;
  }
}
