package front_end.AST.ExpressionAST;

import org.antlr.v4.runtime.ParserRuleContext;

import front_end.Visitor;
import front_end.types.*;

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

        // check if the unary operator is compatible with the expression.
        Class expressionClass = expression.getType().getClass();
        if (!(expressionClass.equals(expectedElemType.getClass()))) {
           error("The unary operator " + unaryOp + " received an unexcpeted type." + 
           "\nExpected: " + expectedElemType.getClass().getName() + 
           "\nActual: " + expressionClass.getName());
        }
    }

    private void initialise_attr(){
        switch(unaryOp) {
            case "!":
                expectedElemType = new BOOLEAN();
                returnType = "bool";
                break;
            case "-":
                expectedElemType = new INT();
                returnType = "int";
                break;
            case "len":
                expectedElemType = new ARRAY(null, 0); // constructor params are not important in this scenario.
                returnType = "int";
                break;
            case "ord":
                expectedElemType = new CHAR();
                returnType = "int";
                break;
            case "chr":
                expectedElemType = new INT();
                returnType = "char";
        }
    }


}
