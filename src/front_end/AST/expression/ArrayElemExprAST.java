package front_end.AST.expression;

import back_end.FunctionBody;
import back_end.instructions.Condition;
import back_end.instructions.store.LDR;
import back_end.operands.registers.OffsetRegister;
import back_end.operands.registers.Register;
import front_end.AST.assignment.ArrayElemAST;
import front_end.types.BOOLEAN;
import front_end.types.CHAR;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

// the AST for accessing elements in an array, exp: arr[1], or arr[1][0].
public class ArrayElemExprAST extends ExpressionAST {

  private final ArrayElemAST arrayElemAST;

  public ArrayElemExprAST(ParserRuleContext ctx, ArrayElemAST arrayElemAST) {
    super(ctx);
    this.arrayElemAST = arrayElemAST;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    arrayElemAST.assemble(body, freeRegs);
    Condition cond = Condition.NONE;
    if (arrayElemAST.getEvalType() instanceof CHAR || arrayElemAST
        .getEvalType() instanceof BOOLEAN) {
      cond = Condition.SB;
    }
    body.addInstr(new LDR(cond, freeRegs.get(0), new OffsetRegister(freeRegs.get(0))));
  }

  @Override
  public void check() {
    arrayElemAST.check();
  }

  @Override
  public TYPE getEvalType() {
    return arrayElemAST.getEvalType();
  }
}
