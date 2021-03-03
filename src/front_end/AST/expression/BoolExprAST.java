package front_end.AST.expression;

import front_end.Visitor;
import front_end.types.TYPE;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import back_end.FunctionBody;
import back_end.instructions.logical.MOV;
import back_end.operands.immediate.ImmInt;
import back_end.operands.registers.Register;

public class BoolExprAST extends ExpressionAST {

  // syntactic attr:
  private final boolean boolVal;

  public BoolExprAST(ParserRuleContext ctx, String boolString) {
    super(ctx);
    boolVal = boolString.equals("true");
  }

  @Override
  public void check() {
    identObj = Visitor.ST.lookupAll("bool");
    if (identObj == null) {
      error("Undefined type: bool");
    }

  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    if (boolVal) {
      body.addInstr(new MOV(freeRegs.get(0), new ImmInt(true)));
    }
    else {
      body.addInstr(new MOV(freeRegs.get(0), new ImmInt(false)));
    }
  }

  @Override
  public TYPE getEvalType() {
    return identObj.getType();
  }

  public boolean getBoolVal() {
    return boolVal;
  }
}
