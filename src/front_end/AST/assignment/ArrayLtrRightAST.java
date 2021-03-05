package front_end.AST.assignment;

import back_end.FunctionBody;
import back_end.Utils;
import back_end.instructions.logical.MOV;
import back_end.instructions.store.LDR;
import back_end.instructions.store.STR;
import back_end.operands.immediate.ImmInt;
import back_end.operands.registers.OffsetRegister;
import back_end.operands.registers.Register;
import back_end.operands.registers.RegisterManager;
import front_end.AST.expression.ExpressionAST;
import front_end.types.ARRAY;
import front_end.types.BOOLEAN;
import front_end.types.CHAR;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class ArrayLtrRightAST extends AssignmentRightAST {

  private final List<ExpressionAST> array;

  public ArrayLtrRightAST(ParserRuleContext ctx,
      List<ExpressionAST> array) {
    super(ctx);
    this.array = array;
  }

  @Override
  public void check() {
    if (array.size() == 0) {
      identObj = new ARRAY(null, 0);
      return;
    }
    ExpressionAST first = array.get(0);
    first.check();
    TYPE firstType = first.getEvalType();
    for (int i = 1; i < array.size(); i++) {
      ExpressionAST exp = array.get(i);
      exp.check();
      TYPE expType = exp.getEvalType();
      if (!firstType.equalsType(expType)) {
        error(
            "Array literal contains items of different types. Expected: " + firstType + " actual: "
                + expType);
      }
    }
    identObj = new ARRAY(firstType, array.size());
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    int elemSize = 4;
    if (array.size() > 0) {
      if (array.get(0).getEvalType() instanceof BOOLEAN || array.get(0)
          .getEvalType() instanceof CHAR) {
        elemSize = 1;
      }
    }
    int size = ((ARRAY) identObj).getSize() * elemSize + 4;
    body.addInstr(
        new LDR(RegisterManager.getResultReg(), new ImmInt(size)));
    body.addInstr(Utils.MALLOC);
    body.addInstr(new MOV(freeRegs.get(0), RegisterManager.getResultReg()));
    List<Register> freeRegs2 = freeRegs.subList(1, freeRegs.size());
    for (int i = 0; i < array.size(); i++) {
      ExpressionAST expr = array.get(i);
      expr.assemble(body, freeRegs2);
      body.addInstr(
          new STR(freeRegs2.get(0),
              new OffsetRegister(freeRegs.get(0), i * elemSize + 4, false)));
    }
    body.addInstr(new LDR(freeRegs2.get(0), new ImmInt(array.size())));
    body.addInstr(
        new STR(freeRegs2.get(0),
            new OffsetRegister(freeRegs.get(0), 0, false)));
  }

  @Override
  public TYPE getEvalType() {
    return identObj.getType();
  }
}
