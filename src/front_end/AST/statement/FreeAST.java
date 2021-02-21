package front_end.AST.statement;

import front_end.AST.expression.ExpressionAST;
import front_end.types.PAIR;
import org.antlr.v4.runtime.ParserRuleContext;

public class FreeAST extends StatementAST {
  private ExpressionAST expression;

  public FreeAST(ParserRuleContext ctx, ExpressionAST expression) {
    super(ctx);
    this.expression = expression;
  }

  @Override
  public void check() {
    expression.check();
    if(!(expression.getIdentObj() instanceof PAIR)) {
      error("the only expression that can be freed is a pair type");
    }
  }
}
