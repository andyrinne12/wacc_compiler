package front_end.types;
public class PAIR extends TYPE {

  private final TYPE firstType;
  private final TYPE secondType;

  public PAIR(TYPE firstType, TYPE secondType) {
    this.firstType = firstType;
    this.secondType = secondType;

  }

  public TYPE getFirstType() {
    return firstType;
  }

  public TYPE getSecondType() {
    return secondType;
  }

  public TYPE getType() {
    return this;
  }

}
