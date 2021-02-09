package semantics.types;

public class PrimitiveType implements Type{

  private final PrimitiveType type;

  public PrimitiveType(PrimitiveType type) {
    this.type = type;
  }

  public PrimitiveType getType() {
    return type;
  }

  @Override
  public boolean equalsType(Type t) {
    //TODO: Implement
    return false;
  }
}
