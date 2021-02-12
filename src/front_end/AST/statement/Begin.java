package front_end.AST.statement;

import org.antlr.v4.runtime.ParserRuleContext;

public class Begin extends Statement {

  private final StatementSequenceAST statSeq;

  public Begin(ParserRuleContext ctx, StatementSequenceAST statSeq) {
    super(ctx);
    this.statSeq = statSeq;
  }

  @Override
  public void check() {
    statSeq.check();
  }
}
