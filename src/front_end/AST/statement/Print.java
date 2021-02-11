package front_end.AST.statement;

import front_end.AST.expression.ExpressionAST;
import org.antlr.v4.runtime.ParserRuleContext;

public class Print extends Statement {
  private ExpressionAST expression;

  public Print(ParserRuleContext ctx, ExpressionAST expression) {
    super(ctx);
    this.expression = expression;
  }

  @Override
  public void check() {
    expression.check();
  }
}
