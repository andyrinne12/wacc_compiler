package front_end.types;

public class POINTER extends TYPE {

  private final TYPE objectType;

  public POINTER(TYPE objectType) {
    this.objectType = objectType;
  }

  public TYPE getType() {
    return this;
  }

  public TYPE getObjectType() {
    return objectType;
  }

  @Override
  public String toString() {
    return objectType + "*";
  }

  @Override
  public boolean equalsType(TYPE type2) {
    if (!(type2 instanceof POINTER)) {
      return false;
    }
    POINTER array2 = (POINTER) type2;
    if (objectType == null || array2.objectType == null) {
      return true;
    }
    return array2.getObjectType().equalsType(objectType);
  }
}
