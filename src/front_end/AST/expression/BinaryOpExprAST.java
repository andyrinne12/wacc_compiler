package front_end.AST.expression;

import front_end.Visitor;
import front_end.types.ARRAY;
import front_end.types.PAIR;
import front_end.types.TYPE;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class BinaryOpExprAST extends ExpressionAST {

  private String binaryOp;
  private ExpressionAST expr1;
  private ExpressionAST expr2;
  private List<TYPE> expectedElemTypes;
  private String returnType;

  public BinaryOpExprAST(ParserRuleContext ctx, ExpressionAST expr1, ExpressionAST expr2,
      String binaryOp) {
    super(ctx);
    this.expr1 = expr1;
    this.expr2 = expr2;
    this.binaryOp = binaryOp;

    expectedElemTypes = new ArrayList<>();
    initialise_attr();
  }

  @Override
  public void check() {
    identObj = Visitor.ST.lookupAll(returnType);
    expr1.check();
    expr2.check();

    // if either of the 2 identifiers hasn't been previously defined:
    if ((expr1.getIdentObj() == null) || (expr2.getIdentObj() == null)) {
      return;
    }

    TYPE type1 = expr1.getEvalType();
    boolean expr1CorrectType = false;

    for (TYPE t : expectedElemTypes) {
      if (type1.equalsType(t)) {
        expr1CorrectType = true;
        break;
      }
    }

    if (expr1CorrectType) {
      TYPE type2 = expr2.getEvalType();
      if (!type1.equalsType(type2)) {
        error("Both LHS and RHS expressions have different types." +
            "\nExpected: " + expr1.getEvalType() +
            "\nActual: " + expr2.getEvalType());
      }
    } else {
      String expectedTypesInString = getTypesString();

      error(
          "The binary operator " + binaryOp + " received an unexpected type for the LHS expression."
              +
              "\nExpected types: {" + expectedTypesInString + "}" +
              "\nActual: " + type1);
    }

  }

  private String getTypesString() {
    List<String> listOfTypes = new ArrayList<>();

    for (TYPE t : expectedElemTypes) {
      listOfTypes.add((t.getClass().getName()));
    }

    return String.join(", ", listOfTypes);
  }

  private void initialise_attr() {
    TYPE intIdent = Visitor.ST.lookupAll("int").getType();
    TYPE boolIdent = Visitor.ST.lookupAll("bool").getType();
    TYPE charIdent = Visitor.ST.lookupAll("char").getType();
    TYPE strIdent = Visitor.ST.lookupAll("string").getType();
    switch (binaryOp) {
      case "+":
      case "-":
      case "*":
      case "/":
      case "%":
        expectedElemTypes.add(intIdent);
        returnType = "int";
        break;
      case ">":
      case ">=":
      case "<":
      case "<=":
        expectedElemTypes.add(intIdent);
        expectedElemTypes.add(charIdent);
        returnType = "bool";
        break;
      case "==":
      case "!=":
        expectedElemTypes.add(intIdent);
        expectedElemTypes.add(charIdent);
        expectedElemTypes.add(strIdent);
        expectedElemTypes.add(boolIdent);
        expectedElemTypes
            .add(new PAIR(null, null)); // constructor params are not important in this scenario.
        expectedElemTypes
            .add(new ARRAY(null, 0)); // constructor params are not important in this scenario.
        returnType = "bool";
        break;
      case "||":
      case "&&":
        expectedElemTypes.add(boolIdent);
        returnType = "bool";
        break;
    }
  }

  @Override
  public TYPE getEvalType() {
    return identObj.getType();
  }
}
