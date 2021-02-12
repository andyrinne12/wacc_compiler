package front_end.AST.assignment;

import front_end.AST.statement.Statement;
import front_end.AST.type.TypeAST;
import front_end.Visitor;
import front_end.types.TYPE;
import front_end.types.VARIABLE;
import org.antlr.v4.runtime.ParserRuleContext;

public class InitializationAST extends Statement {

  private final TypeAST type;
  private final IdentLeftAST ident;
  private final AssignmentRightAST rhs;

  public InitializationAST(ParserRuleContext ctx, TypeAST type, IdentLeftAST ident,
      AssignmentRightAST rhs) {
    super(ctx);
    this.type = type;
    this.ident = ident;
    this.rhs = rhs;
  }

  @Override
  public void check() {
    TYPE actType = type.getTypeObj();
    if (Visitor.ST.lookup(ident.toString()) != null) {
      error("Variable " + ident.toString() + " already defined");
    }
    Visitor.ST.add(ident.toString(), new VARIABLE(actType));
    rhs.check();
    TYPE rhsType = rhs.getEvalType();
    if (!actType.equalsType(rhsType)) {
      error("Invalid type at initialization. Expected: " + actType + " actual: " + rhsType);
    }
  }
}
