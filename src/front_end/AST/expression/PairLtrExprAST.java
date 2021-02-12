package front_end.AST.expression;

import front_end.types.PAIR;
import front_end.types.TYPE;
import org.antlr.v4.runtime.ParserRuleContext;

public class PairLtrExprAST extends ExpressionAST {

  private String nullStr;

  public PairLtrExprAST(ParserRuleContext ctx, String str) {
    super(ctx);
    nullStr = str;
    identObj = new PAIR(null, null);
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
