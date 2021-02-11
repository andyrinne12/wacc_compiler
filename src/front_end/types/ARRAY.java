package front_end.types;

public class ARRAY extends TYPE {

  private final TYPE elemType;
  private final int size;

  public ARRAY(TYPE elemType, int size) {
    this.elemType = elemType;
    this.size = size;
  }

  public TYPE getType() {
    return this;
  }

  public TYPE getElemType() {
    return elemType;
  }

  public int getSize() {
    return size;
  }

  @Override
  public boolean equalsType(TYPE type2) {
    if (!(type2 instanceof ARRAY)) {
      return false;
    }
    ARRAY array2 = (ARRAY) type2;
    return array2.getElemType().equalsType(elemType);
  }
}
