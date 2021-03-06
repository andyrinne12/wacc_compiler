package front_end.AST.function;

import back_end.FunctionBody;
import back_end.operands.registers.Register;
import front_end.AST.ASTNode;
import front_end.types.PARAM;
import front_end.types.TYPE;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class ParamListAST extends ASTNode {

  private final List<ParamAST> parameters;

  public ParamListAST(ParserRuleContext ctx, List<ParamAST> parameters) {
    super(ctx);
    this.parameters = parameters;
  }

  @Override
  public void check() {
    for (ParamAST p : parameters) {
      p.check();
    }
  }

  public List<ParamAST> getParamASTs() {
    return parameters;
  }

  public List<PARAM> getPARAMs() {
    List<PARAM> list = new ArrayList<>();
    for (ParamAST p : parameters) {
      list.add((PARAM) p.getIdentObj());
    }

    return list;
  }

  public List<TYPE> getParamTypes() {
    List<TYPE> paramTypes = new ArrayList<>();
    for (ParamAST p : parameters) {
      paramTypes.add(p.getType());
    }

    return paramTypes;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    for(ParamAST p : parameters) {
      p.assemble(body, freeRegs);
    }
  }
}
