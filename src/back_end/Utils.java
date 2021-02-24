package back_end;

import back_end.instructions.Condition;
import back_end.instructions.Instruction;
import back_end.instructions.branch.BL;

public class Utils {

  public static Instruction PUTCHAR = new BL(Condition.NONE, "putchar");

}
