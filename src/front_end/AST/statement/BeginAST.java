package front_end.AST.statement;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import back_end.FunctionBody;
import back_end.operands.registers.Register;

public class BeginAST extends StatementAST {

  private final StatementSequenceAST statSeq;

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

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    // an implementation is not needed.
    // in ProgramAST's assemble(), a FunctionBody object is created, 
    // which pushes the relevant initialization instructions into our global instructions list.
  }
}
