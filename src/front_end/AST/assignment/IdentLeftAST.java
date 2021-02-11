package front_end.AST.assignment;

import antlr.WACCParser.IdentLHSContext;
import front_end.Visitor;

public class IdentLeftAST extends AssignmentLeftAST {

  private final String identName;

  public IdentLeftAST(IdentLHSContext ctx) {
    super(ctx);
    identName = ctx.IDENT().getText();
  }

  @Override
  public void check() {
    identObj = Visitor.ST.lookupAll(identName);
    if (identObj == null) {
      error("has not been previously defined.");
    }
  }
}
