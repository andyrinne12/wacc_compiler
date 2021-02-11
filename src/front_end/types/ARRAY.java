package front_end.types;

public class ARRAY extends TYPE {

  private final TYPE elemType;
  private int size;

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
}
