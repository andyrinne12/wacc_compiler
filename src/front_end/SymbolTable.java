package front_end;

import java.util.HashMap;
import java.util.Map;

import front_end.types.IDENTIFIER;

public class SymbolTable {
    
    SymbolTable parentST;
    Map<String, IDENTIFIER> dictionary;

    public SymbolTable(SymbolTable parentST) {
        this.parentST = parentST;
        dictionary = new HashMap<>();
    }

    public void add(String name, IDENTIFIER obj) {
        dictionary.put(name, obj);
    }

    public IDENTIFIER lookup(String name) {
        return dictionary.get(name);
    }

    public IDENTIFIER lookupAll(String name) {
        SymbolTable ST = this;
        while (ST != null) {
            IDENTIFIER obj = ST.lookup(name);
            if (obj != null) return obj;
            ST = ST.parentST;
        }

        return null;
    }

    public SymbolTable getParentST() {
        return parentST;
    }
}
