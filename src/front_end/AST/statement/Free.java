package front_end.AST.statement;

import front_end.AST.expression.ExpressionAST;
import front_end.types.PAIR;
import org.antlr.v4.runtime.ParserRuleContext;

public class Free extends Statement{
  private ExpressionAST expression;

  public Free(ParserRuleContext ctx, ExpressionAST expression) {
    super(ctx);
    this.expression = expression;
  }

  @Override
  public void check() {
    expression.wasChecked();
    if(!(expression.getIdentObj() instanceof PAIR)) {
      error("The variable you are trying to free is not a pair: " + expression.getEvalType());
    }
  }
}
