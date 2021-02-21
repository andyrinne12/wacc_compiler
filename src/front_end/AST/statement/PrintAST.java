package front_end.AST.statement;

import front_end.AST.expression.ExpressionAST;
import org.antlr.v4.runtime.ParserRuleContext;

public class PrintAST extends StatementAST {
  private ExpressionAST expression;

  public PrintAST(ParserRuleContext ctx, ExpressionAST expression) {
    super(ctx);
    this.expression = expression;
  }

  @Override
  public void check() {
    expression.check();
  }
}
