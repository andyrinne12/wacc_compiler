package back_end.instructions;

public enum Condition {
  EQ,
  NE,
  VS,
  GT,
  GE,
  LE,
  LT,
  B,
  NONE {
    @Override
    public String toString() {
      return "";
    }
  }
}
