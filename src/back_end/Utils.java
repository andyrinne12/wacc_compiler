package back_end;

import back_end.instructions.Condition;
import back_end.instructions.Instruction;
import back_end.instructions.arithmetic.ADD;
import back_end.instructions.arithmetic.CMP;
import back_end.instructions.branch.BL;
import back_end.instructions.logical.MOV;
import back_end.instructions.store.LDR;
import back_end.instructions.store.POP;
import back_end.operands.immediate.ImmInt;
import back_end.operands.immediate.ImmString;
import back_end.operands.registers.OffsetRegister;
import back_end.operands.registers.Register;
import back_end.operands.registers.RegisterManager;

public class Utils {

  public static final int EXIT_CODE = -1;
  private static final ImmString MESSAGE = CodeGen.addData("UNDEFINED: IMPLEMENT ME PLS UwU");
  public static Instruction PUTCHAR = new BL(Condition.NONE, "putchar");
  public static Instruction MALLOC = new BL(Condition.NONE, "malloc");
  public static Instruction CHECK_ARRAY_BOUNDS = new BL(Condition.NONE, "p_check_array_bounds");
  public static Instruction CHECK_NULL_POINTER = new BL(Condition.NONE, "p_check_null_pointer");
  public static Instruction RUNTIME_ERROR = new BL(Condition.NONE, "p_throw_runtime_error");
  public static Instruction FREE_PAIR = new BL(Condition.NONE, "p_free_pair");
  public static Instruction FREE_ARRAY = new BL(Condition.NONE, "p_free_array");

  public static void printFunctions() {

  }

  private static void printLast(FunctionBody body) {
    body.addInstr(new ADD(false, RegisterManager.getResultReg(), RegisterManager.getResultReg(),
        new ImmInt(4)));
    body.addInstr(new BL(Condition.NONE, "printf"));
    body.addInstr(new MOV(RegisterManager.getResultReg(), new ImmInt(0)));
    body.addInstr(new BL(Condition.NONE, "fflush"));
    body.addInstr(new POP(RegisterManager.PC));
  }

  public static FunctionBody printBool() {
    FunctionBody printBool = new FunctionBody("print_bool", false, true, true);
    printBool.addInstr(new CMP(RegisterManager.getResultReg(), new ImmInt(0)));
    printBool.addInstr(new LDR(Condition.NE, RegisterManager.getResultReg(), MESSAGE));
    printBool.addInstr(new LDR(Condition.EQ, RegisterManager.getResultReg(), MESSAGE));
    printLast(printBool);
    return printBool;
  }

  public static FunctionBody printString() {
    FunctionBody printString = new FunctionBody("print_string", false, true, true);
    printString.addInstr(new LDR(RegisterManager.getParamRegs().get(1),
        new OffsetRegister(RegisterManager.getResultReg())));
    printString
        .addInstr(new ADD(false, new Register(2), RegisterManager.getResultReg(), new ImmInt(4)));
    printString.addInstr(new LDR(RegisterManager.getResultReg(), MESSAGE));
    printLast(printString);
    return printString;
  }

  public static FunctionBody printInt() {
    FunctionBody printInt = new FunctionBody("print_int", false, true, true);
    printInt
        .addInstr(new MOV(RegisterManager.getParamRegs().get(1), RegisterManager.getResultReg()));
    printInt.addInstr(new LDR(RegisterManager.getResultReg(), MESSAGE));
    printLast(printInt);
    return printInt;
  }

}
