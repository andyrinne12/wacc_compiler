package back_end;

import back_end.instructions.Condition;
import back_end.instructions.Instruction;
import back_end.instructions.branch.BL;

public class Utils {

  public static Instruction PUTCHAR = new BL(Condition.NONE, "putchar");
  public static Instruction MALLOC = new BL(Condition.NONE, "malloc");
  public static Instruction CHECK_ARRAY_BOUNDS = new BL(Condition.NONE, "p_check_array_bounds");
  public static Instruction CHECK_NULL_POINTER = new BL(Condition.NONE, "p_check_null_pointer");

}
