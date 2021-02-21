package front_end.AST.statement;

import front_end.AST.expression.ExpressionAST;
import org.antlr.v4.runtime.ParserRuleContext;

public class PrintlnAST extends StatementAST {
  private ExpressionAST expression;

  public PrintlnAST(ParserRuleContext ctx, ExpressionAST expression) {
    super(ctx);
    this.expression = expression;
  }

  @Override
  public void check() {
    expression.check();
  }
}
