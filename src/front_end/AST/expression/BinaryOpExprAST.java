package front_end.AST.expression;

import static java.util.Objects.isNull;

import back_end.CodeGen;
import front_end.Visitor;
import front_end.types.*;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

import back_end.FunctionBody;
import back_end.Utils;
import back_end.instructions.Condition;
import back_end.instructions.arithmetic.*;
import back_end.instructions.branch.BL;
import back_end.instructions.logical.*;
import back_end.operands.immediate.ImmInt;
import back_end.operands.registers.*;

public class BinaryOpExprAST extends ExpressionAST {

  private String binaryOp;
  private ExpressionAST expr1;
  private ExpressionAST expr2;
  private List<TYPE> expectedElemTypes;
  private String returnType;

  private final int SHIFT_VALUE = 31;
  protected static boolean overflow;

  public BinaryOpExprAST(ParserRuleContext ctx, ExpressionAST expr1, ExpressionAST expr2,
      String binaryOp) {
    super(ctx);
    this.expr1 = expr1;
    this.expr2 = expr2;
    this.binaryOp = binaryOp;

    expectedElemTypes = new ArrayList<>();
    initialise_attr();
    overflow = false;
  }

  @Override
  public void check() {
    identObj = Visitor.ST.lookupAll(returnType);
    expr1.check();
    expr2.check();

    // if either of the 2 identifiers hasn't been previously defined:
    if ((expr1.getIdentObj() == null) || (expr2.getIdentObj() == null)) {
      return;
    }

    TYPE type1 = expr1.getEvalType();
    boolean expr1CorrectType = false;

    for (TYPE t : expectedElemTypes) {
      if (type1.equalsType(t)) {
        expr1CorrectType = true;
        break;
      }
    }

    if (expr1CorrectType) {
      TYPE type2 = expr2.getEvalType();
      if (!type1.equalsType(type2)) {
        error("Both LHS and RHS expressions have different types." +
            "\nExpected: " + expr1.getEvalType() +
            "\nActual: " + expr2.getEvalType());
      }
    } else {
      String expectedTypesInString = getTypesString();

      error(
          "The binary operator " + binaryOp + " received an unexpected type for the LHS expression."
              +
              "\nExpected types: {" + expectedTypesInString + "}" +
              "\nActual: " + type1);
    }

  }

  private String getTypesString() {
    List<String> listOfTypes = new ArrayList<>();

    for (TYPE t : expectedElemTypes) {
      listOfTypes.add((t.getClass().getName()));
    }

    return String.join(", ", listOfTypes);
  }

  private void initialise_attr() {
    TYPE intIdent = Visitor.ST.lookupAll("int").getType();
    TYPE boolIdent = Visitor.ST.lookupAll("bool").getType();
    TYPE charIdent = Visitor.ST.lookupAll("char").getType();
    TYPE strIdent = Visitor.ST.lookupAll("string").getType();
    switch (binaryOp) {
      case "+":
      case "-":
      case "*":
      case "/":
      case "%":
        expectedElemTypes.add(intIdent);
        returnType = "int";
        break;
      case ">":
      case ">=":
      case "<":
      case "<=":
        expectedElemTypes.add(intIdent);
        expectedElemTypes.add(charIdent);
        returnType = "bool";
        break;
      case "==":
      case "!=":
        expectedElemTypes.add(intIdent);
        expectedElemTypes.add(charIdent);
        expectedElemTypes.add(strIdent);
        expectedElemTypes.add(boolIdent);
        expectedElemTypes
            .add(new PAIR(null, null)); // constructor params are not important in this scenario.
        expectedElemTypes
            .add(new ARRAY(null, 0)); // constructor params are not important in this scenario.
        returnType = "bool";
        break;
      case "||":
      case "&&":
        expectedElemTypes.add(boolIdent);
        returnType = "bool";
        break;
    }
  }

