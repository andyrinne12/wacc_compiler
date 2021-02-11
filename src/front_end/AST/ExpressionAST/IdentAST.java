package front_end.AST.ExpressionAST;

import org.antlr.v4.runtime.ParserRuleContext;

import front_end.Visitor;
import front_end.types.IDENTIFIER;

public class IdentAST extends ExpressionAST {
    
    private String identName;
    private IDENTIFIER identObj;

    public IdentAST(ParserRuleContext ctx, String identName) {
        super(ctx);
        this.identName = identName;
    }

    @Override
    public void check() {
        identObj = Visitor.ST.lookupAll(identName);
        if (identObj == null) {
            error("has not been previously defined.");
        }
    }

}
