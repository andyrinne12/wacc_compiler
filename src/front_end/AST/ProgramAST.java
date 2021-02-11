package front_end.AST;

import front_end.AST.function.FunctionDeclAST;
import front_end.AST.statement.Statement;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class ProgramAST extends  ASTNode {
  private List<FunctionDeclAST> functions;
  private Statement stat;

  public ProgramAST(ParserRuleContext ctx,
      List<FunctionDeclAST> functions, Statement stat) {
    super(ctx);
    this.functions = functions;
    this.stat = stat;
  }

  @Override
  public void check() {

  }
}
