package front_end.AST.ExpressionAST;

import org.antlr.v4.runtime.ParserRuleContext;

import front_end.Visitor;
import front_end.types.IDENTIFIER;

public class StringExprAST extends ExpressionAST {
    
    private String strVal;

    public StringExprAST(ParserRuleContext ctx, String strVal) {
        super(ctx);
        this.strVal = strVal;
    }

    @Override
    public void check() {
        IDENTIFIER identObj = Visitor.ST.lookupAll("string");
        if (identObj == null) {
            error("Undefined type: string");
        }
    }
}
