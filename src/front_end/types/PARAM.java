package front_end.types;

public class PARAM extends IDENTIFIER {

    private final TYPE type;

    public PARAM(TYPE type) {
        this.type = type;
    }

    public TYPE getType() {
        return type;
    }
}
