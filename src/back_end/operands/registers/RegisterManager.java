package back_end.operands.registers;

import java.util.Arrays;
import java.util.List;

public class RegisterManager {

  /* Special registers */
  public static final Register SP = new Register(13); // stack pointer
  public static final Register LR = new Register(14); // link register
  public static final Register PC = new Register(15); // program counter

  /* Function parameters */

  private static final Register R0 = new Register(0);
  private static final Register R1 = new Register(1);
  private static final Register R2 = new Register(2);
  private static final Register R3 = new Register(3);

  /* Local variables */
  private static final Register R4 = new Register(4);
  private static final Register R5 = new Register(5);
  private static final Register R6 = new Register(6);
  private static final Register R7 = new Register(7);
  private static final Register R8 = new Register(8);
  private static final Register R9 = new Register(9);
  private static final Register R10 = new Register(10);
  private static final Register R11 = new Register(11);
  private static final Register R12 = new Register(12);

  public static List<Register> getLocalRegs() {
    return Arrays.asList(R4, R5, R6, R7, R8, R9, R10, R11, R12);
  }

  public static List<Register> getParamRegs() {
    return Arrays.asList(R0, R1, R2, R3);
  }

  public static Register getResultReg() {
    return R0;
  }

}
