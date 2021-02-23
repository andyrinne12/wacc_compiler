package back_end.operands.immediate;

public class ImmString {

  private final String text;
  private final String label;

  public ImmString(String text, int no) {
    this.text = text;
    this.label = "msg_" + no;
  }

  @Override
  public String toString() {
    return "=" + label;
  }

  public String getHeader() {
    return label + ":\n\t.word " + text.length() + "\n\t.ascii \"" + text + '"';
  }
}
