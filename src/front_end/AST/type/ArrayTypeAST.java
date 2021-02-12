package front_end.AST.type;

import front_end.types.ARRAY;
import org.antlr.v4.runtime.ParserRuleContext;

public class ArrayTypeAST extends TypeAST {

  private final TypeAST elemType;

  public ArrayTypeAST(ParserRuleContext ctx, TypeAST elemType) {
    super(ctx);
    this.elemType = elemType;
  }

  @Override
  public void check() {
    elemType.wasChecked();
    typeObj = new ARRAY(elemType.typeObj, 0);
  }
}
