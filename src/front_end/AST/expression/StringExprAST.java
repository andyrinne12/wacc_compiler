package front_end.AST.expression;

import front_end.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class StringExprAST extends ExpressionAST {
    
    private String strVal;

    public StringExprAST(ParserRuleContext ctx, String strVal) {
        super(ctx);
        this.strVal = strVal;
    }

    @Override
    public void check() {
        identObj = Visitor.ST.lookupAll("string");
        if (identObj == null) {
            error("Undefined type: string");
        }
    }
}
