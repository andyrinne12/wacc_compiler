package front_end.AST.assignment;

import front_end.types.TYPE;
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

  @Override
  public TYPE getEvalType() {
    return pairElem.getEvalType();
  }
}
