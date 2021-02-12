package front_end.AST.statement;

import front_end.AST.expression.ExpressionAST;
import front_end.Visitor;
import front_end.types.IDENTIFIER;
import org.antlr.v4.runtime.ParserRuleContext;

public class Exit extends Statement{
  private ExpressionAST expression;

  public Exit(ParserRuleContext ctx, ExpressionAST expression) {
    super(ctx);
    this.expression = expression;
  }

  @Override
  public void check() {
    expression.wasChecked();
    IDENTIFIER intIdent = Visitor.ST.lookupAll("int");
    if(!expression.getEvalType().equalsType(intIdent.getType())) {
      error("Exit code must be an integer. Actual type:" + expression.getEvalType());
    }

  }
}
