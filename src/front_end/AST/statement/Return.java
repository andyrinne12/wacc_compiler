package front_end.AST.statement;

import front_end.AST.expression.ExpressionAST;
import front_end.AST.type.TypeAST;
import front_end.types.TYPE;

import org.antlr.v4.runtime.ParserRuleContext;

public class Return extends Statement {

  private ExpressionAST exprAST;
  private TypeAST expectedReturnTypeAST; // the return type as specified by the enclosing function.

  public Return(ParserRuleContext ctx, ExpressionAST exprAST, TypeAST expectedReturnTypeAST) {
    super(ctx);
    this.exprAST = exprAST;
    this.expectedReturnTypeAST = expectedReturnTypeAST;
  }

  @Override
  public void check() {
    exprAST.wasChecked();

    // check if the expected return type is the same as the expression's type
    TYPE exprType = exprAST.getEvalType();
    TYPE expectedType = expectedReturnTypeAST.getTypeObj();
    if (!exprType.equals(expectedType)) {
      error("Return type expected by the function: " + expectedType +
            "\nActual type: " + exprType);
    }
  }
}
