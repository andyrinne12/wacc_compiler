package back_end;

import back_end.instructions.Condition;
import back_end.instructions.Instruction;
import back_end.instructions.branch.BL;

public class Utils {

  public static Instruction PUTCHAR = new BL(Condition.NONE, "putchar");
  public static Instruction MALLOC = new BL(Condition.NONE, "malloc");
  public static Instruction CHECK_ARRAY_BOUNDS = new BL(Condition.NONE, "p_check_array_bounds");
  public static Instruction CHECK_NULL_POINTER = new BL(Condition.NONE, "p_check_null_pointer");
  public static Instruction RUNTIME_ERROR = new BL(Condition.NONE, "p_throw_runtime_error");
  public static Instruction FREE_PAIR = new BL(Condition.NONE, "p_free_pair");
  public static Instruction FREE_ARRAY = new BL(Condition.NONE, "p_free_array");
}
