package front_end.AST.expression;

import front_end.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class CharExprAST extends ExpressionAST {

    private String charVal;

    public CharExprAST(ParserRuleContext ctx, String charVal) {
        super(ctx);
        this.charVal = charVal;
    }

    @Override
    public void check() {
        identObj = Visitor.ST.lookupAll("char");
        if (identObj == null) {
            error("Undefined type: char");
        }
    }
    
}
