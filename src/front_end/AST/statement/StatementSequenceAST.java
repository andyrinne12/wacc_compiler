package front_end.AST.statement;

import front_end.SymbolTable;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class StatementSequenceAST extends ScopingStatementAST {

  private final List<StatementAST> statementSeq;

  public StatementSequenceAST(ParserRuleContext ctx,
      SymbolTable parentSt, List<StatementAST> statementSeq) {
    super(ctx, parentSt);
    this.statementSeq = statementSeq;

  }

  @Override
  public void check() {
    enterScope();
    for (StatementAST stat : statementSeq) {
      if (stat != null) {
        stat.check();
      }
    }
    exitScope();
  }

  public boolean checkReturn() {
    if (statementSeq != null && !statementSeq.isEmpty()) {
      StatementAST last = statementSeq.get(statementSeq.size() - 1);
      if (last instanceof ReturnAST || last instanceof ExitAST) {
        return true;
      } else if (last instanceof IfAST) {
        IfAST lastIf = (IfAST) last;
        return lastIf.checkReturn();
      } else if (last instanceof BeginAST) {
        BeginAST lastBegin = (BeginAST) last;
        return lastBegin.checkReturn();
      }
    }
    return false;
  }
}
