package front_end.AST.statement;

import front_end.AST.expression.ExpressionAST;
import front_end.AST.type.TypeAST;
import front_end.types.TYPE;
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
}
