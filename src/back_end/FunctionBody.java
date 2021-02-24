package back_end;

import back_end.instructions.Directive;
import back_end.instructions.Instruction;
import back_end.instructions.Label;
import java.util.ArrayList;
import java.util.List;

public class FunctionBody {

  private final List<Instruction> instrList = new ArrayList<>();
  private final String name;

  public FunctionBody(String name) {
    this.name = name;
    if (!name.equals("main")) {
      name = "f_" + name;
    }
    Label title = new Label(name);
    instrList.add(title);
  }

  public String getName() {
    return name;
  }

  public void addInstr(Instruction instr) {
    instrList.add(instr);
  }

  public void endBody() {
    instrList.add(Directive.DIR_LTORG);
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    for (Instruction instr : instrList) {
      str.append('\t').append(instr).append('\n');
    }
    str.append('\n');
    return str.toString();
  }
}
