package front_end.AST.assignment;

import back_end.FunctionBody;
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
import front_end.types.FUNCTION;
import front_end.types.PARAM;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class FunctionCallRightAST extends AssignmentRightAST {

  private final String ident;
  private final List<ExpressionAST> argList;

  public FunctionCallRightAST(ParserRuleContext ctx, String ident,
      List<ExpressionAST> argList) {
    super(ctx);
    this.ident = ident;
    this.argList = argList;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    assert (identObj != null);
    for (int i = argList.size() - 1; i >= 0; i--) {
      ExpressionAST expr = argList.get(i);
      expr.assemble(body, freeRegs);
      body.addInstr(new STR(Condition.NONE, freeRegs.get(0),
          new OffsetRegister(RegisterManager.SP, -4, true)));
      Visitor.ST.pushOffset();
    }
    body.addInstr(new BL(Condition.NONE, ident));
    body.addInstr(new ADD(Condition.NONE, false, RegisterManager.SP, RegisterManager.SP,
        new ImmInt(argList.size() * 4)));
    body.addInstr(new MOV(Condition.NONE, false, freeRegs.get(0), RegisterManager.getResultReg()));
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
}
