package front_end.types;

public class VARIABLE extends IDENTIFIER {
    private TYPE type;

    public VARIABLE(TYPE type) {
        this.type = type;
    }
    
    public TYPE getType() {
        return type;
    }


}
