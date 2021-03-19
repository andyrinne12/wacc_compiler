package front_end.AST.assignment;

import back_end.FunctionBody;
import back_end.instructions.Condition;
import back_end.instructions.store.STR;
import back_end.operands.registers.OffsetRegister;
import back_end.operands.registers.Register;
import back_end.operands.registers.RegisterManager;
import front_end.AST.expression.AddressRefExprAST;
import front_end.AST.statement.StatementAST;
import front_end.Visitor;
import front_end.types.ARRAY;
import front_end.types.BOOLEAN;
import front_end.types.CHAR;
import front_end.types.IDENTIFIER;
import front_end.types.INT;
import front_end.types.PAIR;
import front_end.types.POINTER;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class AssignmentAST extends StatementAST {

  private final AssignmentLeftAST lhs;
  private final AssignmentRightAST rhs;

  public AssignmentAST(ParserRuleContext ctx, AssignmentLeftAST lhs, AssignmentRightAST rhs) {
    super(ctx);
    this.lhs = lhs;
    this.rhs = rhs;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    rhs.assemble(body, freeRegs);

    Condition cond = Condition.NONE;
    if ((rhs.getEvalType() instanceof BOOLEAN) || (rhs.getEvalType() instanceof CHAR)) {
      cond = Condition.B;
    }

    if (lhs instanceof IdentLeftAST) {
      IdentLeftAST ident = (IdentLeftAST) lhs;
      int offset = Visitor.ST.getIdentOffset(ident.getIdent()) + Visitor.ST.getJumpOffset();
      body.addInstr(new STR(cond, freeRegs.get(0), new OffsetRegister(RegisterManager.SP, offset)));
    } else {
      List<Register> freeRegs1 = freeRegs.subList(1, freeRegs.size());
      lhs.assemble(body, freeRegs1);
      body.addInstr(new STR(cond, freeRegs.get(0), new OffsetRegister(freeRegs1.get(0))));
    }
  }

  @Override
  public void check() {
    lhs.check();
    rhs.check();
    TYPE lhsType = lhs.getEvalType();
    if (lhsType == null) {
      // if the lhs is an identifier that we havent encountered before, then we don't bother with checking assignment compatibility with the rhs.
      return;
    }
    TYPE rhsType = rhs.getEvalType();
    IDENTIFIER charIdent = Visitor.ST.lookupAll("char");
    if (lhsType instanceof ARRAY) {
      ARRAY array = (ARRAY) lhsType;
      if (!array.getElemType().equalsType(charIdent.getType())) {
        error("Array variables cannot be directly assigned to: " + lhs.getIdentObj());
      }
    }
    if (!lhsType.equalsType(rhsType)) {
      if (lhsType instanceof POINTER) {
        if (rhsType instanceof POINTER) {
          warning("assignment from incompatible pointer type");
          return;
        } else if (rhsType instanceof INT) {
          if (rhs instanceof ExprRightAST) {
            ExprRightAST expr = (ExprRightAST) rhs;
            if (expr.getExpr() instanceof AddressRefExprAST) {
              return;
            }
          }
          warning("assignment to pointer from integer without a cast");
          return;
        } else if (rhsType instanceof ARRAY || rhsType instanceof PAIR) {
          return;
        }
        /* otherwise */
        error("Invalid type at assignment. Expected: " + lhsType + " actual: " + rhsType);
      }
    }
  }
}
