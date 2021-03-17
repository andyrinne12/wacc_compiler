package front_end.AST.statement;

import back_end.CodeGen;
import back_end.FunctionBody;
import back_end.instructions.Condition;
import back_end.instructions.Label;
import back_end.instructions.arithmetic.CMP;
import back_end.instructions.branch.B;
import back_end.operands.immediate.ImmInt;
import back_end.operands.registers.Register;
import front_end.AST.expression.BoolExprAST;
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
    if(isFalse()){
      return;
    }
    /* Always true case not optimized yet */
    String condLabel = CodeGen.getLabel();
    String bodyLabel = CodeGen.getLabel();

    // a label that represents the end of the while loop. Useful for break statements to know where to jump to.
    String endLabel = CodeGen.getLabel();

    body.addInstr(new B(Condition.NONE, condLabel));
    body.addInstr(new Label(bodyLabel));
    statSeq.setEndLabel(endLabel);
    statSeq.assemble(body, freeRegs);

    body.addInstr(new Label(condLabel));
    expression.assemble(body, freeRegs);
    body.addInstr(new CMP(freeRegs.get(0), new ImmInt(true)));
    body.addInstr(new B(Condition.EQ, bodyLabel));
     
    body.addInstr(new Label(endLabel));
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

  private boolean isTrue() {
    if (expression instanceof BoolExprAST) {
      return ((BoolExprAST) expression).getBoolVal();
    }
    return false;
  }

  private boolean isFalse() {
    if (expression instanceof BoolExprAST) {
      return !((BoolExprAST) expression).getBoolVal();
    }
    return false;
  }
}
