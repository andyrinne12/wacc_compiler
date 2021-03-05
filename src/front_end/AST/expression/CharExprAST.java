package front_end.AST.expression;

import back_end.FunctionBody;
import back_end.instructions.logical.MOV;
import back_end.operands.immediate.ImmInt;
import back_end.operands.registers.Register;
import front_end.Visitor;
import front_end.types.TYPE;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class CharExprAST extends ExpressionAST {

  private char charVal;

  public CharExprAST(ParserRuleContext ctx, String charVal) {
    super(ctx);
    charVal = charVal.substring(1, charVal.length() - 1);
    this.charVal = unescape(charVal).charAt(0);
  }

  public static String unescape(String str) {
    StringBuilder newStr = new StringBuilder();
    for (int i = 0; i < str.length(); i++) {
      if (i == str.length() - 1) {
        newStr.append(str.charAt(i));
        break;
      }
      switch (str.substring(i, i + 2)) {
        case "\\n":
          newStr.append('\n');
          i++;
          break;
        case "\\t":
          newStr.append('\t');
          i++;
          break;
        case "\\0":
          newStr.append('\0');
          i++;
          break;
        case "\\\\":
          newStr.append('\\');
          i++;
          break;
        case "\\\"":
          newStr.append('\"');
          i++;
          break;
        case "\\'":
          newStr.append('\'');
          i++;
          break;
        default:
          newStr.append(str.charAt(i));
      }
    }
    return newStr.toString();
  }

  @Override
  public void check() {
    identObj = Visitor.ST.lookupAll("char");
    if (identObj == null) {
      error("Undefined type: char");
    }
  }

  @Override
  public TYPE getEvalType() {
    return identObj.getType();
  }

  @Override
  public void assemble(FunctionBody body, List<Register> freeRegs) {
    // charVal has a format of 'c', so we use charVal.charAt(1) to get c.

    body.addInstr(new MOV(freeRegs.get(0), new ImmInt(charVal)));
  }

  public int getCharInt() {
    return charVal;
  }
}
