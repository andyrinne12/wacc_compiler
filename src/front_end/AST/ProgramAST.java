package front_end.AST;

import back_end.CodeGen;
import back_end.FunctionBody;
import back_end.Utils;
import back_end.operands.registers.Register;
import front_end.AST.function.FunctionDeclAST;
import front_end.AST.statement.StatementSequenceAST;
import front_end.Main;
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
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    for (FunctionDeclAST function : functions) {
      function.assemble(null, freeRegs);
    }

    FunctionBody main = new FunctionBody("main", true, false, true);
    this.body.assemble(main, freeRegs);
    main.endBody();
    CodeGen.funcBodies.add(main);

    Utils.printFunctions();
  }

  @Override
  public void check() {
    for (FunctionDeclAST functionDeclAST : functions) {
      if (!functionDeclAST.checkReturn()) {
        error("Function must end with a return or an exit statement");
        Main.EXIT_CODE = 100;
      }
    }
    if (Main.EXIT_CODE != 0) {
      System.exit(Main.EXIT_CODE);
    }
    for (FunctionDeclAST functionDeclAST : functions) {
      functionDeclAST.check();
    }
    for (FunctionDeclAST functionDeclAST : functions) {
      functionDeclAST.checkBody();
    }
    body.check();
  }
}
