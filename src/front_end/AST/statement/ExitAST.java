package front_end.AST.statement;

import back_end.FunctionBody;
import back_end.instructions.Condition;
import back_end.instructions.branch.BL;
import back_end.instructions.logical.MOV;
import back_end.operands.registers.Register;
import back_end.operands.registers.RegisterManager;
import front_end.AST.expression.ExpressionAST;
import front_end.Visitor;
import front_end.types.IDENTIFIER;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class ExitAST extends StatementAST {
  private ExpressionAST expression;

  public ExitAST(ParserRuleContext ctx, ExpressionAST expression) {
    super(ctx);
    this.expression = expression;
  }

  @Override
  public void check() {
    expression.check();
    IDENTIFIER intIdent = Visitor.ST.lookupAll("int");
    if(expression.getEvalType() == null || !expression.getEvalType().equalsType(intIdent.getType())) {
      error("Exit code must be an integer");
    }
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    expression.assemble(body, freeRegs);
    body.addInstr(new MOV(RegisterManager.getParamRegs().get(0), freeRegs.get(0)));
    body.addInstr(new BL(Condition.NONE, "exit"));
  }
}
