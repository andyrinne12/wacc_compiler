package front_end.AST.assignment;

import front_end.AST.expression.ExpressionAST;
import front_end.types.TYPE;
import org.antlr.v4.runtime.ParserRuleContext;

public class ExprRightAST extends AssignmentRightAST {

  public final ExpressionAST expr;

  public ExprRightAST(ParserRuleContext ctx, ExpressionAST expr) {
    super(ctx);
    this.expr = expr;
  }

  @Override
  public void check() {
    expr.check();
  }

  @Override
  public TYPE getEvalType() {
    return expr.getIdentObj().getType();
  }
}
