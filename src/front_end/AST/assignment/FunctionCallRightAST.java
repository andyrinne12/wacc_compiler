package front_end.AST.assignment;

import front_end.AST.expression.ExpressionAST;
import front_end.Visitor;
import front_end.types.FUNCTION;
import front_end.types.PARAM;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class FunctionCallRightAST extends AssignmentRightAST {

  private final String ident;
  private final List<ExpressionAST> argList;

  public FunctionCallRightAST(ParserRuleContext ctx, String ident,
      List<ExpressionAST> argList) {
    super(ctx);
    this.ident = ident;
    this.argList = argList;
  }

  @Override
  public void check() {
    identObj = Visitor.ST.lookupAll(ident);
    if (identObj == null || !(identObj instanceof FUNCTION)) {
      error("Function " + ident + " is not defined.");
    } else {
      FUNCTION func = (FUNCTION) identObj;
      PARAM[] params = func.getParams();
      if (argList.size() != params.length) {
        error("Invalid number of arguments on function call");
      }
      for (int i = 0; i < argList.size(); i++) {
        ExpressionAST arg = argList.get(i);
        arg.check();
        if (!(arg.getIdentObj().getType().equalsType(params[i].getType()))) {
          error("Invalid argument type for arg " + i);
        }
      }
    }
  }


  @Override
  public TYPE getEvalType() {
    FUNCTION func = (FUNCTION) identObj;
    return func.getReturnType();
  }
}