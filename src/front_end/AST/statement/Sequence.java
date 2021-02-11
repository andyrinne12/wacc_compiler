package front_end.AST.statement;

import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class Sequence extends Statement {
  private List<Statement> stats;

  public Sequence(ParserRuleContext ctx, List<Statement> stats) {
    super(ctx);
    this.stats = stats;
  }

  @Override
  public void check() {
    for(Statement stat : stats) {
      stat.wasChecked();
    }
  }
}
