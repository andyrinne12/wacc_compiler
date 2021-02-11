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
  public String toString() {
    StringBuilder str = new StringBuilder(returnType + "(");
    for (int i = 0; i < params.length; i++) {
      str.append(params[i].getType());
      if (i < params.length - 1) {
        str.append(", ");
      }
    }
    str.append(")");
    return str.toString();
  }

  @Override
  public boolean equalsType(TYPE type) {
    return false;
  }
}
