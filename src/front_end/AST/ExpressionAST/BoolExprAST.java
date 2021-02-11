package front_end.AST.ExpressionAST;

import org.antlr.v4.runtime.ParserRuleContext;

import front_end.Visitor;
import front_end.types.IDENTIFIER;

public class BoolExprAST extends ExpressionAST {
    // syntactic attr:
    private boolean boolVal;

    public BoolExprAST(ParserRuleContext ctx, String boolString) {
        super(ctx);
        if (boolString.equals("true")) {
            boolVal = true;
        }
        else {
            boolVal = false;
        }
    }

    @Override
    public void check() {
        IDENTIFIER identObj = Visitor.ST.lookupAll("bool");
        if (identObj == null) {
            error("Undefined type: bool");
        }
    }
}
