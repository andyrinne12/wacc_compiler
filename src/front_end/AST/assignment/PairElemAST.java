package front_end.AST.assignment;

import antlr.WACCParser.PairElemContext;
import back_end.FunctionBody;
import back_end.Utils;
import back_end.instructions.logical.MOV;
import back_end.instructions.store.LDR;
import back_end.operands.registers.OffsetRegister;
import back_end.operands.registers.Register;
import back_end.operands.registers.RegisterManager;
import front_end.AST.ASTNode;
import front_end.AST.expression.ExpressionAST;
import front_end.types.PAIR;
import front_end.types.TYPE;
import java.util.List;

public class PairElemAST extends ASTNode {

  private final PairElemEnum elem;
  private final ExpressionAST identExp;

  public PairElemAST(PairElemContext ctx, ExpressionAST identExp) {
    super(ctx);
    this.identExp = identExp;
    this.elem = ctx.elem.getText().equals("fst") ? PairElemEnum.FST : PairElemEnum.SND;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    identExp.assemble(body, freeRegs);
    body.addInstr(new MOV(RegisterManager.getResultReg(), freeRegs.get(0)));
    body.addInstr(Utils.CHECK_NULL_POINTER);
    int elemOfs = (elem == PairElemEnum.FST) ? 0 : 4;
    body.addInstr(new LDR(freeRegs.get(0), new OffsetRegister(freeRegs.get(0), elemOfs)));
  }

  @Override
  public void check() {
    identExp.check();
    if (!(identExp.getEvalType() instanceof PAIR)) {
      error("Invalid pair element access. Expression of type + " + identExp.getEvalType()
          + "is not a valid identifier.");
    }
    identObj = identExp.getEvalType();
  }

  public PairElemEnum getElem() {
    return elem;
  }

  //@Override
  public TYPE getEvalType() {
    PAIR pair = (PAIR) identObj;
    if (pair == null) {
      return null;
    }
    if (elem == PairElemEnum.FST) {
      return pair.getFirstType();
    } else {
      return pair.getSecondType();
    }
  }
}
