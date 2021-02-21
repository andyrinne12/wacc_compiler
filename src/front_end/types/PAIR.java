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

  @Override
  public String toString() {
    return "(" + firstType + ", " + secondType + ")";
  }

  @Override
  public boolean equalsType(TYPE type) {
    if (!(type instanceof PAIR)) {
      return false;
    }
    PAIR pair2 = (PAIR) type;
    if (firstType == null || secondType == null) {
      return true;
    }
    if (pair2.firstType == null || pair2.secondType == null) {
      return true;
    }
    return pair2.firstType.equalsType(firstType) && pair2.secondType.equalsType(secondType);
  }
}
