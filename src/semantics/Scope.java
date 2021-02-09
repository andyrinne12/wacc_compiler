package semantics;

import java.util.HashMap;
import java.util.Map;
import semantics.types.Type;

class Scope {

  private final Map<String, Type> variables = new HashMap<>();

  public void define(String ident, Type type) {
    variables.put(ident, type);
  }

  public boolean isDefined(String ident) {
    return variables.containsKey(ident);
  }

  public Type getType(String ident) {
    return variables.get(ident);
  }

}
