package front_end.AST.assignment;

import antlr.WACCParser.IdentLHSContext;
import front_end.Visitor;
import front_end.types.ARRAY;
import front_end.types.FUNCTION;
import front_end.types.TYPE;

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
      error(identName + "has not been previously defined.");
    }
    if (identObj instanceof ARRAY) {
      error("Array variables cannot be directly assigned to.");
    }
    if (identObj instanceof FUNCTION) {
      error("Function identifier on the left hand side of the assignment");
    }
  }

  @Override
  public String toString() {
    return identName;
  }

  @Override
  public TYPE getEvalType() {
    return identObj.getType();
  }
}
