package front_end.AST.expression;

import front_end.Visitor;
import front_end.types.ARRAY;
import front_end.types.INT;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

// the AST for accessing elements in an array, exp: arr[1], or arr[1][0].
public class ArrayElemExprAST extends ExpressionAST {

  private String arrayIdent; // name of the array
  private List<ExpressionAST> exprList;

  public ArrayElemExprAST(ParserRuleContext ctx, String ident, List<ExpressionAST> exprList) {
    super(ctx);
    arrayIdent = ident;
    this.exprList = exprList;
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
      for (ExpressionAST expr : exprList) {
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
