package front_end.AST.expression;

import back_end.FunctionBody;
import back_end.instructions.store.LDR;
import back_end.operands.immediate.ImmInt;
import back_end.operands.registers.Register;
import front_end.types.PAIR;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class PairLtrExprAST extends ExpressionAST {

  private String nullStr;

  public PairLtrExprAST(ParserRuleContext ctx, String str) {
    super(ctx);
    nullStr = str;
    identObj = new PAIR(null, null);
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    body.addInstr(new LDR(freeRegs.get(0), new ImmInt(0)));
  }

  @Override
  public void check() {
    // to do
  }

  @Override
  public TYPE getEvalType() {
    return (TYPE) identObj;
  }
}
