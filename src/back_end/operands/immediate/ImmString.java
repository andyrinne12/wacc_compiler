package back_end.operands.immediate;

public class ImmString extends ImmValue{

  private final String text;
  private final String label;

  public ImmString(String text, int no) {
    this.text = text;
    this.label = "msg_" + no;
  }

  @Override
  public String instrPrint() {
    return "=" + label;
  }

  public String getHeader() {
    return "\t" + label + ":\n\t\t.word " + text.length() + "\n\t\t.ascii \"" + text + '"';
  }
}
