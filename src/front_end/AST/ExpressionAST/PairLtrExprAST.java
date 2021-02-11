package front_end.AST.ExpressionAST;

import org.antlr.v4.runtime.ParserRuleContext;

import front_end.types.PAIR;

public class PairLtrExprAST extends ExpressionAST {

    private String nullStr;
    private PAIR pairObj;
    
    public PairLtrExprAST(ParserRuleContext ctx, String str) {
        super(ctx);
        nullStr = str;
        pairObj = new PAIR(null, null);
    }

    @Override
    public void check() {
        // to do
    }

}
