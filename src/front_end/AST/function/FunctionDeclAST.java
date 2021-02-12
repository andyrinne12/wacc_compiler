package front_end.AST.function;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import front_end.Visitor;
import front_end.AST.ASTNode;
import front_end.AST.statement.Statement;
import front_end.AST.type.TypeAST;
import front_end.types.*;

public class FunctionDeclAST extends ASTNode {

    private String functionName;
    private TypeAST returnType;
    private ParamListAST paramList;
    private Statement statement; // a statement AST. 
    // Statements in a function are not checked in this object.
    // Rather, they're checked in the visitFunc() metod in the Visitor class.

    public FunctionDeclAST(ParserRuleContext ctx, String functionName, TypeAST returnType, ParamListAST paramList) {
        super(ctx);
        this.functionName = functionName;
        this.returnType = returnType; // no need to check return type, as it has been checked before the FunctionDeclAST object is created.
        this.paramList = paramList; // no need to check the parameters list, as it has been checked before the FunctionDeclAST object is created.
    }

    @Override
    public void check() {
        // check function name
        IDENTIFIER funcNameTemp = Visitor.ST.lookupAll(functionName);
        if (funcNameTemp != null) {
            error(funcNameTemp + "has already been declared");
        }
        else {
            // Inside this class, Visitor.ST references the symbol table of the function.
            identObj = new FUNCTION(returnType.getType(), paramList.getPARAMs(), Visitor.ST);
            Visitor.ST.getParentST().add(functionName, identObj); // add function name to enclosing symbol table.
        }

        // add parameters into the ST of the function.
        if (paramList != null) {
            List<ParamAST> paramASTs = paramList.getParamASTs();
            for (ParamAST p: paramASTs) {
                Visitor.ST.add(p.getIdent(), p.getType());
            }
        }
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }
    
}
