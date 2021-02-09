package semantics;

import java.util.ArrayList;
import java.util.List;
import semantics.types.Type;

public class ProgramState {

  private final FuncTable funcTable;
  private final List<Scope> scopeStack = new ArrayList<>();

  public ProgramState(FuncTable funcTable) {
    this.funcTable = funcTable;
    this.scopeStack.add(new Scope());
  }

  public boolean varDefined(String ident) {
    return scopeStack.stream()
        .anyMatch(scope -> scope.isDefined(ident));
  }

  public boolean funcDefined(String ident) {
    return funcTable.isDefined(ident);
  }

  public boolean identDefined(String ident) {
    return varDefined(ident) || funcDefined(ident);
  }

  public void defineFunc(String ident, Function func) {
    funcTable.define(ident, func);
  }

  public void defineVar(String ident, Type type) {
    scopeStack.get(scopeStack.size() - 1).define(ident, type);
  }

  public Function getFunction(String ident) {
    return funcTable.getFunction(ident);
  }

  public Type getVarType(String ident) {
    for (int i = scopeStack.size() - 1; i > 0; i--) {
      if (scopeStack.get(i).isDefined(ident)) {
        return scopeStack.get(i).getType(ident);
      }
    }
    return null;
  }

  public void innerScope() {
    scopeStack.add(new Scope());
  }

  public void outerScope() {
    scopeStack.remove(scopeStack.size() - 1);
  }
}
