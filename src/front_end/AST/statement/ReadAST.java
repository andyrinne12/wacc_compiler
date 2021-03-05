package front_end.AST.statement;

import back_end.FunctionBody;
import back_end.Utils;
import back_end.instructions.Condition;
import back_end.instructions.arithmetic.ADD;
import back_end.instructions.branch.BL;
import back_end.instructions.logical.MOV;
import back_end.operands.immediate.ImmInt;
import back_end.operands.registers.Register;
import back_end.operands.registers.RegisterManager;
import front_end.AST.assignment.AssignmentLeftAST;
import front_end.AST.assignment.IdentLeftAST;
import front_end.Visitor;
import front_end.types.IDENTIFIER;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class ReadAST extends StatementAST {

  private final AssignmentLeftAST expr;

  public ReadAST(ParserRuleContext ctx, AssignmentLeftAST expr) {
    super(ctx);
    this.expr = expr;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    if (expr instanceof IdentLeftAST) {
      IdentLeftAST ident = (IdentLeftAST) expr;
      int offset = Visitor.ST.getIdentOffset(ident.getIdent()) + Visitor.ST.getJumpOffset();
      body.addInstr(new ADD(false, freeRegs.get(0), RegisterManager.SP, new ImmInt(offset)));
    } else {
      expr.assemble(body, freeRegs);
    }
    body.addInstr(new MOV(RegisterManager.getResultReg(), freeRegs.get(0)));
    String readFuncLabel = Utils.getReadFunctionName(expr.getEvalType());
    Utils.addFunc(readFuncLabel, RegisterManager.LR);
    body.addInstr(new BL(Condition.NONE, readFuncLabel));
  }

  @Override
  public void check() {
    expr.check();
    TYPE evalType = expr.getEvalType();

    if (evalType == null) {
      error("cannot read on null reference");
    } else {

      IDENTIFIER intType = Visitor.ST.lookupAll("int");

      IDENTIFIER charType = Visitor.ST.lookupAll("char");

      if (!evalType.equalsType(intType.getType()) && !evalType.equalsType(charType.getType())) {
        error("read statement can take only int or char types");
      }
    }
  }
}
