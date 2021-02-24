package back_end.instructions.store;

import back_end.instructions.ArmInstruction;
import back_end.instructions.Condition;
import back_end.operands.registers.Register;
import java.util.List;

public class PUSH extends ArmInstruction {

  private final String regList;

  public PUSH(Register reg) {
    super("PUSH", Condition.NONE);
    regList = String.format("{%s}", reg);
  }

  public PUSH(List<Register> regList) {
    super("PUSH", Condition.NONE);
    StringBuilder str = new StringBuilder("{");
    for (Register r : regList) {
      str.append(r.toString()).append(", ");
    }
    if (!regList.isEmpty()) {
      str.delete(str.length() - 2, str.length() - 1);
    }
    str.append("}");
    this.regList = str.toString();
  }

  @Override
  public String instrPrint() {
    return String.format("%s %s", name, regList);
  }
}
