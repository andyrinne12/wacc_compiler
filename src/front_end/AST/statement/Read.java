package front_end.AST.statement;

import front_end.AST.assignment.AssignmentLeftAST;
import front_end.Visitor;
import front_end.types.IDENTIFIER;
import front_end.types.TYPE;
import org.antlr.v4.runtime.ParserRuleContext;

public class Read extends Statement{
  private AssignmentLeftAST expr;

  public Read(ParserRuleContext ctx, AssignmentLeftAST expr) {
    super(ctx);
    this.expr = expr;
  }

  @Override
  public void check() {
    expr.check();
    IDENTIFIER type = expr.getType();

    IDENTIFIER intType = Visitor.ST.lookupAll("int").getType();

    IDENTIFIER charType = Visitor.ST.lookupAll("char").getType();

    if (!type.equals(intType) && !type.equals(charType)) {
      error("read statement can take only int or char types");
    }
  }
}
