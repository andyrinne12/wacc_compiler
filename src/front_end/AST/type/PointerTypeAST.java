package front_end.AST.type;

import front_end.types.POINTER;
import org.antlr.v4.runtime.ParserRuleContext;

public class PointerTypeAST extends TypeAST {

  private final TypeAST objectType;

  public PointerTypeAST(ParserRuleContext ctx, TypeAST objectType) {
    super(ctx);
    this.objectType = objectType;
  }

  @Override
  public void check() {
    objectType.check();
    typeObj = new POINTER(objectType.getTypeObj());
  }
}
