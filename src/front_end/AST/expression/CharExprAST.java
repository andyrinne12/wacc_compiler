package front_end.AST.expression;

import front_end.Visitor;
import front_end.types.TYPE;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import back_end.FunctionBody;
import back_end.instructions.logical.MOV;
import back_end.operands.immediate.ImmInt;
import back_end.operands.registers.Register;

public class CharExprAST extends ExpressionAST {

  private String charVal;

  public CharExprAST(ParserRuleContext ctx, String charVal) {
    super(ctx);
    this.charVal = charVal;
  }

  @Override
  public void check() {
    identObj = Visitor.ST.lookupAll("char");
    if (identObj == null) {
      error("Undefined type: char");
    }
  }

  @Override
  public TYPE getEvalType() {
    return identObj.getType();
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    body.addInstr(new MOV(freeRegs.get(0), new ImmInt(charVal.charAt(0))));
  }
}
