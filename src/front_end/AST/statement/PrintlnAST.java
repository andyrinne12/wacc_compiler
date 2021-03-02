package front_end.AST.statement;

import back_end.FunctionBody;
import back_end.instructions.Condition;
import back_end.instructions.branch.BL;
import back_end.operands.registers.Register;
import front_end.AST.expression.ExpressionAST;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class PrintlnAST extends StatementAST {
  private ExpressionAST expression;

  public PrintlnAST(ParserRuleContext ctx, ExpressionAST expression) {
    super(ctx);
    this.expression = expression;
  }

  @Override
  public void check() {
    expression.check();
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    expression.assemble(body, freeRegs);
    body.addInstr(new BL(Condition.LT, "p_print_ln"));
  }
}
