package front_end.AST.assignment;

import antlr.WACCParser.ArrayElemContext;
import front_end.AST.expression.ExpressionAST;
import front_end.Visitor;
import front_end.types.ARRAY;
import front_end.types.INT;
import front_end.types.TYPE;
import java.util.List;

public class ArrayElemAST extends AssignmentLeftAST {

  private final String arrayIdent;
  private List<ExpressionAST> indices;

  public ArrayElemAST(ArrayElemContext ctx, List<ExpressionAST> indices) {
    super(ctx);
    arrayIdent = ctx.IDENT().getText();
    this.indices = indices;
  }

  @Override
  public void check() {
    identObj = Visitor.ST.lookupAll(arrayIdent);
    if (identObj == null) {
      error("undeclared variable");
    } else {
      if (!(identObj instanceof ARRAY)) {
        error("Invalid access on non-array variable");
      }
      for (ExpressionAST expr : indices) {
        expr.check();
        if (!(expr.getIdentObj() instanceof INT)) {
          error(
              "An ArrayElement only accepts integers between its square brackets. The type given here is "
                  + expr.getIdentObj().getType());
        }
      }
    }
  }

  @Override
  public TYPE getEvalType() {
    ARRAY array = (ARRAY) identObj;
    return array.getElemType();
  }
}
