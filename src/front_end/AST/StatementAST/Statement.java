package front_end.AST.StatementAST;

import front_end.AST.ASTNode;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class Statement extends ASTNode {

  public Statement(ParserRuleContext ctx) {
    super(ctx);
  }

  public boolean determineInvariance(List<String> identifiers) {
    return false;
  }

  public void findInvariants() {}

  public void findInvariants(List<String> identifiers) {}

}
