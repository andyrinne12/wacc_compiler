package front_end.AST.statement;

import back_end.FunctionBody;
import back_end.operands.registers.Register;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class SkipAST extends StatementAST {

  public SkipAST(ParserRuleContext ctx) {
    super(ctx);
  }

  @Override
  public void check() {

  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {

  }
}
