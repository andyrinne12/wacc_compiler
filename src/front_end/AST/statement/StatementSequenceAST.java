package front_end.AST.statement;

import front_end.SymbolTable;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class StatementSequenceAST extends ScopingStatementAST {

  private final List<Statement> statementSeq;

  public StatementSequenceAST(ParserRuleContext ctx,
      SymbolTable parentSt, List<Statement> statementSeq) {
    super(ctx, parentSt);
    this.statementSeq = statementSeq;
  }

  @Override
  public void check() {
    enterScope();
    for (Statement stat : statementSeq) {
      stat.check();
    }
    exitScope();
  }
}
