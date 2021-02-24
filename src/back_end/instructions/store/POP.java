package back_end.instructions.store;

import back_end.instructions.ArmInstruction;
import back_end.instructions.Condition;
import back_end.operands.registers.Register;
import java.util.List;

public class POP extends ArmInstruction {

  private final String reglist;

  public POP(String name, Condition cond, List<Register> reglist) {
    super("POP", Condition.NONE);
    StringBuilder str = new StringBuilder("{");
    for (Register r : reglist) {
      str.append(r.toString()).append(", ");
    }
    if (!reglist.isEmpty()) {
      str.delete(str.length() - 2, str.length() - 1);
    }
    str.append("}");
    this.reglist = str.toString();
  }

  @Override
  public String instrPrint() {
    return String.format("%s %s", name, reglist);
  }
}
