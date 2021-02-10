package front_end.types;

public class ARRAY extends TYPE {

  private final TYPE elemType;

  public ARRAY(TYPE elemType) {
    this.elemType = elemType;
  }

  public TYPE getType() {
    return elemType;
  }
}
