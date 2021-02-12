package front_end.AST.function;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import front_end.AST.ASTNode;
import front_end.types.TYPE;

public class ParamListAST extends ASTNode {
    
    private List<ParamAST> parameters;

    public ParamListAST(ParserRuleContext ctx, List<ParamAST> parameters) {
        super(ctx);
        this.parameters = parameters;
    }

    @Override
    public void check() {
        for (ParamAST p: parameters) {
            p.wasChecked();
        }
    }

    public List<ParamAST> getParamASTs() {
        return parameters;
    }

    public List<TYPE> getParamTypes() {
        List<TYPE> paramTypes = new ArrayList<>();
        for(ParamAST n : parameters) {
            paramTypes.add(n.getType());
        }

        return paramTypes;
    }

}
