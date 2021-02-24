package front_end.AST.statement;

import back_end.FunctionBody;
import back_end.operands.registers.Register;
import front_end.AST.expression.ExpressionAST;
import front_end.Visitor;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class WhileAST extends StatementAST {

  private final ExpressionAST expression;
  private final StatementSequenceAST statSeq;

  public WhileAST(ParserRuleContext ctx, ExpressionAST expression, StatementSequenceAST statSeq) {
    super(ctx);
    this.expression = expression;
    this.statSeq = statSeq;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    statSeq.assemble(body, freeRegs);
  }

  @Override
  public void check() {
    //check the validity of the expression
    expression.check();
    if (expression.getEvalType() == null || !expression.getEvalType()
        .equals(Visitor.ST.lookupAll("bool"))) {
      error("while condition is not boolean");
    }

    statSeq.check();
  }
}
