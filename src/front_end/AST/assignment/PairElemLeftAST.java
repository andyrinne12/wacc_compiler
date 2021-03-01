package front_end.AST.assignment;

import back_end.FunctionBody;
import back_end.operands.registers.Register;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class PairElemLeftAST extends AssignmentLeftAST {

  private final PairElemAST pairElem;

  public PairElemLeftAST(ParserRuleContext ctx,
      PairElemAST pairElem) {
    super(ctx);
    this.pairElem = pairElem;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    pairElem.assemble(body, freeRegs);
  }

  @Override
  public void check() {
    pairElem.check();
  }

  @Override
  public TYPE getEvalType() {
    return pairElem.getEvalType();
  }
}
