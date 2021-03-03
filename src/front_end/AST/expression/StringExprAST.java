package front_end.AST.expression;

import front_end.Visitor;
import front_end.types.TYPE;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import back_end.CodeGen;
import back_end.FunctionBody;
import back_end.instructions.store.LDR;
import back_end.operands.immediate.ImmString;
import back_end.operands.registers.Register;

public class StringExprAST extends ExpressionAST {

  private String strVal;

  public StringExprAST(ParserRuleContext ctx, String strVal) {
    super(ctx);
    this.strVal = strVal;
  }

  @Override
  public void check() {
    identObj = Visitor.ST.lookupAll("string");
    if (identObj == null) {
      error("Undefined type: string");
    }
  }

  @Override
  public TYPE getEvalType() {
    return identObj.getType();
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    CodeGen.addData(strVal);
    ImmString label = CodeGen.textData.get(CodeGen.textData.size() - 1);
    body.addInstr(new LDR(freeRegs.get(0), label));
  }
}
