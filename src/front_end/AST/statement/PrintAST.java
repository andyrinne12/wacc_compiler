package front_end.AST.statement;

import back_end.FunctionBody;
import back_end.instructions.logical.MOV;
import back_end.operands.registers.Register;
import back_end.operands.registers.RegisterManager;
import front_end.AST.expression.ExpressionAST;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class PrintAST extends StatementAST {
  private ExpressionAST expression;

  public PrintAST(ParserRuleContext ctx, ExpressionAST expression) {
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
  }
}
