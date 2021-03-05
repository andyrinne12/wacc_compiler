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
  CS,
  NONE {
    @Override
    public String toString() {
      return "";
    }
  }
}
