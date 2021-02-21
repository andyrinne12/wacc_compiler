package front_end.AST.expression;

import front_end.Visitor;
import front_end.types.ARRAY;
import front_end.types.TYPE;
import org.antlr.v4.runtime.ParserRuleContext;

public class UnaryOpExprAST extends ExpressionAST {

  private String unaryOp;
  private ExpressionAST expression;
  private TYPE expectedElemType;
  private String returnType;

  public UnaryOpExprAST(ParserRuleContext ctx, ExpressionAST expression, String unaryOp) {
    super(ctx);
    this.expression = expression;
    this.unaryOp = unaryOp;

    initialise_attr();
  }

  @Override
  public void check() {
    identObj = Visitor.ST.lookupAll(returnType);
    expression.check();
    if (expression.getIdentObj() == null) {
      return;
    }

    // check if the unary operator is compatible with the expression.
    TYPE type = expression.getEvalType();
    if (!type.equalsType(expectedElemType)) {
      error("The unary operator " + unaryOp + " received an unexcpeted type." +
          "expected: " + expectedElemType +
          "actual: " + type);
    }
  }

  private void initialise_attr() {
    TYPE intIdent = Visitor.ST.lookupAll("int").getType();
    TYPE boolIdent = Visitor.ST.lookupAll("bool").getType();
    TYPE charIdent = Visitor.ST.lookupAll("char").getType();
    switch (unaryOp) {
      case "!":
        expectedElemType = boolIdent;
        returnType = "bool";
        break;
      case "-":
        expectedElemType = intIdent;
        returnType = "int";
        break;
      case "len":
        expectedElemType = new ARRAY(null,
            0); // constructor params are not important in this scenario.
        returnType = "int";
        break;
      case "ord":
        expectedElemType = charIdent;
        returnType = "int";
        break;
      case "chr":
        expectedElemType = intIdent;
        returnType = "char";
    }
  }


  @Override
  public TYPE getEvalType() {
    return identObj.getType();
  }
}
