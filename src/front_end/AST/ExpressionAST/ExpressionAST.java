package front_end.AST.ExpressionAST;

import front_end.AST.ASTNode;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class ExpressionAST extends ASTNode {
    
    public ExpressionAST(ParserRuleContext ctx) {
        super(ctx);
    }

}
