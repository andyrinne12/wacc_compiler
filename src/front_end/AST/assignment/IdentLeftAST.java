package front_end.AST.assignment;

import back_end.FunctionBody;
import back_end.instructions.Condition;
import back_end.instructions.store.LDR;
import back_end.operands.registers.OffsetRegister;
import back_end.operands.registers.Register;
import back_end.operands.registers.RegisterManager;
import front_end.Visitor;
import front_end.types.FUNCTION;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class IdentLeftAST extends AssignmentLeftAST {

  private final String identName;

  public IdentLeftAST(ParserRuleContext ctx, String identName) {
    super(ctx);
    this.identName = identName;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    int offset = Visitor.ST.getIdentOffset(identName) + Visitor.ST.getJumpOffset();
    body.addInstr(new LDR(Condition.NONE, freeRegs.get(0),
        new OffsetRegister(RegisterManager.SP, offset, false)));
  }

  @Override
  public void check() {
    identObj = Visitor.ST.lookupAll(identName);
    if (identObj == null) {
      error("Variable " + identName + " on the lhs has not been previously defined.");
    }
    if (identObj instanceof FUNCTION) {
      error("Function identifier on the left hand side of the assignment");
    }
  }

  @Override
  public String toString() {
    return identName;
  }

  @Override
  public TYPE getEvalType() {
    if (identObj == null) {
      return null;
    } else {
      return identObj.getType();
    }
  }
}
