package front_end.types;

import java.util.List;

import front_end.SymbolTable;

public class FUNCTION extends IDENTIFIER{

  private final TYPE returnType;
  private final List<PARAM> params;
  private final SymbolTable ST;

  public FUNCTION(TYPE returnType, List<PARAM> params, SymbolTable ST) {
    this.returnType = returnType;
    this.params = params;
    this.ST = ST;
  }

  // as a function is not a type, we return null
  public TYPE getType() {
    return null;
  }

  public List<PARAM> getParams() {
    return params;
  }

  public TYPE getReturnType() {
    return returnType;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder(returnType + "(");
    for (int i = 0; i < params.size(); i++) {
      str.append(params.get(i).getType());
      if (i < params.size() - 1) {
        str.append(", ");
      }
    }
    str.append(")");
    return str.toString();
  }

  // @Override
  // public boolean equalsType(TYPE type) {
  //   return false;
  // }
}
