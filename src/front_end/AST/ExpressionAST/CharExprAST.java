package front_end.AST.ExpressionAST;

import org.antlr.v4.runtime.ParserRuleContext;

import front_end.Visitor;
import front_end.types.IDENTIFIER;

public class CharExprAST extends ExpressionAST {

    private String charVal;

    public CharExprAST(ParserRuleContext ctx, String charVal) {
        super(ctx);
        this.charVal = charVal;
    }

    @Override
    public void check() {
        IDENTIFIER identObj = Visitor.ST.lookupAll("char");
        if (identObj == null) {
            error("Undefined type: char");
        }
    }
    
}
