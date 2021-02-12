package front_end.AST.type;

import front_end.AST.ASTNode;
import front_end.types.TYPE;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class TypeAST extends ASTNode {

  protected TYPE typeObj;

  public TypeAST(ParserRuleContext ctx) {
    super(ctx);
  }

  public TYPE getTypeObj() {
    return typeObj;
  }
}
