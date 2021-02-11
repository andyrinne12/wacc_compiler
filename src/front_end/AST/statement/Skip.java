package front_end.AST.statement;

import org.antlr.v4.runtime.ParserRuleContext;

public class Skip extends Statement {

  public Skip(ParserRuleContext ctx) {
    super(ctx);
  }

  @Override
  public void check() {

  }
}
