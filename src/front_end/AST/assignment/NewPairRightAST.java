package front_end.AST.assignment;

import org.antlr.v4.runtime.ParserRuleContext;

public class NewPairRightAST extends AssignmentRightAST {

  private final ExprRightAST expr1;
  private final ExprRightAST expr2;

  public NewPairRightAST(ParserRuleContext ctx, ExprRightAST expr1, ExprRightAST expr2) {
    super(ctx);
    this.expr1 = expr1;
    this.expr2 = expr2;
  }


  @Override
  public void check() {
    expr1.check();
    expr2.check();
  }

  public ExprRightAST getExpr1() {
    return expr1;
  }

  public ExprRightAST getExpr2() {
    return expr2;
  }
}
