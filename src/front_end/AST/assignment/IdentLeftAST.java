package front_end.AST.assignment;

import front_end.Visitor;
import front_end.types.FUNCTION;
import front_end.types.TYPE;
import org.antlr.v4.runtime.ParserRuleContext;

public class IdentLeftAST extends AssignmentLeftAST {

  private final String identName;

  public IdentLeftAST(ParserRuleContext ctx, String identName) {
    super(ctx);
    this.identName = identName;
  }

  @Override
  public void check() {
    identObj = Visitor.ST.lookupAll(identName);
    if (identObj == null) {
      error(identName + "lhs has not been previously defined.");
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
