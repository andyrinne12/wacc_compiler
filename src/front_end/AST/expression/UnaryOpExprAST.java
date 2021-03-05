package front_end.AST.expression;

import front_end.Visitor;
import front_end.types.ARRAY;
import front_end.types.TYPE;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import back_end.FunctionBody;
import back_end.Utils;
import back_end.instructions.Condition;
import back_end.instructions.arithmetic.RSBS;
import back_end.instructions.branch.BL;
import back_end.instructions.logical.EOR;
import back_end.instructions.store.LDR;
import back_end.operands.immediate.ImmInt;
import back_end.operands.registers.OffsetRegister;
import back_end.operands.registers.Register;

public class UnaryOpExprAST extends ExpressionAST {

  private String unaryOp;
  private ExpressionAST expression;
  private TYPE expectedElemType;
  private String returnType;

  public UnaryOpExprAST(ParserRuleContext ctx, ExpressionAST expression, String unaryOp) {
    super(ctx);
    this.expression = expression;
    this.unaryOp = unaryOp;

    initialise_attr();
  }

  @Override
  public void check() {
    identObj = Visitor.ST.lookupAll(returnType);
    expression.check();
    if (expression.getIdentObj() == null) {
      return;
    }

    // check if the unary operator is compatible with the expression.
    TYPE type = expression.getEvalType();
    if (!type.equalsType(expectedElemType)) {
      error("The unary operator " + unaryOp + " received an unexcpeted type." +
          "expected: " + expectedElemType +
          "actual: " + type);
    }
  }

  private void initialise_attr() {
    TYPE intIdent = Visitor.ST.lookupAll("int").getType();
    TYPE boolIdent = Visitor.ST.lookupAll("bool").getType();
    TYPE charIdent = Visitor.ST.lookupAll("char").getType();
    switch (unaryOp) {
      case "!":
        expectedElemType = boolIdent;
        returnType = "bool";
        break;
      case "-":
        expectedElemType = intIdent;
        returnType = "int";
        break;
      case "len":
        expectedElemType = new ARRAY(null,
            0); // constructor params are not important in this scenario.
        returnType = "int";
        break;
      case "ord":
        expectedElemType = charIdent;
        returnType = "int";
        break;
      case "chr":
        expectedElemType = intIdent;
        returnType = "char";
    }
  }


  @Override
  public TYPE getEvalType() {
    return identObj.getType();
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    expression.assemble(body, freeRegs);

    // get the register that is holding the result of expression.
    Register reg = freeRegs.get(0);

    switch(unaryOp) {
      case "!":
        // to obtain the effect of NOT, we use Exclusive OR along with boolean true.
        body.addInstr(new EOR(reg, reg, new ImmInt(true)));
        break;
      case "-":
        body.addInstr(new RSBS(false, reg, reg));
        body.addInstr(new BL(Condition.VS, "p_throw_overflow_error"));
        Utils.addFunc("p_integer_overflow", null);
        break;
      case "len":
        body.addInstr(new LDR(Condition.NONE, reg, new OffsetRegister(reg)));
        break;
      case "ord": 
        // Do nothing
        break;
      case "chr":
        // Do nothing
        break;
    }
  }
}
