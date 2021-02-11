package front_end.AST.assignment;

import org.antlr.v4.runtime.ParserRuleContext;

public class PairElemRightAST extends AssignmentRightAST {

  private final PairElemAST pairElem;

  public PairElemRightAST(ParserRuleContext ctx,
      PairElemAST pairElem) {
    super(ctx);
    this.pairElem = pairElem;
  }

  @Override
  public void check() {
    pairElem.check();
  }
}
