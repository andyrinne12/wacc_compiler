package back_end;

import back_end.instructions.Directive;
import back_end.instructions.Instruction;
import back_end.instructions.Label;
import back_end.instructions.store.LDR;
import back_end.instructions.store.POP;
import back_end.instructions.store.PUSH;
import back_end.operands.immediate.ImmInt;
import back_end.operands.registers.RegisterManager;
import java.util.ArrayList;
import java.util.List;

public class FunctionBody {

  private final static List<Instruction> instrList = new ArrayList<>();
  private final String name;
  private boolean main = false;

  public FunctionBody(String name, boolean main) {
    this(name);
    this.main = main;
  }

  public FunctionBody(String name) {
    this.name = name;
    if (main) {
      name = "f_" + name;
    }
    Label title = new Label(name);
    PUSH pushLink = new PUSH(RegisterManager.LR);
    instrList.add(title);
    instrList.add(pushLink);
  }

  public String getName() {
    return name;
  }

  public static void addInstr(Instruction instr) {
    instrList.add(instr);
  }

  public void endBody() {
    if (main) {
      instrList.add(new LDR(RegisterManager.getResultReg(), new ImmInt(0)));
    }
    POP popLink = new POP(RegisterManager.PC);
    instrList.add(popLink);
    if (!main) {
      instrList.add(popLink);
    }
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
