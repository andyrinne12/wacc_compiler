package front_end.AST.expression;

import back_end.FunctionBody;
import back_end.instructions.store.LDR;
import back_end.operands.registers.OffsetRegister;
import back_end.operands.registers.Register;
import front_end.AST.assignment.PointerDerefAST;
import front_end.Visitor;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class PointerDerefExprAST extends ExpressionAST {

  private final PointerDerefAST pointerDeref;

  public PointerDerefExprAST(ParserRuleContext ctx, PointerDerefAST pointerDeref) {
    super(ctx);
    this.pointerDeref = pointerDeref;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    pointerDeref.assemble(body, freeRegs);
    body.addInstr(new LDR(freeRegs.get(0), new OffsetRegister(freeRegs.get(0))));
  }

  @Override
  public void check() {
    pointerDeref.check();
    /* treat values in memory locations as integers */
    identObj = Visitor.ST.lookupAll("int");
  }

  @Override
  public TYPE getEvalType() {
    return identObj.getType();
  }
}
