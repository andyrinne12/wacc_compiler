package front_end.AST.expression;

import back_end.FunctionBody;
import back_end.instructions.Condition;
import back_end.instructions.store.LDR;
import back_end.operands.immediate.ImmInt;
import back_end.operands.registers.Register;
import front_end.Visitor;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class SignedIntExprAST extends ExpressionAST {

  private String intSign;
  private int value;

  public SignedIntExprAST(ParserRuleContext ctx, String intSign, String value) {
    super(ctx);
    this.value = Integer.parseInt(intSign + value);
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    body.addInstr(new LDR(Condition.NONE, freeRegs.get(0), new ImmInt(value)));
  }

  @Override
  public void check() {
    identObj = Visitor.ST.lookupAll("int");
    if (identObj == null) {
      error("Undefined type: int");
    }
  }

  @Override
  public TYPE getEvalType() {
    return identObj.getType();
  }

  public int getValue() {
    return value;
  }
}
