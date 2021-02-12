package front_end.AST.assignment;

import front_end.AST.ASTNode;
import front_end.Visitor;
import front_end.types.TYPE;
import front_end.types.VARIABLE;
import org.antlr.v4.runtime.ParserRuleContext;

public class InitializationAST extends ASTNode {

  private final TYPE type;
  private final IdentLeftAST ident;
  private final AssignmentRightAST rhs;

  public InitializationAST(ParserRuleContext ctx, TYPE type, IdentLeftAST ident,
      AssignmentRightAST rhs) {
    super(ctx);
    this.type = type;
    this.ident = ident;
    this.rhs = rhs;
  }

  @Override
  public void check() {
    if (Visitor.ST.lookup(ident.toString()) != null) {
      error("Variable " + ident.toString() + " already defined");
    }
    Visitor.ST.add(ident.toString(), new VARIABLE(type));
    TYPE rhsType = rhs.getEvalType();
    if (!type.equalsType(rhsType)) {
      error("Invalid type at initialization. Expected: " + type + " actual: " + rhsType);
    }
  }
}
