package front_end.AST.expression;

import front_end.Visitor;
import front_end.types.TYPE;
import org.antlr.v4.runtime.ParserRuleContext;

public class SignedIntExprAST extends ExpressionAST {

  private String intSign;
  private String value;

  public SignedIntExprAST(ParserRuleContext ctx, String intSign, String value) {
    super(ctx);
    this.value = value;
    this.intSign = intSign;
  }

  @Override
  public void check() {
    identObj = Visitor.ST.lookupAll("int");
    if (identObj == null) {
      error("Undefined type: int");
    }
  }

  @Override
  public TYPE getEvalType() {
    return identObj.getType();
  }
}
