package front_end.AST;

import front_end.AST.function.FunctionDeclAST;
import front_end.AST.statement.StatementSequenceAST;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class ProgramAST extends ASTNode {

  private final List<FunctionDeclAST> functions;
  private final StatementSequenceAST body;

  public ProgramAST(ParserRuleContext ctx,
      List<FunctionDeclAST> functions, StatementSequenceAST body) {
    super(ctx);
    this.functions = functions;
    this.body = body;
  }

  @Override
  public void check() {
    for (FunctionDeclAST functionDeclAST : functions) {
      functionDeclAST.check();
    }
    for (FunctionDeclAST functionDeclAST : functions) {
      functionDeclAST.checkBody();
    }
    body.check();
  }
}
