package front_end.AST.expression;

import front_end.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class IdentAST extends ExpressionAST {

  private final String identName;

  public IdentAST(ParserRuleContext ctx, String identName) {
    super(ctx);
    this.identName = identName;
  }

  @Override
  public void check() {
    identObj = Visitor.ST.lookupAll(identName);
    if (identObj == null) {
      error(identName + " has not been previously defined.");
    }
  }

  public String getIdentName() {
    return identName;
  }
}
