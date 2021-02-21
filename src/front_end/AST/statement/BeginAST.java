package front_end.AST.statement;

import org.antlr.v4.runtime.ParserRuleContext;

public class BeginAST extends StatementAST {

  private final StatementSequenceAST statSeq;

  public BeginAST(ParserRuleContext ctx, StatementSequenceAST statSeq) {
    super(ctx);
    this.statSeq = statSeq;
  }

  @Override
  public void check() {
    statSeq.check();
  }

  public boolean checkReturn() {
    return statSeq.checkReturn();
  }
}
