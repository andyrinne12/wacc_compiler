package front_end.AST.statement;

import back_end.FunctionBody;
import back_end.instructions.arithmetic.ADD;
import back_end.instructions.logical.MOV;
import back_end.instructions.store.POP;
import back_end.operands.immediate.ImmInt;
import back_end.operands.registers.Register;
import back_end.operands.registers.RegisterManager;
import front_end.AST.expression.ExpressionAST;
import front_end.AST.type.TypeAST;
import front_end.Visitor;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class ReturnAST extends StatementAST {

  private ExpressionAST exprAST;
  private TypeAST expectedReturnTypeAST; // the return type as specified by the enclosing function.

  public ReturnAST(ParserRuleContext ctx, ExpressionAST exprAST, TypeAST expectedReturnTypeAST) {
    super(ctx);
    this.exprAST = exprAST;
    this.expectedReturnTypeAST = expectedReturnTypeAST;
  }

  @Override
  public void check() {
    exprAST.check();
    expectedReturnTypeAST.check();

    // check if the expected return type is the same as the expression's type
    TYPE exprType = exprAST.getEvalType();
    TYPE expectedType = expectedReturnTypeAST.getTypeObj();
    if (!exprType.equalsType(expectedType)) {
      error(" return type expected by the function: " + expectedType +
          "actual: " + exprType);
    }
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    exprAST.assemble(body,freeRegs);
    int size = Visitor.ST.setFrameSize();

    body.addInstr(new MOV(RegisterManager.getParamRegs().get(0),freeRegs.get(0)));
    if(size != 0) {
      body.addInstr(new ADD(false, RegisterManager.SP, RegisterManager.SP, new ImmInt(size)));
    }
    body.addInstr(new POP(RegisterManager.PC));
  }
}
