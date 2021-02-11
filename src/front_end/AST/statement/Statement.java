package front_end.AST.statement;

import front_end.AST.ASTNode;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class Statement extends ASTNode {

  public Statement(ParserRuleContext ctx) {
    super(ctx);
  }
}
