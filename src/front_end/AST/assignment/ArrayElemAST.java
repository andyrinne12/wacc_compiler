package front_end.AST.assignment;

import antlr.WACCParser.ArrayElemContext;
import back_end.CodeGen;
import back_end.FunctionBody;
import back_end.Utils;
import back_end.instructions.arithmetic.ADD;
import back_end.instructions.logical.MOV;
import back_end.instructions.store.LDR;
import back_end.operands.immediate.ImmInt;
import back_end.operands.registers.OffsetRegister;
import back_end.operands.registers.Register;
import back_end.operands.registers.RegisterManager;
import back_end.operands.registers.Shift;
import back_end.operands.registers.ShiftedRegister;
import front_end.AST.expression.ExpressionAST;
import front_end.Visitor;
import front_end.types.ARRAY;
import front_end.types.INT;
import front_end.types.TYPE;
import java.util.List;

public class ArrayElemAST extends AssignmentLeftAST {

  private final String arrayIdent;
  private final List<ExpressionAST> indices;

  private static boolean error;

  public ArrayElemAST(ArrayElemContext ctx, List<ExpressionAST> indices) {
    super(ctx);
    arrayIdent = ctx.IDENT().getText();
    this.indices = indices;
    error = false;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    /* May need to push registers if all are used (quite improbable) */
    body.addInstr(new ADD(false, freeRegs.get(0), RegisterManager.SP,
        new ImmInt(Visitor.ST.getIdentOffset(arrayIdent) + Visitor.ST
            .getJumpOffset())));
    List<Register> freeRegs1 = freeRegs.subList(1, freeRegs.size());
    for (ExpressionAST expr : indices) {
      expr.assemble(body, freeRegs1);

      if(!error) {
        CodeGen.addData("ArrayIndexOutOfBoundsError: negative index\\n\\0");
        CodeGen.addData("ArrayIndexOutOfBoundsError: index too large\\n\\0");

        Utils.addFunc("p_check_array_bounds", freeRegs1.get(0));
        error = true;
      }

      body.addInstr(new LDR(freeRegs.get(0), new OffsetRegister(freeRegs.get(0))));
      body.addInstr(new MOV(RegisterManager.getParamRegs().get(0), freeRegs1.get(0)));
      body.addInstr(new MOV(RegisterManager.getParamRegs().get(1), freeRegs.get(0)));
      body.addInstr(Utils.CHECK_ARRAY_BOUNDS);
      body.addInstr(new ADD(false, freeRegs.get(0), freeRegs.get(0), new ImmInt(4)));
      body.addInstr(new ADD(false, freeRegs.get(0), freeRegs.get(0),
          new ShiftedRegister(freeRegs1.get(0), Shift.LSL, new ImmInt(2))));
    }
  }

  @Override
  public void check() {
    identObj = Visitor.ST.lookupAll(arrayIdent);
    if (identObj == null) {
      error(arrayIdent + " undeclared variable");
    } else {
      if (!(identObj instanceof ARRAY)) {
        error("Invalid access on non-array variable " + arrayIdent);
      }
      for (ExpressionAST expr : indices) {
        expr.check();
        if (!(expr.getIdentObj() instanceof INT)) {
          error(
              "An ArrayElement only accepts integers between its square brackets. The type given here is "
                  + expr.getIdentObj().getType());
        }
      }
    }
  }

  @Override
  public TYPE getEvalType() {
    TYPE type = (TYPE) identObj;
    for (int i = 0; i < indices.size(); i++) {
      if (!(type instanceof ARRAY)) {
        return null;
      }
      ARRAY array = (ARRAY) type;
      type = array.getElemType();
    }
    return type;
  }
}
