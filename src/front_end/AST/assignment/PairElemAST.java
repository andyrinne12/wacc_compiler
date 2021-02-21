package front_end.AST.assignment;

import antlr.WACCParser.PairElemContext;
import front_end.AST.ASTNode;
import front_end.AST.expression.ExpressionAST;
import front_end.types.PAIR;
import front_end.types.TYPE;

public class PairElemAST extends ASTNode {

  private final PairElemEnum elem;
  private final ExpressionAST identExp;

  public PairElemAST(PairElemContext ctx, ExpressionAST identExp) {
    super(ctx);
    this.identExp = identExp;
    this.elem = ctx.elem.getText().equals("fst") ? PairElemEnum.FST : PairElemEnum.SND;
  }

  @Override
  public void check() {
    identExp.check();
    if (!(identExp.getEvalType() instanceof PAIR)) {
      error("Invalid pair element access. Expression of type + " + identExp.getEvalType()
          + "is not a valid identifier.");
    }
    identObj = identExp.getEvalType();
  }

  public PairElemEnum getElem() {
    return elem;
  }

  //@Override
  public TYPE getEvalType() {
    PAIR pair = (PAIR) identObj;
    if (pair == null) {
      return null;
    }
    if (elem == PairElemEnum.FST) {
      return pair.getFirstType();
    } else {
      return pair.getSecondType();
    }
  }
}
