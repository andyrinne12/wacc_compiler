package semantics.types;

public class PrimitiveType implements Type{

  private final PrimitiveTypeEnum type;

  public PrimitiveType(PrimitiveTypeEnum type) {
    this.type = type;
  }

  public PrimitiveTypeEnum getType() {
    return type;
  }

  @Override
  public boolean equalsType(Type t) {
    //TODO: Implement
    return false;
  }
}
