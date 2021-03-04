package front_end.AST.statement;

import back_end.FunctionBody;
import back_end.instructions.arithmetic.CMP;
import back_end.operands.immediate.ImmInt;
import back_end.operands.registers.Register;
import front_end.AST.expression.BinaryOpExprAST;
import front_end.AST.expression.BoolExprAST;
import front_end.AST.expression.ExpressionAST;
import front_end.Visitor;
import front_end.types.IDENTIFIER;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class IfAST extends StatementAST {

  private final ExpressionAST expression;
  private final StatementSequenceAST thenSeq;
  private final StatementSequenceAST elseSeq;

  public IfAST(ParserRuleContext ctx, ExpressionAST expression, StatementSequenceAST thenSeq,
      StatementSequenceAST elseSeq) {
    super(ctx);
    this.expression = expression;
    this.thenSeq = thenSeq;
    this.elseSeq = elseSeq;
  }

  @Override
  public void check() {
    //assure the validity of the expression
    expression.check();
    IDENTIFIER ident = Visitor.ST.lookupAll("bool");
    if (!expression.getIdentObj().equals(ident)) {
      error("If condition type is not boolean");
    }

    //check that the statements are valid
    thenSeq.check();
    elseSeq.check();
  }

  private boolean isTrue() {
    if (expression instanceof BoolExprAST) {
      return ((BoolExprAST) expression).getBoolVal() == true;
    } else if (expression instanceof BinaryOpExprAST) {
      if (((BinaryOpExprAST) expression).booleanTranslation() != null) {
        return ((BinaryOpExprAST) expression).booleanTranslation();
      }
    }
    return false;
  }

  private boolean isFalse() {
    if (expression instanceof BoolExprAST) {
      return ((BoolExprAST) expression).getBoolVal() == false;
    } else if (expression instanceof BinaryOpExprAST) {
      if (((BinaryOpExprAST) expression).booleanTranslation() != null) {
        return !((BinaryOpExprAST) expression).booleanTranslation();
      }
    }
    return false;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    if(!(isTrue() || isFalse())) {
      expression.assemble(body, freeRegs);

      body.addInstr(new CMP(freeRegs.get(0), new ImmInt(0)));
    }

  }

  public boolean checkReturn() {
    return thenSeq.checkReturn() && elseSeq.checkReturn();
  }
}
