package front_end.AST.statement;

import front_end.SymbolTable;
import front_end.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class Begin extends Statement {
  private Statement stat;
  private SymbolTable ST;

  public Begin(ParserRuleContext ctx, Statement stat) {
    super(ctx);
    this.stat = stat;
    this.ST = Visitor.ST;
  }

  @Override
  public void check() {
    stat.wasChecked();
  }
}
