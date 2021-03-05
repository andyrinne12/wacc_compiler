package front_end.AST.statement;

import back_end.CodeGen;
import back_end.FunctionBody;
import back_end.instructions.Condition;
import back_end.instructions.Label;
import back_end.instructions.arithmetic.CMP;
import back_end.instructions.branch.B;
import back_end.instructions.branch.BL;
import back_end.operands.immediate.ImmInt;
import back_end.operands.registers.Register;
import front_end.AST.expression.BoolExprAST;
import front_end.AST.expression.ExpressionAST;
import front_end.Visitor;
import front_end.types.IDENTIFIER;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class IfAST extends StatementAST {

  private final ExpressionAST condition;
  private final StatementSequenceAST thenSeq;
  private final StatementSequenceAST elseSeq;

  public IfAST(ParserRuleContext ctx, ExpressionAST condition, StatementSequenceAST thenSeq,
      StatementSequenceAST elseSeq) {
    super(ctx);
    this.condition = condition;
    this.thenSeq = thenSeq;
    this.elseSeq = elseSeq;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    /* If the condition is either true or false simplify */
    if (isFalse()) {
      elseSeq.assemble(body, freeRegs);
    } else if (isTrue()) {
      thenSeq.assemble(body, freeRegs);
    } else {
      body.addInstr(new CMP(freeRegs.get(0), new ImmInt(false)));
      String labelFalse = CodeGen.getLabel();
      String labelContinue = CodeGen.getLabel();

      body.addInstr(new B(Condition.EQ, labelFalse));
      thenSeq.assemble(body, freeRegs);
      body.addInstr(new BL(Condition.NONE, labelContinue));

      body.addInstr(new Label(labelFalse));
      elseSeq.assemble(body,freeRegs);

      body.addInstr(new Label(labelContinue));
    }
  }

  @Override
  public void check() {
    //assure the validity of the expression
    condition.check();
    IDENTIFIER ident = Visitor.ST.lookupAll("bool");
    if (!condition.getIdentObj().equals(ident)) {
      error("If condition type is not boolean");
    }

    //check that the statements are valid
    thenSeq.check();
    elseSeq.check();
  }

  private boolean isTrue() {
    if (condition instanceof BoolExprAST) {
      return ((BoolExprAST) condition).getBoolVal();
    }
    return false;
  }

  private boolean isFalse() {
    if (condition instanceof BoolExprAST) {
      return !((BoolExprAST) condition).getBoolVal();
    }
    return false;
  }

  public boolean checkReturn() {
    return thenSeq.checkReturn() && elseSeq.checkReturn();
  }
}
