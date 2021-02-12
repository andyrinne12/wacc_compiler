package front_end.AST.expression;

import front_end.Visitor;
import front_end.types.TYPE;
import org.antlr.v4.runtime.ParserRuleContext;

public class BoolExprAST extends ExpressionAST {

  // syntactic attr:
  private final boolean boolVal;

  public BoolExprAST(ParserRuleContext ctx, String boolString) {
    super(ctx);
    boolVal = boolString.equals("true");
  }

  @Override
  public void check() {
    identObj = Visitor.ST.lookupAll("bool");
    if (identObj == null) {
      error("Undefined type: bool");
    }

  }

  @Override
  public TYPE getEvalType() {
    return identObj.getType();
  }
}
