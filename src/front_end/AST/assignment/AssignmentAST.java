package front_end.AST.assignment;

import front_end.AST.ASTNode;
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

  }
}
