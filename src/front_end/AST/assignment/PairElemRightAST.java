package front_end.AST.assignment;

import back_end.FunctionBody;
import back_end.instructions.store.LDR;
import back_end.operands.registers.OffsetRegister;
import back_end.operands.registers.Register;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class PairElemRightAST extends AssignmentRightAST {

  private final PairElemAST pairElem;

  public PairElemRightAST(ParserRuleContext ctx,
      PairElemAST pairElem) {
    super(ctx);
    this.pairElem = pairElem;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    pairElem.assemble(body, freeRegs);
    body.addInstr(new LDR(freeRegs.get(0), new OffsetRegister(freeRegs.get(0))));
  }

  @Override
  public void check() {
    pairElem.check();
  }


  @Override
  public TYPE getEvalType() {
    return pairElem.getEvalType();
  }


  public PairElemAST getPairElem() {
    return pairElem;
  }

}