  @Override
  public TYPE getEvalType() {
    return identObj.getType();
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    expr1.assemble(body, freeRegs);
    Register lhsReg = freeRegs.get(0);

    List<Register> freeRegs1 = freeRegs.subList(1, freeRegs.size());
    expr2.assemble(body, freeRegs1);
    Register rhsReg = freeRegs1.get(0);

    switch (binaryOp) {
      case "+":
        body.addInstr(new ADD(false, lhsReg, lhsReg, rhsReg));
        body.addInstr(new BL(Condition.VS, "p_throw_overflow_error"));
        Utils.addFunc("p_integer_overflow", null); // p_throw_overflow_error doesn't need a register.
        break;
      case "-":
        body.addInstr(new SUB(false, lhsReg, lhsReg, rhsReg));
        body.addInstr(new BL(Condition.VS, "p_throw_overflow_error"));
        Utils.addFunc("p_integer_overflow", null);
        break;
      case "*":
        body.addInstr(new SMULL(lhsReg, rhsReg, lhsReg, rhsReg));
        body.addInstr(new CMP(rhsReg, new ShiftedRegister(lhsReg, Shift.ASR, new ImmInt(SHIFT_VALUE))));
        body.addInstr(new BL(Condition.NE, "p_throw_overflow_error"));

        if(!overflow) {
          CodeGen.addData("OverflowError: the result is too small/large to store in a" +
              "4-byte signed-integer.\\n");
          Utils.addFunc("p_integer_overflow", lhsReg);
          overflow = true;
        }
        break;
      case "/":
        // for divide, we make use of an external ARM API function called __aeabi_idiv. 
        // It divides the value stored in R0 by the value stored in R1.
        Register R0 = RegisterManager.getParamRegs().get(0);
        Register R1 = RegisterManager.getParamRegs().get(1);
        body.addInstr(new MOV(R0, lhsReg));
        body.addInstr(new MOV(R1, rhsReg));
        body.addInstr(new BL(Condition.NONE, "p_divide_by_zero"));
        body.addInstr(new BL(Condition.NONE, "__aeabi_idiv"));
        body.addInstr(new MOV(lhsReg, R0)); // obtain the result of the division from R0, and put it into lhsReg.
        Utils.addFunc("p_divide_by_zero", null);
        break;
      case "%":
        // for mod, we make use of an external ARM API function called __aeabi_idivmod. 
        R0 = RegisterManager.getParamRegs().get(0);
        R1 = RegisterManager.getParamRegs().get(1);
        body.addInstr(new MOV(R0, lhsReg));
        body.addInstr(new MOV(R1, rhsReg));
        body.addInstr(new BL(Condition.NONE, "p_divide_by_zero"));
        body.addInstr(new BL(Condition.NONE, "__aeabi_idivmod"));
        body.addInstr(new MOV(lhsReg, R1)); // obtain the result of the mod from R1, and put it into lhsReg.
        Utils.addFunc("p_divide_by_zero", null);
        break;
      case ">":
        body.addInstr(new CMP(lhsReg, rhsReg));
        body.addInstr(new MOV(Condition.GT, false, lhsReg, new ImmInt(1)));
        body.addInstr(new MOV(Condition.LE, false, lhsReg, new ImmInt(0)));
        break;
      case ">=":
        body.addInstr(new CMP(lhsReg, rhsReg));
        body.addInstr(new MOV(Condition.GE, false, lhsReg, new ImmInt(1)));
        body.addInstr(new MOV(Condition.LT, false, lhsReg, new ImmInt(0)));
        break;
      case "<":
        body.addInstr(new CMP(lhsReg, rhsReg));
        body.addInstr(new MOV(Condition.LT, false, lhsReg, new ImmInt(1)));
        body.addInstr(new MOV(Condition.GE, false, lhsReg, new ImmInt(0)));
        break;
      case "<=":
        body.addInstr(new CMP(lhsReg, rhsReg));
        body.addInstr(new MOV(Condition.LE, false, lhsReg, new ImmInt(1)));
        body.addInstr(new MOV(Condition.GT, false, lhsReg, new ImmInt(0)));
        break;
      case "==":
        body.addInstr(new CMP(lhsReg, rhsReg));
        body.addInstr(new MOV(Condition.EQ, false, lhsReg, new ImmInt(1)));
        body.addInstr(new MOV(Condition.NE, false, lhsReg, new ImmInt(0)));
        break;
      case "!=":
        body.addInstr(new CMP(lhsReg, rhsReg));
        body.addInstr(new MOV(Condition.NE, false, lhsReg, new ImmInt(1)));
        body.addInstr(new MOV(Condition.EQ, false, lhsReg, new ImmInt(0)));
        break;
      case "&&":
        body.addInstr(new AND(lhsReg, lhsReg, rhsReg));
        break;
      case "||":
        body.addInstr(new OR(lhsReg, lhsReg, rhsReg));
        break;
    }
  }

