package front_end.AST.StatementAST;

import front_end.AST.ExpressionAST.ExpressionAST;
import front_end.SymbolTable;
import front_end.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class While extends Statement {
  ExpressionAST expression;
  Statement stat;
  SymbolTable ST;

  public While(ParserRuleContext ctx, ExpressionAST expression, Statement stat, SymbolTable ST) {
    super(ctx);
    this.expression = expression;
    this.stat = stat;
    this.ST = ST;
  }

  @Override
  public void check() {
    //check the validity of th expression
    expression.wasChecked();

    if(expression.getType().equals(Visitor.ST.lookupAll("bool"))) {
      //check the validity of the statement
      stat.wasChecked();
    } else {
      error("while condition is not boolean");
    }
  }
}
