package semantics;

import java.util.HashMap;
import java.util.Map;

class FuncTable {

  private final Map<String, Function> functions = new HashMap<>();

  public void define(String ident, Function function) {
    functions.put(ident, function);
  }

  public boolean isDefined(String ident) {
    return functions.containsKey(ident);
  }

  public Function getFunction(String ident) {
    return functions.get(ident);
  }

}
