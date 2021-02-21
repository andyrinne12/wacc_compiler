package front_end.AST;

import front_end.Visitor;
import front_end.types.IDENTIFIER;
import front_end.types.TYPE;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class ASTNode {

  protected ParserRuleContext ctx;
  protected IDENTIFIER identObj; // the semantic attribute of the class.

  public ASTNode(ParserRuleContext ctx) {
    this.ctx = ctx;
  }

  public abstract void check();

  protected void error(String msg) {
    Visitor.error(ctx, msg);
  }

  public IDENTIFIER getIdentObj() {
    return identObj;
  }

  public TYPE getType() {
    return identObj.getType();
  }
}
