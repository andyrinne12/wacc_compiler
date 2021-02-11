package front_end.AST.type;

import org.antlr.v4.runtime.ParserRuleContext;

import front_end.AST.ASTNode;

public abstract class TypeAST extends ASTNode {
    public TypeAST(ParserRuleContext ctx) {
        super(ctx);
    }
}
