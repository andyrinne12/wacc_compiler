package front_end.AST.statement;

import back_end.FunctionBody;
import back_end.Utils;
import back_end.instructions.Condition;
import back_end.instructions.branch.BL;
import back_end.instructions.logical.MOV;
import back_end.operands.registers.Register;
import back_end.operands.registers.RegisterManager;
import front_end.AST.expression.ExpressionAST;
import front_end.types.*;

import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class PrintAST extends StatementAST {
  private ExpressionAST expression;

  public PrintAST(ParserRuleContext ctx, ExpressionAST expression) {
    super(ctx);
    this.expression = expression;
  }

  @Override
  public void check() {
    expression.check();
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    expression.assemble(body, freeRegs);
    body.addInstr(new MOV(RegisterManager.getParamRegs().get(0), freeRegs.get(0)));

    // get the specific print function name
    String nameOfPrintFunction = Utils.getPrintFunctionName(expression.getEvalType()); 

    body.addInstr(new BL(Condition.NONE, nameOfPrintFunction));

    // push the function into the CodeGen.lastFuncs list.
    Utils.addFunc(nameOfPrintFunction, freeRegs.get(0));

  }
}
