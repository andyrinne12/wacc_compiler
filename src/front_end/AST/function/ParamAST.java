package front_end.AST.function;

import org.antlr.v4.runtime.ParserRuleContext;

import front_end.Visitor;
import front_end.AST.ASTNode;
import front_end.AST.type.TypeAST;
import front_end.types.*;

public class ParamAST extends ASTNode {

    private TypeAST type;
    private String ident;

    public ParamAST(ParserRuleContext ctx, TypeAST type, String ident) {
        super(ctx);
        this.type = type;
        this.ident = ident;
    }
    
    @Override
    public void check() {
        if (type.getTypeObj() instanceof ARRAY) {
            TYPE arrayElemType = ((ARRAY) type.getTypeObj()).getElemType();
            ARRAY arrayType = new ARRAY(arrayElemType, 0); // array size doesn't matter in this scenario.
            identObj = new PARAM(arrayType);
        }
        else if (type.getTypeObj() instanceof PAIR) {
            TYPE firstType = ((PAIR) type.getIdentObj()).getFirstType();
            TYPE secondType = ((PAIR) type.getIdentObj()).getFirstType();
            PAIR pairType = new PAIR(firstType, secondType);
            identObj = new PARAM(pairType);
        }
        else {
            String typeNameInString = type.getTypeObj().toString();
            IDENTIFIER T = Visitor.ST.lookupAll(typeNameInString);
            identObj = new PARAM((TYPE) T);
        }
    }

    public String getIdent() {
        return ident;
    }

}
