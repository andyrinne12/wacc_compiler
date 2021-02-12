package front_end.AST;

import org.antlr.v4.runtime.ParserRuleContext;

import front_end.Visitor;
import front_end.types.IDENTIFIER;
import front_end.types.TYPE;

public abstract class ASTNode { // to be inherited by other ASTNodes.

    protected ParserRuleContext ctx;
    protected IDENTIFIER identObj; // the semantic attribute of the class.
    protected boolean isChecked;  // variable for marking if the node has already been checked

    public ASTNode(ParserRuleContext ctx) {
        this.ctx = ctx;
    }

    public abstract void check();

    protected void error(String msg) {
        Visitor.error(ctx, msg);
    }

    public IDENTIFIER getIdentObj() {
        return identObj;
    }

    public TYPE getType() {
        return identObj.getType();
    }

    public void wasChecked(){
        if(!isChecked) {
            check();
            isChecked = true;
        }
    }
    
}
