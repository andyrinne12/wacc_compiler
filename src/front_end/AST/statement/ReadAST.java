package front_end.AST.statement;

import front_end.AST.assignment.AssignmentLeftAST;
import front_end.Visitor;
import front_end.types.IDENTIFIER;
import front_end.types.TYPE;
import org.antlr.v4.runtime.ParserRuleContext;

public class ReadAST extends StatementAST {

  private AssignmentLeftAST expr;

  public ReadAST(ParserRuleContext ctx, AssignmentLeftAST expr) {
    super(ctx);
    this.expr = expr;
  }

  @Override
  public void check() {
    expr.check();
    TYPE evalType = expr.getEvalType();

    if (evalType == null) {
      error("cannot read on null reference");
    } else {

      IDENTIFIER intType = Visitor.ST.lookupAll("int");

      IDENTIFIER charType = Visitor.ST.lookupAll("char");

      if (!evalType.equalsType(intType.getType()) && !evalType.equalsType(charType.getType())) {
        error("read statement can take only int or char types");
      }
    }
  }
}
