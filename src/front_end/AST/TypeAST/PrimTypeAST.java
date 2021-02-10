package front_end.AST.TypeAST;

import org.antlr.v4.runtime.ParserRuleContext;

import front_end.Visitor;
import front_end.types.IDENTIFIER;
import front_end.types.TYPE;

public class PrimTypeAST extends TypeAST {

    private String typeName;
    private TYPE typeObj;

    public PrimTypeAST(ParserRuleContext ctx, String typeName) {
        super(ctx);
        this.typeName = typeName;
    }

    @Override
    public void check() {
        // check if type is in the symbol table.
        IDENTIFIER type = Visitor.ST.lookupAll(typeName);
        if (type == null) { // the type doesn't exist in the symbol table.
            error("Undefined type.");
        }
        typeObj = (TYPE) type;
    }
    
}