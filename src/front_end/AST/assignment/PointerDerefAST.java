package front_end.AST.assignment;

import back_end.FunctionBody;
import back_end.instructions.store.LDR;
import back_end.operands.registers.OffsetRegister;
import back_end.operands.registers.Register;
import back_end.operands.registers.RegisterManager;
import front_end.Visitor;
import front_end.types.POINTER;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class PointerDerefAST extends AssignmentLeftAST {

  private final AssignmentLeftAST lhs;

  public PointerDerefAST(ParserRuleContext ctx, AssignmentLeftAST lhs) {
    super(ctx);
    this.lhs = lhs;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    if (lhs instanceof IdentLeftAST) {
      IdentLeftAST ident = (IdentLeftAST) lhs;
      int offset = Visitor.ST.getIdentOffset(ident.getIdent()) + Visitor.ST.getJumpOffset();
      body.addInstr(new LDR(freeRegs.get(0), new OffsetRegister(RegisterManager.SP, offset)));
    } else {
      lhs.assemble(body, freeRegs);
      body.addInstr(new LDR(freeRegs.get(0), new OffsetRegister(freeRegs.get(0))));
    }
  }

  @Override
  public void check() {
    /* As memory can be dynamically allocated there is not more to check at compile-time */
    lhs.check();
    if (!(lhs.getEvalType() instanceof POINTER)) {
      error("invalid memory dereference from variable of type: " + lhs.getEvalType()
          + " pointer type expected");
    }
  }

  @Override
  public TYPE getEvalType() {
    return null;
  }
}
