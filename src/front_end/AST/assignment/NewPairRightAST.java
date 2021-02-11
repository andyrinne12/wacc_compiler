package front_end.AST.assignment;

import front_end.types.PAIR;
import front_end.types.TYPE;
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
    identObj = new PAIR(expr1.getEvalType(), expr2.getEvalType());
  }

  @Override
  public TYPE getEvalType() {
    return identObj.getType();
  }
}
