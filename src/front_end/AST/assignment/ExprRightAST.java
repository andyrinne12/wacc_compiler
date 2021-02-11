package front_end.AST.assignment;

import front_end.AST.expression.ExpressionAST;
import org.antlr.v4.runtime.ParserRuleContext;

public class ExprRightAST extends AssignmentRightAST {

  private final ExpressionAST expr;

  public ExprRightAST(ParserRuleContext ctx, ExpressionAST expr) {
    super(ctx);
    this.expr = expr;
  }

  @Override
  public void check() {
    expr.check();
  }

  public ExpressionAST getExpr() {
    return expr;
  }
}