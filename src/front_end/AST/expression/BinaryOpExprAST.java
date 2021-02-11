package front_end.AST.expression;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import front_end.Visitor;
import front_end.types.*;

public class BinaryOpExprAST extends ExpressionAST {

    private String binaryOp;
    private ExpressionAST expr1;
    private ExpressionAST expr2;
    private List<TYPE> expectedElemTypes;
    private String returnType;
    
    public BinaryOpExprAST(ParserRuleContext ctx, ExpressionAST expr1, ExpressionAST expr2, String binaryOp) {
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
        expr1.wasChecked();
        expr2.wasChecked();

        Class expr1Class = expr1.getIdentObj().getClass();
        boolean expr1CorrectType = false;

        for (TYPE t: expectedElemTypes) {
            if (expr1Class.equals(t.getClass())) {
                expr1CorrectType = true;
                break;
            }
        }

        if (expr1CorrectType) {
            Class expr2Class = expr2.getIdentObj().getClass();
            if (!expr1Class.equals(expr2Class)) {
                error("Both LHS and RHS expressions have different types." +
                "\nExpected: " + expr1Class.getName() +
                "\nActual: " + expr2Class.getName());
            }
        }
        else {
            String expectedTypesInString = getTypesString();

            error("The binary operator " + binaryOp + " received an unexpected type for the LHS expression." + 
           "\nExpected types: {" + expectedTypesInString + "}" + 
           "\nActual: " + expr1Class.getName());
        }

    }

    private String getTypesString() {
        List<String> listOfTypes = new ArrayList<>();

        for (TYPE t: expectedElemTypes) {
            listOfTypes.add((t.getClass().getName()));
        }

        return String.join(", ", listOfTypes);
    }

    private void initialise_attr() {
        switch(binaryOp) {
            case "+":
            case "-":
            case "*":
            case "/":
            case "%":
                expectedElemTypes.add(new INT());
                returnType = "int";
                break;
            case ">":
            case ">=":
            case "<":
            case "<=":
                expectedElemTypes.add(new INT());
                expectedElemTypes.add(new CHAR());
                returnType = "bool";
                break;
            case "==":
            case "!=":
                expectedElemTypes.add(new INT());
                expectedElemTypes.add(new CHAR());
                expectedElemTypes.add(new STRING());
                expectedElemTypes.add(new BOOLEAN());
                expectedElemTypes.add(new PAIR(null, null)); // constructor params are not important in this scenario.
                expectedElemTypes.add(new ARRAY(null, 0)); // constructor params are not important in this scenario.
                returnType = "bool";
                break;
            case "||":
            case "&&":
                expectedElemTypes.add(new BOOLEAN());
                returnType = "bool";
        }
    }

}
