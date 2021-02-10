package front_end.types;

import front_end.SymbolTable;

public class FUNCTION extends TYPE {
    
    private TYPE returnType;
    private PARAM[] params;
    private SymbolTable ST;

    public FUNCTION(TYPE returnType, PARAM[] params, SymbolTable ST) {
        this.returnType = returnType;
        this.params = params;
        this.ST = ST;
    }

    public TYPE getType() {
        return this;
    }
}