  public Boolean booleanTranslation() {
    Boolean result = null;
    Integer rhs = null;
    Integer lhs = null;
    if (expr1 instanceof BinaryOpExprAST) {
      if (((BinaryOpExprAST) expr1).returnType.equals("bool")) {
        lhs = ((BinaryOpExprAST) expr1).booleanTranslation() ? 1 : 0;
      } else { // return type is int
        lhs = ((BinaryOpExprAST) expr1).integerTranslation();
      }
    } else if (expr1 instanceof SignedIntExprAST) {
      lhs = ((SignedIntExprAST) expr1).getValue();
    } else if (expr1 instanceof BoolExprAST) {
      lhs = ((BoolExprAST) expr1).getBoolVal() ? 1 : 0;
    } else if (expr1 instanceof CharExprAST) {
      lhs = ((CharExprAST) expr1).getCharInt();
    }

    if (expr2 instanceof BinaryOpExprAST) {
      if (((BinaryOpExprAST) expr2).returnType.equals("bool")) {
        if (!isNull(lhs)) {
          rhs = ((BinaryOpExprAST) expr2).booleanTranslation() ? 1 : 0;
        }
      } else {
        rhs = ((BinaryOpExprAST) expr2).integerTranslation();
      }
    } else if (expr2 instanceof SignedIntExprAST) {
      rhs = ((SignedIntExprAST) expr2).getValue();
    } else if (expr2 instanceof BoolExprAST) {
      rhs = ((BoolExprAST) expr2).getBoolVal() ? 1 : 0;
    } else if (expr2 instanceof CharExprAST) {
      rhs = ((CharExprAST) expr2).getCharInt();
    }

    if (lhs != null && rhs != null) {
      switch (binaryOp) {
        case ">":
          result = lhs > rhs;
          break;
        case ">=":
          result = lhs >= rhs;
          break;
        case "<":
          result = lhs < rhs;
          break;
        case "<=":
          result = lhs <= rhs;
          break;
        case "==":
          result = lhs.equals(rhs);
          break;
        case "!=":
          result = !lhs.equals(rhs);
          break;
        case "&&":
          result = (lhs == 1) && (rhs == 1);
          break;
        case "||":
          result = (lhs == 1) || (rhs == 1);
          break;
      }
    }
    return result;
  }

  private Integer integerTranslation() {
    Integer result = null;
    Integer rhs = null;
    Integer lhs = null;
    if(expr1 instanceof BinaryOpExprAST) {
      lhs = ((BinaryOpExprAST) expr1).integerTranslation();
    } else if(expr1 instanceof SignedIntExprAST) {
      lhs = ((SignedIntExprAST) expr1).getValue();
    }

    if(expr2 instanceof BinaryOpExprAST) {
      rhs = ((BinaryOpExprAST) expr2).integerTranslation();
    } else if(expr2 instanceof SignedIntExprAST) {
      rhs = ((SignedIntExprAST) expr2).getValue();
    }

    if(lhs != null && rhs != null) {
      switch(binaryOp) {
        case "+":
          result = lhs + rhs;
          break;
        case "-":
          result = lhs - rhs;
          break;
        case "*":
          result = lhs * rhs;
          break;
        case "/":
          if(rhs != 0) {
            result = lhs / rhs;
          }
          break;
        case "%":
          if(rhs != 0) {
            result = lhs % rhs;
          }
          break;
      }

    }
    return result;
  }
}
