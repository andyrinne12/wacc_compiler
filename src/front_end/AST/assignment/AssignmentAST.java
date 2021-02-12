package front_end.AST.assignment;

import front_end.AST.ASTNode;
import front_end.types.ARRAY;
import front_end.types.TYPE;
import org.antlr.v4.runtime.ParserRuleContext;

public class AssignmentAST extends ASTNode {

  private final AssignmentLeftAST lhs;
  private final AssignmentRightAST rhs;

  public AssignmentAST(ParserRuleContext ctx, AssignmentLeftAST lhs, AssignmentRightAST rhs) {
    super(ctx);
    this.lhs = lhs;
    this.rhs = rhs;
  }

  @Override
  public void check() {
    lhs.check();
    rhs.check();
    TYPE lhsType = lhs.getEvalType();
    TYPE rhsType = rhs.getEvalType();
    if (lhsType instanceof ARRAY) {
      error("Array variables cannot be directly assigned to.");
    }
    if (lhsType.equalsType(rhsType)) {
      error("Invalid type at assignment. Expected: " + lhsType + " actual: " + rhsType);
    }
  }
}
