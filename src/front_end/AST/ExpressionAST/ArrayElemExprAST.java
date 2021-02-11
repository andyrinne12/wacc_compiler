package front_end.AST.ExpressionAST;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import front_end.Visitor;
import front_end.types.ARRAY;
import front_end.types.IDENTIFIER;
import front_end.types.INT;

// the AST for accessing elements in an array, exp: arr[1], or arr[1][0].
public class ArrayElemExprAST extends ExpressionAST {

    private String arrayIdent; // name of the array
    private List<ExpressionAST> exprList;
    
    public ArrayElemExprAST(ParserRuleContext ctx, String ident, List<ExpressionAST> exprList) {
        super(ctx);
        arrayIdent = ident;
        this.exprList = exprList;
    }

    @Override
    public void check() {
        IDENTIFIER arrIdentObj = Visitor.ST.lookupAll(arrayIdent);
        if (arrIdentObj == null) {
            error("undeclared variable");
        }
        else {
            for (ExpressionAST expr: exprList) {
                expr.check();
                if (!(expr.getType() instanceof INT)) {
                    Class temp = expr.getType().getClass();
                    error("An ArrayElement only accepts integers between its square brackets. The type given here is " + temp.getName());
                }
            }

            identObj = ((ARRAY) arrIdentObj).getElemType(); 
            // identObj will end up having the type of the array's elements.
            // Eg: for an array int[], identObj will be int. 
        }
    }

}
