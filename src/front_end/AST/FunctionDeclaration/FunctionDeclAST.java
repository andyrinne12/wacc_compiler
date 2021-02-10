package front_end.AST.FunctionDeclaration;

import org.antlr.v4.runtime.ParserRuleContext;

import front_end.AST.ASTNode;
import front_end.AST.TypeAST.TypeAST;

public class FunctionDeclAST extends ASTNode {

    private String returnTypeName;
    private String functionName;

    private TypeAST returnType;

    // to extend the constructor method to take in other params.
    public FunctionDeclAST(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public void check() {
        // to implement
    }
    
}
