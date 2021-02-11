package front_end.AST.ExpressionAST;

import org.antlr.v4.runtime.ParserRuleContext;

import front_end.Visitor;
import front_end.types.IDENTIFIER;
import front_end.types.STRING;

public class StringExprAST extends ExpressionAST {
    
    private String strVal;

    private STRING strObj;

    public StringExprAST(ParserRuleContext ctx, String strVal) {
        super(ctx);
        this.strVal = strVal;
    }

    @Override
    public void check() {
        IDENTIFIER type = Visitor.ST.lookupAll("string");
        if (type == null) {
            error("Undefined type: string");
        }
        strObj = (STRING) type;
    }
}
