package front_end.types;

public abstract class TYPE extends IDENTIFIER {

  @Override
  public abstract String toString();

  public abstract boolean equalsType(TYPE type);

}
