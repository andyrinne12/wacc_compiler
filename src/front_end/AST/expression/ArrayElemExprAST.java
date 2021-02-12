package front_end.AST.expression;

import front_end.AST.assignment.ArrayElemAST;
import front_end.types.TYPE;
import org.antlr.v4.runtime.ParserRuleContext;

// the AST for accessing elements in an array, exp: arr[1], or arr[1][0].
public class ArrayElemExprAST extends ExpressionAST {

  private final ArrayElemAST arrayElemAST;

  public ArrayElemExprAST(ParserRuleContext ctx, ArrayElemAST arrayElemAST) {
    super(ctx);
    this.arrayElemAST = arrayElemAST;
  }

  @Override
  public void check() {
    arrayElemAST.check();
  }

  @Override
  public TYPE getEvalType() {
    return arrayElemAST.getEvalType();
  }
}
