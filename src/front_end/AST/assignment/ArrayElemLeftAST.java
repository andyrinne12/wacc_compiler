package front_end.AST.assignment;

import front_end.AST.expression.ArrayElemExprAST;
import org.antlr.v4.runtime.ParserRuleContext;

public class ArrayElemLeftAST extends AssignmentLeftAST {

  private final ArrayElemExprAST arrayElem;

  public ArrayElemLeftAST(ParserRuleContext ctx, ArrayElemExprAST arrayElem) {
    super(ctx);
    this.arrayElem = arrayElem;
  }

  @Override
  public void check() {
    arrayElem.check();
  }

  public ArrayElemExprAST getArrayElem() {
    return arrayElem;
  }
}
