package front_end.AST.statement;

import front_end.AST.expression.ExpressionAST;
import front_end.Visitor;
import front_end.types.IDENTIFIER;
import org.antlr.v4.runtime.ParserRuleContext;

public class IfAST extends StatementAST {

  private final ExpressionAST expression;
  private final StatementSequenceAST thenSeq;
  private final StatementSequenceAST elseSeq;

  public IfAST(ParserRuleContext ctx, ExpressionAST expression, StatementSequenceAST thenSeq,
      StatementSequenceAST elseSeq) {
    super(ctx);
    this.expression = expression;
    this.thenSeq = thenSeq;
    this.elseSeq = elseSeq;
  }

  @Override
  public void check() {
    //assure the validity of the expression
    expression.check();
    IDENTIFIER ident = Visitor.ST.lookupAll("bool");
    if (!expression.getIdentObj().equals(ident)) {
      error("If condition type is not boolean");
    }

    //check that the statements are valid
    thenSeq.check();
    elseSeq.check();
  }

  public boolean checkReturn() {
    return thenSeq.checkReturn() && elseSeq.checkReturn();
  }
}
