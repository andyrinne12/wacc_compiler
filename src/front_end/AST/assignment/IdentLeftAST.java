package front_end.AST.assignment;

import back_end.FunctionBody;
import back_end.instructions.Directive;
import back_end.operands.registers.Register;
import front_end.Visitor;
import front_end.types.FUNCTION;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class IdentLeftAST extends AssignmentLeftAST {

  private final String identName;

  public IdentLeftAST(ParserRuleContext ctx, String identName) {
    super(ctx);
    this.identName = identName;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    /* Empty */
    body.addInstr(new Directive("SHOULD BE EMPTY"));
  }

  @Override
  public void check() {
    identObj = Visitor.ST.lookupAll(identName);
    if (identObj == null) {
      error("Variable " + identName + " on the lhs has not been previously defined.");
    }
    if (identObj instanceof FUNCTION) {
      error("Function identifier on the left hand side of the assignment");
    }
  }

  @Override
  public String toString() {
    return identName;
  }

  @Override
  public TYPE getEvalType() {
    if (identObj == null) {
      return null;
    } else {
      return identObj.getType();
    }
  }

  public String getIdent() {
    return identName;
  }
}
