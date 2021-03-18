package front_end.AST.assignment;

import back_end.FunctionBody;
import back_end.Utils;
import back_end.instructions.Condition;
import back_end.instructions.logical.MOV;
import back_end.instructions.store.LDR;
import back_end.instructions.store.STR;
import back_end.operands.immediate.ImmInt;
import back_end.operands.registers.OffsetRegister;
import back_end.operands.registers.Register;
import back_end.operands.registers.RegisterManager;
import front_end.types.BOOLEAN;
import front_end.types.CHAR;
import front_end.types.PAIR;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class NewPairRightAST extends AssignmentRightAST {

  private final ExprRightAST expr1;
  private final ExprRightAST expr2;

  public NewPairRightAST(ParserRuleContext ctx, ExprRightAST expr1, ExprRightAST expr2) {
    super(ctx);
    this.expr1 = expr1;
    this.expr2 = expr2;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    body.addInstr(new LDR(Condition.NONE, RegisterManager.getParamRegs().get(0), new ImmInt(8)));
    body.addInstr(Utils.MALLOC);
    body.addInstr(new MOV(Condition.NONE, false, freeRegs.get(0), RegisterManager.getResultReg()));
    List<Register> freeRegs1 = freeRegs.subList(1, freeRegs.size());

    int size1 = 4;
    int size2 = 4;
    if (expr1.getEvalType() instanceof PAIR) {
      size1 = 8;
    } else if (expr1.getEvalType() instanceof BOOLEAN || expr1.getEvalType() instanceof CHAR) {
      size1 = 1;
    }
    if (expr2.getEvalType() instanceof PAIR) {
      size2 = 8;
    } else if (expr2.getEvalType() instanceof BOOLEAN || expr2.getEvalType() instanceof CHAR) {
      size2 = 1;
    }
    expr1.assemble(body, freeRegs1);
    body.addInstr(
        new LDR(Condition.NONE, RegisterManager.getParamRegs().get(0), new ImmInt(size1)));
    body.addInstr(Utils.MALLOC);
    body.addInstr(new STR(Condition.NONE, freeRegs1.get(0),
        new OffsetRegister(RegisterManager.getResultReg(), 0, false)));
    body.addInstr(new STR(Condition.NONE, RegisterManager.getResultReg(),
        new OffsetRegister(freeRegs.get(0), 0, false)));

    expr2.assemble(body, freeRegs1);
    body.addInstr(
        new LDR(Condition.NONE, RegisterManager.getParamRegs().get(0), new ImmInt(size2)));
    body.addInstr(Utils.MALLOC);
    body.addInstr(new STR(Condition.NONE, freeRegs1.get(0),
        new OffsetRegister(RegisterManager.getResultReg(), 0, false)));
    body.addInstr(new STR(Condition.NONE, RegisterManager.getResultReg(),
        new OffsetRegister(freeRegs.get(0), 4, false)));
  }

  @Override
  public void check() {
    expr1.check();
    expr2.check();
    identObj = new PAIR(expr1.getEvalType(), expr2.getEvalType());
  }

  @Override
  public TYPE getEvalType() {
    return identObj.getType();
  }
}
