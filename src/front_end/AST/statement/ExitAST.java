package front_end.AST.statement;

import front_end.AST.expression.ExpressionAST;
import front_end.Visitor;
import front_end.types.IDENTIFIER;
import org.antlr.v4.runtime.ParserRuleContext;

public class ExitAST extends StatementAST {
  private ExpressionAST expression;

  public ExitAST(ParserRuleContext ctx, ExpressionAST expression) {
    super(ctx);
    this.expression = expression;
  }

  @Override
  public void check() {
    expression.check();
    IDENTIFIER intIdent = Visitor.ST.lookupAll("int");
    if(expression.getEvalType() == null || !expression.getEvalType().equalsType(intIdent.getType())) {
      error("Exit code must be an integer");
    }

  }
}
