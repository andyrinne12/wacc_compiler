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
import back_end.operands.immediate.ImmString;
import back_end.operands.registers.OffsetRegister;
import back_end.operands.registers.Register;
import back_end.operands.registers.RegisterManager;
import java.util.Map;

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
    for (Map.Entry<String, Register> entry : CodeGen.lastFuncs.entrySet()) {
      switch (entry.getKey()) {
        case "p_print_bool":
          CodeGen.funcBodies.add(printBool());
          break;
        case "p_print_string":
          CodeGen.funcBodies.add(printString());
          break;
        case "p_print_int":
          CodeGen.funcBodies.add(printInt());
          break;
        case "p_print_reference":
          CodeGen.funcBodies.add(printReference());
          break;
        case "p_print_ln":
          CodeGen.funcBodies.add(printlnInstr());
          break;
        case "p_read_int":
          CodeGen.funcBodies.add(printRead("int", entry.getValue()));
          break;
        case "p_read_char":
          CodeGen.funcBodies.add(printRead("char", entry.getValue()));
          break;
        case "p_check_array_bounds":
          CodeGen.funcBodies.add(p_check_array_bounds());
          break;
        case "p_check_null_pointer":
          CodeGen.funcBodies.add(p_check_null_pointer());
          break;
        case "p_divide_by_zero":
          CodeGen.funcBodies.add(p_divide_by_zero(entry.getValue()));
          break;
        case "p_integer_overflow":
          CodeGen.funcBodies.add(p_integer_overflow());
          break;
        case "p_throw_runtime_error":
          CodeGen.funcBodies.add(p_throw_runtime_error());
          break;
        case "p_free_pair":
          CodeGen.funcBodies.add(p_free_pair());
          break;
        case "p_free_array":
          CodeGen.funcBodies.add(p_free_array());
          break;
      }
    }
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
    FunctionBody printBool = new FunctionBody("print_bool", false, true, false);
    printBool.addInstr(new PUSH(RegisterManager.LR));
    printBool.addInstr(new CMP(RegisterManager.getResultReg(), new ImmInt(0)));
    printBool.addInstr(new LDR(Condition.NE, RegisterManager.getResultReg(), CodeGen.checkTrueFormat()));
    printBool.addInstr(new LDR(Condition.EQ, RegisterManager.getResultReg(), CodeGen.checkFalseFormat()));
    printLast(printBool);
    return printBool;
  }

  public static FunctionBody printString() {
    FunctionBody printString = new FunctionBody("print_string", false, true, false);
    printString.addInstr(new PUSH(RegisterManager.LR));
    printString.addInstr(new LDR(RegisterManager.getParamRegs().get(1),
        new OffsetRegister(RegisterManager.getResultReg())));
    printString
        .addInstr(new ADD(false, new Register(2), RegisterManager.getResultReg(), new ImmInt(4)));
    printString.addInstr(new LDR(RegisterManager.getResultReg(), CodeGen.checkStrFormat()));
    printLast(printString);
    return printString;
  }

  public static FunctionBody printInt() {
    FunctionBody printInt = new FunctionBody("print_int", false, true, false);
    printInt.addInstr(new PUSH(RegisterManager.LR));
    printInt
        .addInstr(new MOV(RegisterManager.getParamRegs().get(1), RegisterManager.getResultReg()));
    printInt.addInstr(new LDR(RegisterManager.getResultReg(), CodeGen.checkIntFormat()));
    printLast(printInt);
    return printInt;
  }

  public static FunctionBody printRead(String type, Register register) {
    FunctionBody printRead = new FunctionBody("read" + type, false, true, true);
    printRead.addInstr(new MOV(RegisterManager.getResultReg(), register));
    printRead.addInstr(new LDR(RegisterManager.getResultReg(),CodeGen.checkIntFormat()));

    printRead.addInstr(new ADD(false, RegisterManager.getResultReg(), RegisterManager.getResultReg(), new ImmInt(4)));
    printRead.addInstr(new BL(Condition.NONE, "scanf"));
    return printRead;
  }

  public static FunctionBody printlnInstr() {
    FunctionBody printlnInstr = new FunctionBody("print_ln", false, true, true);
    printlnInstr.addInstr(new LDR(RegisterManager.getResultReg(), CodeGen.checkEmptyFormat()));
    printlnInstr.addInstr(new ADD(false, RegisterManager.getResultReg(), RegisterManager.getResultReg(), new ImmInt(4)));
    printlnInstr.addInstr(new BL(Condition.NONE, "puts"));
    printlnInstr.addInstr(new MOV(RegisterManager.getResultReg(), new ImmInt(0)));
    printlnInstr.addInstr(new BL(Condition.NONE, "fflush"));
    return printlnInstr;
  }

  public static FunctionBody printReference() {
    FunctionBody printlnReference = new FunctionBody("print_reference", false, true, false);
    printlnReference.addInstr(new PUSH(RegisterManager.LR));
    printlnReference.addInstr(new MOV(RegisterManager.getParamRegs().get(1), RegisterManager.getResultReg()));
    printlnReference.addInstr(new LDR(RegisterManager.getResultReg(), CodeGen.checkRefFormat()));
    printLast(printlnReference);
    return printlnReference;
  }

  public static FunctionBody p_check_null_pointer() {
    FunctionBody checkNullPointer = new FunctionBody("check_null_pointer", false, true, true);
    checkNullPointer.addInstr(new CMP(RegisterManager.getResultReg(), new ImmInt(0)));
    checkNullPointer.addInstr(new LDR(Condition.EQ, RegisterManager.getResultReg(), CodeGen.addData("NullReferenceError: dereference a null reference\\n\\0")));
    checkNullPointer.addInstr(new BL(Condition.EQ, "p_throw_runtime_error"));
    return checkNullPointer;
  }

  public static FunctionBody p_throw_runtime_error() {
    FunctionBody runtimeError = new FunctionBody("throw_runtime_error", false, true, true);
    runtimeError.addInstr(new BL(Condition.NONE, "p_print_string"));
    runtimeError.addInstr(new MOV(RegisterManager.getResultReg(), new ImmInt(EXIT_CODE)));
    runtimeError.addInstr(new BL(Condition.NONE, "exit"));
    return runtimeError;
  }

  public static FunctionBody p_check_array_bounds() {
    FunctionBody checkBound = new FunctionBody("check_array_bounds", false, true, true);

    checkBound.addInstr(new CMP(RegisterManager.getResultReg(), new ImmInt(0)));
    checkBound.addInstr(new LDR(Condition.LT, RegisterManager.getResultReg(), CodeGen.addData("ArrayIndexOutOfBoundsError: negative index\\n\\0")));
    checkBound.addInstr(new BL(Condition.LT, "p_throw_runtime_error"));
    checkBound.addInstr(new LDR(RegisterManager.getParamRegs().get(1), new OffsetRegister(RegisterManager.getParamRegs().get(1))));

    checkBound.addInstr(new CMP(RegisterManager.getResultReg(), RegisterManager.getParamRegs().get(1)));
    checkBound.addInstr(new LDR(Condition.CS, RegisterManager.getResultReg(), CodeGen.addData("ArrayIndexOutOfBoundsError: index too large\\n\\0")));
    checkBound.addInstr(new BL(Condition.CS, "p_throw_runtime_error"));
    return checkBound;
  }

  public static FunctionBody p_divide_by_zero(Register register) {
    FunctionBody checkDivide = new FunctionBody("check_divide_by_zero", false, true, true);
    checkDivide.addInstr(new CMP(register, new ImmInt(0)));
    checkDivide.addInstr(new LDR(Condition.EQ, RegisterManager.getResultReg(), CodeGen.addData("DivideByZeroError: divide or modulo by zero\\n\\0")));
    checkDivide.addInstr(new BL(Condition.EQ, "p_throw_runtime_error"));
    return checkDivide;
  }

  public static FunctionBody p_integer_overflow() {
    FunctionBody overflow = new FunctionBody("throw_overflow_error", false, true, true);
    overflow.addInstr(new LDR(RegisterManager.getResultReg(), CodeGen.addData("OverflowError: the result is too small/large to store in a " +
        "4-byte signed-integer.\\\\n")));
    overflow.addInstr(new BL(Condition.NONE, "p_throw_runtime_error"));
    return overflow;
  }

  private static void p_free(FunctionBody f) {
    f.addInstr(new CMP(RegisterManager.getResultReg(), new ImmInt(0)));
    f.addInstr(new LDR(Condition.EQ, RegisterManager.getResultReg(), CodeGen.addData("NullReferenceError: dereference a null reference\\n\\0")));
    f.addInstr(new BL(Condition.EQ, "p_throw_runtime_error"));
  }


  public static FunctionBody p_free_pair() {
    FunctionBody freePair = new FunctionBody("free_pair", false, true, true);
    p_free(freePair);
    freePair.addInstr(new LDR(RegisterManager.getResultReg(), new OffsetRegister(RegisterManager.getResultReg())));
    freePair.addInstr(new BL(Condition.NONE, "free"));
    freePair.addInstr(new LDR(RegisterManager.getResultReg(), new OffsetRegister(RegisterManager.SP)));
    freePair.addInstr(new LDR(RegisterManager.getResultReg(), new OffsetRegister(RegisterManager.getResultReg(),4)));
    freePair.addInstr(new BL(Condition.NONE, "free"));
    freePair.addInstr(new POP(RegisterManager.getResultReg()));
    freePair.addInstr(new BL(Condition.NONE, "free"));
    return freePair;
  }

  public static FunctionBody p_free_array() {
    FunctionBody freeArray = new FunctionBody("free_array", false, true, true);
    p_free(freeArray);
    freeArray.addInstr(new BL(Condition.NONE, "free"));
    return freeArray;
  }

  public static void addFunc(String s, Register r) {
    if (!CodeGen.lastFuncs.containsKey(s)) {
      CodeGen.lastFuncs.put(s, r);
    }
  }
}
