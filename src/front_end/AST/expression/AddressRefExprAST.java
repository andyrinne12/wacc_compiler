package front_end.AST.expression;

import back_end.FunctionBody;
import back_end.instructions.arithmetic.ADD;
import back_end.operands.immediate.ImmInt;
import back_end.operands.registers.Register;
import back_end.operands.registers.RegisterManager;
import front_end.AST.assignment.AssignmentLeftAST;
import front_end.AST.assignment.IdentLeftAST;
import front_end.AST.assignment.PointerDerefAST;
import front_end.Visitor;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class AddressRefExprAST extends ExpressionAST {

  private final AssignmentLeftAST lhs;

  public AddressRefExprAST(ParserRuleContext ctx,
      AssignmentLeftAST lhs) {
    super(ctx);
    this.lhs = lhs;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    if (lhs instanceof IdentLeftAST) {
      IdentLeftAST ident = (IdentLeftAST) lhs;
      int offset = Visitor.ST.getIdentOffset(ident.getIdent()) + Visitor.ST.getJumpOffset();
      body.addInstr(new ADD(false, freeRegs.get(0), RegisterManager.SP, new ImmInt(offset)));
    } else {
      lhs.assemble(body, freeRegs);
    }
  }

  @Override
  public void check() {
    lhs.check();
    if (lhs instanceof PointerDerefAST) {
      error("referencing a de-reference address is redundant");
    }
    /* treat memory addresses as integers */
    identObj = Visitor.ST.lookupAll("int");
  }

  @Override
  public TYPE getEvalType() {
    return identObj.getType();
  }
}
