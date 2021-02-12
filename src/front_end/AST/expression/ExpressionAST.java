package front_end.AST.expression;

import front_end.AST.ASTNode;
import front_end.types.TYPE;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class ExpressionAST extends ASTNode {
    
    public ExpressionAST(ParserRuleContext ctx) {
        super(ctx);
    }

    public abstract TYPE getEvalType();

}
