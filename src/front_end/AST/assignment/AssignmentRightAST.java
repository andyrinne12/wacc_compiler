package front_end.AST.assignment;

import front_end.AST.ASTNode;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class AssignmentRightAST extends ASTNode {

  public AssignmentRightAST(ParserRuleContext ctx) {
    super(ctx);
  }

}
