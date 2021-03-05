package back_end.operands.immediate;

public class ImmString extends ImmValue {

  private final String text;
  private final String label;
  private int length = 0;

  public ImmString(String text, int no) {
    this.text = text;
    this.label = "msg_" + no;
    this.length = text.length();
  }

  public ImmString(String text, int no, int length) {
    this(text, no);
    this.length = length;
  }

  @Override
  public String instrPrint() {
    return "=" + label;
  }

  public String getHeader() {
    return "\t" + label + ":\n\t\t.word " + length + "\n\t\t.ascii \"" + text + '"';
  }
}
