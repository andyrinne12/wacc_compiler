package front_end.AST.ExpressionAST;

import org.antlr.v4.runtime.ParserRuleContext;

import front_end.Visitor;
import front_end.types.BOOLEAN;
import front_end.types.IDENTIFIER;

public class BoolExprAST extends ExpressionAST {
    // syntactic attr:
    private boolean boolVal;

    // semantic attr:
    private BOOLEAN boolObj;

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
        // check if type is in the symbol table.
        IDENTIFIER type = Visitor.ST.lookupAll("bool");
        if (type == null) { // the type doesn't exist in the symbol table.
            error("Undefined type: bool");
        }
        boolObj = (BOOLEAN) type;
    }
}