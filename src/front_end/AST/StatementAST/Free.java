package front_end.AST.StatementAST;

import front_end.AST.ExpressionAST.ExpressionAST;
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
    if(!(expression.getType() instanceof PAIR)) {
      error("the only expression that can be freed is a pair type");
    }
  }
}
