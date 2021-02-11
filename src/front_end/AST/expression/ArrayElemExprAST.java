package front_end.AST.expression;

import front_end.Visitor;
import front_end.types.ARRAY;
import front_end.types.IDENTIFIER;
import front_end.types.INT;
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
    IDENTIFIER arrIdentObj = Visitor.ST.lookupAll(arrayIdent);
    if (arrIdentObj == null) {
      error("undeclared variable");
    } else {
      if (!(arrIdentObj instanceof ARRAY)) {
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
      identObj = ((ARRAY) arrIdentObj).getElemType();
      // identObj will end up having the type of the array's elements.
      // Eg: for an array int[], identObj will be int.
    }
  }

}
