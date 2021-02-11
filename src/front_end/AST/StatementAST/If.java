package front_end.AST.StatementAST;

import front_end.AST.ExpressionAST.ExpressionAST;
import front_end.SymbolTable;
import front_end.Visitor;
import front_end.types.IDENTIFIER;
import org.antlr.v4.runtime.ParserRuleContext;

public class If extends Statement{
  private ExpressionAST expression;
  private Statement then;
  private SymbolTable thenST;
  private Statement elseSt;
  private SymbolTable elseST;

  public If(ParserRuleContext ctx, ExpressionAST expression, Statement then, Statement elseSt, SymbolTable thenST, SymbolTable elseST) {
    super(ctx);
    this.expression = expression;
    this.then = then;
    this.elseSt = elseSt;
    this.thenST = thenST;
    this.elseST = elseST;
  }

  @Override
  public void check() {
    //assure the validity of the expression
    expression.wasChecked();
    IDENTIFIER ident = Visitor.ST.lookupAll("bool");
    if(!expression.getType().equals(ident)) {
      error("If condition type is not boolean");
    }

    //check that the statements are valid
    then.wasChecked();
    then.wasChecked();
  }
}
