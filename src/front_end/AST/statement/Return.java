package front_end.AST.statement;

import front_end.AST.ASTNode;
import org.antlr.v4.runtime.ParserRuleContext;

public class Return extends Statement {
  private ASTNode node;

  public Return(ParserRuleContext ctx, ASTNode node) {
    super(ctx);
    this.node = node;
  }

  @Override
  public void check() {
    node.wasChecked();
  }
}
