package front_end.AST.assignment;

import front_end.AST.ASTNode;
import front_end.types.TYPE;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class AssignmentLeftAST extends ASTNode {


  public AssignmentLeftAST(ParserRuleContext ctx) {
    super(ctx);
  }

  public abstract TYPE getEvalType();

}
