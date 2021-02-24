package front_end.AST.expression;

import back_end.FunctionBody;
import back_end.instructions.Condition;
import back_end.instructions.store.LDR;
import back_end.operands.registers.OffsetRegister;
import back_end.operands.registers.Register;
import back_end.operands.registers.RegisterManager;
import front_end.Visitor;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class IdentAST extends ExpressionAST {

  private final String identName;

  public IdentAST(ParserRuleContext ctx, String identName) {
    super(ctx);
    this.identName = identName;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    body.addInstr(
        new LDR(Condition.NONE, freeRegs.get(0), new OffsetRegister(RegisterManager.SP, Visitor.ST
            .getIdentOffset(identName), false)));
  }

  @Override
  public void check() {
    identObj = Visitor.ST.lookupAll(identName);
    if (identObj == null) {
      error(identName + " has not been previously defined.");
    }
  }

  public String getIdentName() {
    return identName;
  }

  @Override
  public TYPE getEvalType() {
    if (identObj == null) {
      return null;
    }
    return identObj.getType();
  }
}
