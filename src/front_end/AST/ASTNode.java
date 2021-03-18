package front_end.AST;

import back_end.FunctionBody;
import back_end.instructions.Directive;
import back_end.operands.registers.Register;
import front_end.Visitor;
import front_end.types.IDENTIFIER;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class ASTNode {

  protected ParserRuleContext ctx;
  protected IDENTIFIER identObj; // the semantic attribute of the class.

  public ASTNode(ParserRuleContext ctx) {
    this.ctx = ctx;
  }

  public void assemble(FunctionBody body, List<Register> freeRegs) {
    body.addInstr(new Directive(this.getClass().getSimpleName() + " undefined"));
  }

  public abstract void check();

  protected void error(String msg) {
    Visitor.error(ctx, msg);
  }

  protected void warning(String msg) {
    Visitor.warning(ctx, msg);
  }

  public IDENTIFIER getIdentObj() {
    return identObj;
  }

  public TYPE getType() {
    return identObj.getType();
  }
}
