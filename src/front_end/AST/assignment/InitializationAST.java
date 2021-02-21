package front_end.AST.assignment;

import front_end.AST.statement.StatementAST;
import front_end.AST.type.TypeAST;
import front_end.Visitor;
import front_end.types.ARRAY;
import front_end.types.TYPE;
import org.antlr.v4.runtime.ParserRuleContext;

public class InitializationAST extends StatementAST {

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
    boolean success = true;

    type.check();
    TYPE actType = type.getTypeObj();

    if (Visitor.ST.lookup(ident.toString()) != null) {
      error("Variable " + ident.toString() + " already defined");
      success = false;
    }

    rhs.check();
    TYPE rhsType = rhs.getEvalType();

    //    if (rhsType == null) {
//      if (!(type.getTypeObj() instanceof ARRAY)) {
//        error("Cannot assign the empty array to non-array variable");
//        success = false;
//      } else {
//        rhsType = actType;
//      }
//    }

    if ((type.getTypeObj() instanceof ARRAY)) {
      ARRAY array = (ARRAY) type.getTypeObj();
      if (array.getElemType() == null) {
        rhsType = actType;
      }
    }
    if (!actType.equalsType(rhsType)) {
      error("Invalid type at initialization. Expected: " + actType + " actual: " + rhsType);
      success = false;
    }

    if (success) {
      Visitor.ST.add(ident.toString(), actType);
    }
  }
}
