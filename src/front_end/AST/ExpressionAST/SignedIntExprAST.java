package front_end.AST.ExpressionAST;

import org.antlr.v4.runtime.ParserRuleContext;

import front_end.Visitor;

public class SignedIntExprAST extends ExpressionAST {
    
    private String intSign;
    private String value;

    public SignedIntExprAST(ParserRuleContext ctx, String intSign, String value) {
        super(ctx);
        this.value = value;
        this.intSign = intSign;
    }

    @Override
    public void check() {
        identObj = Visitor.ST.lookupAll("int");
        if (identObj == null) {
            error("Undefined type: int");
        }
    }

}
