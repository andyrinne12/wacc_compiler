package front_end.AST.assignment;

import back_end.FunctionBody;
import back_end.Utils;
import back_end.instructions.Condition;
import back_end.instructions.arithmetic.ADD;
import back_end.instructions.branch.BL;
import back_end.instructions.logical.MOV;
import back_end.instructions.store.STR;
import back_end.operands.immediate.ImmInt;
import back_end.operands.registers.OffsetRegister;
import back_end.operands.registers.Register;
import back_end.operands.registers.RegisterManager;
import front_end.AST.expression.ExpressionAST;
import front_end.Visitor;
import front_end.types.BOOLEAN;
import front_end.types.CHAR;
import front_end.types.FUNCTION;
import front_end.types.PARAM;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class FunctionCallRightAST extends AssignmentRightAST {

  private final String ident;
  private final List<ExpressionAST> argList;
  private final String MALLOC = "malloc";

  public FunctionCallRightAST(ParserRuleContext ctx, String ident,
      List<ExpressionAST> argList) {
    super(ctx);
    this.ident = ident;
    this.argList = argList;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    assert (identObj != null);
    int totalOffset = 0;

    if (ident.equals(MALLOC)) {
      malloc(body, freeRegs);
      return;
    }

    for (int i = argList.size() - 1; i >= 0; i--) {
      ExpressionAST expr = argList.get(i);
      expr.assemble(body, freeRegs);

      Condition cond = Condition.NONE;
      int offset = -4;
      TYPE exprType = expr.getEvalType();
      if (exprType instanceof BOOLEAN || exprType instanceof CHAR) {
        offset = -1;
        cond = Condition.B;
      }
      body.addInstr(
          new STR(cond, freeRegs.get(0), new OffsetRegister(RegisterManager.SP, offset, true)));
      Visitor.ST.pushOffset(Math.abs(offset));

      totalOffset += Math.abs(offset);
    }

    body.addInstr(new BL(Condition.NONE, "f_" + ident));
    body.addInstr(new ADD(false, RegisterManager.SP, RegisterManager.SP, new ImmInt(totalOffset)));
    body.addInstr(new MOV(freeRegs.get(0), RegisterManager.getResultReg()));
    Visitor.ST.resetOffset();
  }

  @Override
  public void check() {
    identObj = Visitor.ST.lookupAll(ident);
    if (identObj == null || !(identObj instanceof FUNCTION)) {
      error("Function " + ident + " is not defined.");
    } else {
      FUNCTION func = (FUNCTION) identObj;
      List<PARAM> params = func.getParams();
      if (argList.size() != params.size()) {
        error("Invalid number of arguments on function call");
        return;
      }
      for (int i = 0; i < argList.size(); i++) {
        ExpressionAST arg = argList.get(i);
        arg.check();
        //System.out.println(arg.getClass().getName() + " " + params.get(i));
        if (!(arg.getEvalType().equalsType(params.get(i).getType()))) {
          error("Invalid argument type for arg " + i);
        }
      }
    }
  }

  @Override
  public TYPE getEvalType() {
    if (identObj == null) {
      return null;
    }
    FUNCTION func = (FUNCTION) identObj;
    return func.getReturnType();
  }

  private void malloc(FunctionBody body, List<Register> freeRegs) {
    argList.get(0).assemble(body, freeRegs);
    body.addInstr(new MOV(RegisterManager.getResultReg(), freeRegs.get(0)));
    body.addInstr(Utils.MALLOC);
    body.addInstr(new MOV(freeRegs.get(0), RegisterManager.getResultReg()));
  }
}
