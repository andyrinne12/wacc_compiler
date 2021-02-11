package front_end.AST.statement;

import front_end.AST.ExpressionAST.ExpressionAST;
import org.antlr.v4.runtime.ParserRuleContext;

public class Println extends Statement {
  private ExpressionAST expression;
  private Print printAST;

  public Println(ParserRuleContext ctx, ExpressionAST expression) {
    super(ctx);
    this.expression = expression;
  }

  @Override
  public void check() {
    expression.wasChecked();
  }
}
