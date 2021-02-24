package front_end.AST.statement;

import back_end.FunctionBody;
import back_end.instructions.Condition;
import back_end.instructions.arithmetic.SUB;
import back_end.operands.immediate.ImmInt;
import back_end.operands.registers.Register;
import back_end.operands.registers.RegisterManager;
import front_end.SymbolTable;
import front_end.Visitor;
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
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    enterScope();
    int size = Visitor.ST.setFrameSize();
    if (size > 0) {
      body.addInstr(
          new SUB(Condition.NONE, false, RegisterManager.SP, RegisterManager.SP, new ImmInt(size)));
    }
    for (StatementAST stat : statementSeq) {
      stat.assemble(body, freeRegs);
    }
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
