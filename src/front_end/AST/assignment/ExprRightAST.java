package front_end.AST.assignment;

import back_end.FunctionBody;
import back_end.operands.registers.Register;
import front_end.AST.expression.ExpressionAST;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class ExprRightAST extends AssignmentRightAST {

  public final ExpressionAST expr;

  public ExprRightAST(ParserRuleContext ctx, ExpressionAST expr) {
    super(ctx);
    this.expr = expr;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    expr.assemble(body, freeRegs);
  }

  @Override
  public void check() {
    expr.check();
  }

  @Override
  public TYPE getEvalType() {
    return expr.getEvalType();
  }

  public ExpressionAST getExpr() {
    return expr;
  }
}
