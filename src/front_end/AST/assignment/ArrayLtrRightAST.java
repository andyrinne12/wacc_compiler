package front_end.AST.assignment;

import front_end.AST.expression.ExpressionAST;
import front_end.types.ARRAY;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class ArrayLtrRightAST extends AssignmentRightAST {

  private final List<ExpressionAST> array;

  public ArrayLtrRightAST(ParserRuleContext ctx,
      List<ExpressionAST> array) {
    super(ctx);
    this.array = array;
  }

  @Override
  public void check() {
    if (array.size() == 0) {
      return;
    }
    ExpressionAST first = array.get(0);
    TYPE firstType = first.getIdentObj().getType();
    for (int i = 1; i < array.size(); i++) {
      ExpressionAST exp = array.get(i);
      TYPE expType = exp.getIdentObj().getType();
      if (!firstType.equalsType(expType)) {
        error(
            "Array literal contains items of different types. Expected: " + firstType + " actual: "
                + expType);
      }
    }
    identObj = new ARRAY(firstType, array.size());
  }

  @Override
  public TYPE getEvalType() {
    return identObj.getType();
  }
}
