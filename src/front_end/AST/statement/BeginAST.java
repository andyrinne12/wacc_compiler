package front_end.AST.statement;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import back_end.FunctionBody;
import back_end.operands.registers.Register;

public class BeginAST extends StatementAST {

  private final StatementSequenceAST statSeq;

  private String endLabel; // the endLabel of the enclosing while loop.

  public BeginAST(ParserRuleContext ctx, StatementSequenceAST statSeq) {
    super(ctx);
    this.statSeq = statSeq;
  }

  @Override
  public void check() {
    statSeq.check();
  }

  public boolean checkReturn() {
    return statSeq.checkReturn();
  }

  // BeginAST's assemble() method is only called when we have nested begin-end statements within a program.
  // For the outermost begin-end statement, the assemble() function of ProgramAST is called instead.
  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    statSeq.setEndLabel(endLabel);
    statSeq.assemble(body, freeRegs);
  }

  public void setEndLabel(String endLabel) {
    this.endLabel = endLabel;
  }
}
