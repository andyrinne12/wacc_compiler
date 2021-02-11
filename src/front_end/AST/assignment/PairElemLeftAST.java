package front_end.AST.assignment;

import org.antlr.v4.runtime.ParserRuleContext;

public class PairElemLeftAST extends AssignmentLeftAST {

  private final PairElemAST pairElem;

  public PairElemLeftAST(ParserRuleContext ctx,
      PairElemAST pairElem) {
    super(ctx);
    this.pairElem = pairElem;
  }

  @Override
  public void check() {
    pairElem.check();
  }
}
