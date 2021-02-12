package front_end.AST.type;

import front_end.types.PAIR;
import org.antlr.v4.runtime.ParserRuleContext;

public class PairTypeAST extends TypeAST {

  private final TypeAST type1;
  private final TypeAST type2;

  public PairTypeAST(ParserRuleContext ctx, TypeAST type1, TypeAST type2) {
    super(ctx);
    this.type1 = type1;
    this.type2 = type2;
  }

  @Override
  public void check() {
    type1.check();
    type2.check();
    typeObj = new PAIR(type1.typeObj, type2.typeObj);
  }
}
