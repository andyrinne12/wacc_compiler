package semantics;

import java.util.List;
import semantics.types.Type;

class Function {

  private final String ident;
  private final Type returnType;
  private final List<Type> argTypes;

  public Function(String ident, Type returnType, List<Type> argTypes) {
    this.ident = ident;
    this.returnType = returnType;
    this.argTypes = argTypes;
  }

  public String getIdent() {
    return ident;
  }

  public Type getReturnType() {
    return returnType;
  }

  public List<Type> getArgTypes() {
    return argTypes;
  }
}
