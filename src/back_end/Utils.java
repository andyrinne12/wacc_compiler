package back_end;

import back_end.instructions.Condition;
import back_end.instructions.Instruction;
import back_end.instructions.arithmetic.ADD;
import back_end.instructions.arithmetic.CMP;
import back_end.instructions.branch.BL;
import back_end.instructions.logical.MOV;
import back_end.instructions.store.LDR;
import back_end.instructions.store.POP;
import back_end.instructions.store.PUSH;
import back_end.operands.immediate.ImmInt;
import back_end.operands.registers.OffsetRegister;
import back_end.operands.registers.Register;
import back_end.operands.registers.RegisterManager;

public class Utils {

  public static Instruction PUTCHAR = new BL(Condition.NONE, "putchar");
  public static Instruction MALLOC = new BL(Condition.NONE, "malloc");
  public static Instruction CHECK_ARRAY_BOUNDS = new BL(Condition.NONE, "p_check_array_bounds");
  public static Instruction CHECK_NULL_POINTER = new BL(Condition.NONE, "p_check_null_pointer");
  public static Instruction RUNTIME_ERROR = new BL(Condition.NONE, "p_throw_runtime_error");
  public static Instruction FREE_PAIR = new BL(Condition.NONE, "p_free_pair");
  public static Instruction FREE_ARRAY = new BL(Condition.NONE, "p_free_array");

  public static final int EXIT_CODE = -1;

  public static void printFunctions() {

  }

  private static void printLast() {
    FunctionBody.addInstr(new ADD(false, RegisterManager.getResultReg(), RegisterManager.getResultReg(), new ImmInt(4)));
    FunctionBody.addInstr(new BL(Condition.NONE, "printf"));
    FunctionBody.addInstr(new MOV(RegisterManager.getResultReg(), new ImmInt(0)));
    FunctionBody.addInstr(new BL(Condition.NONE, "fflush"));
    FunctionBody.addInstr(new POP(RegisterManager.PC));
  }

  public static void printBool() {
    FunctionBody.addInstr(new BL(Condition.NONE, "p_print_bool:"));
    FunctionBody.addInstr(new PUSH(RegisterManager.LR));
    FunctionBody.addInstr(new CMP(RegisterManager.getResultReg(), new ImmInt(0)));
    FunctionBody.addInstr(new LDR(Condition.NE, RegisterManager.getResultReg(), MESSAGE));
    FunctionBody.addInstr(new LDR(Condition.EQ, RegisterManager.getResultReg(), MESSAGE));
    printLast();
  }

  public static void printString() {
    FunctionBody.addInstr(new BL(Condition.NONE, "p_print_bool:"));
    FunctionBody.addInstr(new PUSH(RegisterManager.LR));
    FunctionBody.addInstr(new LDR(RegisterManager.getParamRegs().get(1), new OffsetRegister(RegisterManager.getResultReg())));
    FunctionBody.addInstr(new ADD(false, new Register(2), RegisterManager.getResultReg(), new ImmInt(4)));
    FunctionBody.addInstr(new LDR(RegisterManager.getResultReg(), MESSAGE));
    printLast();
  }

  public static void printInt() {
    FunctionBody.addInstr(new BL(Condition.NONE, "p_print_int"));
    FunctionBody.addInstr(new PUSH(RegisterManager.LR));
    FunctionBody.addInstr(new MOV(RegisterManager.getParamRegs().get(1), RegisterManager.getResultReg()));
    FunctionBody.addInstr(new LDR(RegisterManager.getResultReg(), MESSAGE));
    printLast();
  }

}
