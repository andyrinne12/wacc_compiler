package front_end.AST.assignment;

import front_end.types.TYPE;
import org.antlr.v4.runtime.ParserRuleContext;

public class PairElemRightAST extends AssignmentRightAST {

  private final PairElemAST pairElem;

  public PairElemRightAST(ParserRuleContext ctx,
      PairElemAST pairElem) {
    super(ctx);
    this.pairElem = pairElem;
  }

  public PairElemAST getPairElem() {
    return pairElem;
  }

  @Override
  public void check() {
    pairElem.check();
  }


  @Override
  public TYPE getEvalType() {
    return pairElem.getEvalType();
  }
}
