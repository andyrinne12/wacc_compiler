package front_end.AST;

import org.antlr.v4.runtime.ParserRuleContext;

import front_end.Visitor;

public abstract class ASTNode { // to be inherited by other ASTNodes.

    protected ParserRuleContext ctx;

    public ASTNode(ParserRuleContext ctx) {
        this.ctx = ctx;
    }

    public abstract void check();

    protected void error(String msg) {
        Visitor.error(ctx, msg);
    }
    
}
