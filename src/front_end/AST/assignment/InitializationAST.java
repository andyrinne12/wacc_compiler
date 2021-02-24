package front_end.AST.assignment;

import back_end.FunctionBody;
import back_end.instructions.Condition;
import back_end.instructions.store.STR;
import back_end.operands.registers.OffsetRegister;
import back_end.operands.registers.Register;
import back_end.operands.registers.RegisterManager;
import front_end.AST.statement.StatementAST;
import front_end.AST.type.TypeAST;
import front_end.Visitor;
import front_end.types.ARRAY;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class InitializationAST extends StatementAST {

  private final TypeAST type;
  private final IdentLeftAST ident;
  private final AssignmentRightAST rhs;

  public InitializationAST(ParserRuleContext ctx, TypeAST type, IdentLeftAST ident,
      AssignmentRightAST rhs) {
    super(ctx);
    this.type = type;
    this.ident = ident;
    this.rhs = rhs;
  }

  @Override
  public void check() {
    boolean success = true;

    type.check();
    TYPE actType = type.getTypeObj();

    if (Visitor.ST.lookup(ident.toString()) != null) {
      error("Variable " + ident.toString() + " already defined");
      success = false;
    }

    rhs.check();

    if (rhs.getEvalType() == null) {
      return;
    }

    TYPE rhsType = rhs.getEvalType();

    if ((type.getTypeObj() instanceof ARRAY)) {
      ARRAY array = (ARRAY) type.getTypeObj();
      if (array.getElemType() == null) {
        rhsType = actType;
      }
    }
    if (!actType.equalsType(rhsType)) {
      error("Invalid type at initialization. Expected: " + actType + " actual: " + rhsType);
      success = false;
    }

    if (success) {
      Visitor.ST.add(ident.toString(), actType);
    }
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    rhs.assemble(body, freeRegs);
    int offset = Visitor.ST.storeVariable(ident.toString());
    STR init = new STR(Condition.NONE, freeRegs.get(0),
        new OffsetRegister(RegisterManager.SP, offset, false));
    body.addInstr(init);
  }
}
