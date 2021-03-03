package front_end.AST.function;

import back_end.FunctionBody;
import back_end.operands.registers.Register;
import front_end.AST.ASTNode;
import front_end.AST.type.TypeAST;
import front_end.types.PARAM;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class ParamAST extends ASTNode {

  private TypeAST type;
  private String ident;

  public ParamAST(ParserRuleContext ctx, TypeAST type, String ident) {
    super(ctx);
    this.type = type;
    this.ident = ident;
  }

  @Override
  public void check() {
//        if (type.getTypeObj() instanceof ARRAY) {
//            TYPE arrayElemType = ((ARRAY) type.getTypeObj()).getElemType();
//            ARRAY arrayType = new ARRAY(arrayElemType, 0); // array size doesn't matter in this scenario.
//            identObj = new PARAM(arrayType);
//        }
//        else if (type.getTypeObj() instanceof PAIR) {
//            TYPE firstType = ((PAIR) type.getIdentObj()).getFirstType();
//            TYPE secondType = ((PAIR) type.getIdentObj()).getFirstType();
//            PAIR pairType = new PAIR(firstType, secondType);
//            identObj = new PARAM(pairType);
//        }
//        else {
//            String typeNameInString = type.getTypeObj().toString();
//            IDENTIFIER T = Visitor.ST.lookupAll(typeNameInString);
//            identObj = new PARAM((TYPE) T);
//        }
//
//        if (identObj != null) {
//            Visitor.ST.add(ident, identObj);
//        }
    type.check();
    identObj = new PARAM(type.getTypeObj());
  }

  public String getIdent() {
    return ident;
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {

  }
}
