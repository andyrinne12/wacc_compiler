package front_end.AST.statement;

import front_end.AST.ASTNode;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class StatementAST extends ASTNode {

  public StatementAST(ParserRuleContext ctx) {
    super(ctx);
  }
}
