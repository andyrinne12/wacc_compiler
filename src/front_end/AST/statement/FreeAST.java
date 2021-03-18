package front_end.AST.statement;

import back_end.FunctionBody;
import back_end.Utils;
import back_end.instructions.logical.MOV;
import back_end.instructions.store.LDR;
import back_end.operands.registers.OffsetRegister;
import back_end.operands.registers.Register;
import back_end.operands.registers.RegisterManager;
import front_end.AST.expression.ExpressionAST;
import front_end.types.ARRAY;
import front_end.types.PAIR;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class FreeAST extends StatementAST {
  private ExpressionAST expression;

  public FreeAST(ParserRuleContext ctx, ExpressionAST expression) {
    super(ctx);
    this.expression = expression;
  }

  @Override
  public void check() {
    expression.check();
    if(!(expression.getIdentObj() instanceof PAIR)) {
      error("the only expression that can be freed is a pair type");
    }
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    body.addInstr(new LDR(freeRegs.get(0), new OffsetRegister(RegisterManager.SP)));
    body.addInstr(new MOV(RegisterManager.getResultReg(), freeRegs.get(0)));

    if (expression.getEvalType() instanceof PAIR) {
      body.addInstr(Utils.FREE_PAIR);
      Utils.addFunc("p_free_pair", freeRegs.get(0));
    } else if (expression.getEvalType() instanceof ARRAY) {
      body.addInstr(Utils.FREE_ARRAY);
      Utils.addFunc("p_free_array", freeRegs.get(0));
    }
  }
}
