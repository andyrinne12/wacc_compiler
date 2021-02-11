package front_end.AST.assignment;

import antlr.WACCParser.PairElemContext;
import front_end.AST.ASTNode;
import front_end.AST.expression.ExpressionAST;
import front_end.AST.expression.IdentAST;
import front_end.Visitor;
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
    String className = identExp.getIdentObj().getClass().getName();
    if (!(identExp instanceof IdentAST)) {
      error("Invalid pair element access. Expression of type + " + className
          + "is not a valid identifier.");
    } else {
      IdentAST ident = (IdentAST) identExp;
      identObj = Visitor.ST.lookupAll(ident.getIdentName());
      if (identObj == null) {
        error("undeclared variable");
      }
      if (!(identObj.getType() instanceof PAIR)) {
        error(
            "Invalid pair element access. Object " + ident.getIdentName() + " is not of type pair");
      }
    }
  }

  public PairElemEnum getElem() {
    return elem;
  }

  //@Override
  public TYPE getEvalType() {
    PAIR pair = (PAIR) identObj;
    if (elem == PairElemEnum.FST) {
      return pair.getFirstType();
    } else {
      return pair.getSecondType();
    }
  }
}
