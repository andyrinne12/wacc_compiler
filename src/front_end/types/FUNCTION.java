package front_end.types;

import front_end.SymbolTable;

public class FUNCTION extends TYPE {
    
    private final TYPE returnType;
    private final PARAM[] params;
    private final SymbolTable ST;

    public FUNCTION(TYPE returnType, PARAM[] params, SymbolTable ST) {
        this.returnType = returnType;
        this.params = params;
        this.ST = ST;
    }

    public TYPE getType() {
        return this;
    }

    public PARAM[] getParams() {
        return params;
    }

    public TYPE getReturnType() {
        return returnType;
    }

    @Override
    public boolean equalsType(TYPE type) {
        return false;
    }
}
