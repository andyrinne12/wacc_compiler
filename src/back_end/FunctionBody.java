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

  private final List<Instruction> instrList = new ArrayList<>();
  private final String name;
  private boolean main = false;
  private boolean util = false;
  private boolean pushPop = true;

  public FunctionBody(String name, boolean main, boolean util, boolean pushPop) {
    this.main = main;
    this.util = util;
    if (!main && util) {
      this.name = "p_" + name;
    } else {
      this.name = name;
    }
    this.pushPop = pushPop;
    Label title = new Label(this.name);
    PUSH pushLink = new PUSH(RegisterManager.LR);
    instrList.add(title);
    if (pushPop) {
      instrList.add(pushLink);
    }
  }

  public FunctionBody(String name) {
    this.name = "f_" + name;
    Label title = new Label(this.name);
    PUSH pushLink = new PUSH(RegisterManager.LR);
    instrList.add(title);
    instrList.add(pushLink);
  }

  public String getName() {
    return name;
  }

  public void addInstr(Instruction instr) {
    instrList.add(instr);
  }

  public void endBody() {
    if (!pushPop) {
      return;
    }
    if (main) {
      instrList.add(new LDR(RegisterManager.getResultReg(), new ImmInt(0)));
    }
    POP popLink = new POP(RegisterManager.PC);
    instrList.add(popLink);
  //  if (!main && !util) {
  //    instrList.add(popLink);
  //  }
    if (!util) {
      instrList.add(Directive.DIR_LTORG);
    }
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < instrList.size(); i++) {
      str.append('\t').append(instrList.get(i));
      if (i < instrList.size() - 1) {
        str.append('\n');
      }
    }
    return str.toString();
  }
}
