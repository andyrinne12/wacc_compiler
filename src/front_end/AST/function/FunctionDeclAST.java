package front_end.AST.function;

import front_end.AST.statement.ScopingStatementAST;
import front_end.AST.statement.StatementSequenceAST;
import front_end.AST.type.TypeAST;
import front_end.SymbolTable;
import front_end.Visitor;
import front_end.types.FUNCTION;
import front_end.types.IDENTIFIER;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class FunctionDeclAST extends ScopingStatementAST {

  private final String functionName;
  private final TypeAST returnType;
  private final ParamListAST paramList;
  private final StatementSequenceAST statSeq;

  // Statements in a function are not checked in this object.
  // Rather, they're checked in the visitFunc() method in the Visitor class.

  public FunctionDeclAST(ParserRuleContext ctx, String functionName, TypeAST returnType,
      ParamListAST paramList, StatementSequenceAST statSeq, SymbolTable symbolTable) {
    super(ctx, symbolTable);
    this.functionName = functionName;
    this.returnType = returnType; // no need to check return type, as it has been checked before the FunctionDeclAST object is created.
    this.paramList = paramList; // no need to check the parameters list, as it has been checked before the FunctionDeclAST object is created.
    this.statSeq = statSeq;
  }

  @Override
  public void check() {
    enterScope();

    returnType.check();

    // check function name
    IDENTIFIER funcNameTemp = Visitor.ST.lookupAll(functionName);
    if (funcNameTemp != null) {
      error(functionName + " has already been declared");
    } else {
      if (paramList != null) {
        paramList.check();
        List<ParamAST> paramASTs = paramList.getParamASTs();
        for (ParamAST p : paramASTs) {
          symbolTable.add(p.getIdent(), p.getType());
        }
        identObj = new FUNCTION(returnType.getTypeObj(), paramList.getPARAMs());
      } else {
        identObj = new FUNCTION(returnType.getTypeObj(), new ArrayList<>());
      }
      symbolTable.getParentST()
          .add(functionName, identObj); // add function name to enclosing symbol table.
    }
    exitScope();
  }

  public boolean checkReturn() {
    return statSeq.checkReturn();
  }

  public void checkBody() {
    statSeq.check();
  }
}
