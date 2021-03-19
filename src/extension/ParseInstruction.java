package extension;

public class ParseInstruction {
  private final String instruction;

  private String[] parts;

  public ParseInstruction(String instruction) {
    this.instruction = instruction;
    parts = instruction.split(" ", 2);
  }

  public String getOperation() {
    return parts[0];
  }

  public String[] getRegisters() {
    if(getSize() > 1)
    return parts[1].split(", ");
    return null;
  }

  public int getSize() {
    return parts.length;
  }

  public String stringRegisters() {
    return parts[1];
  }
}
