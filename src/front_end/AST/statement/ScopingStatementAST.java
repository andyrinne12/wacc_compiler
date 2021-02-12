package front_end.AST.statement;

import front_end.AST.ASTNode;
import front_end.SymbolTable;
import front_end.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class ScopingStatementAST extends ASTNode {

  protected final SymbolTable symbolTable;

  public ScopingStatementAST(ParserRuleContext ctx,
      SymbolTable symbolTable) {
    super(ctx);
    this.symbolTable = symbolTable;
  }

  @Override
  public abstract void check();

  public void enterScope() {
    Visitor.ST = symbolTable;
  }

  public void exitScope() {
    Visitor.ST = symbolTable.getParentST();
  }

  public SymbolTable getSymbolTable() {
    return symbolTable;
  }
}
