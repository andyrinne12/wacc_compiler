package front_end;

import antlr.WACCParserBaseVisitor;
import front_end.AST.ASTNode;
import front_end.types.BOOLEAN;
import front_end.types.CHAR;
import front_end.types.INT;
import front_end.types.STRING;
import org.antlr.v4.runtime.ParserRuleContext;

public class Visitor extends WACCParserBaseVisitor<ASTNode> {

  public static SymbolTable Top_ST;
  public static SymbolTable ST;

  public Visitor() {
    Top_ST = new SymbolTable(null);

    ST.add("int", new INT());
    ST.add("char", new CHAR());
    ST.add("bool", new BOOLEAN());
    ST.add("string", new STRING());

    ST = new SymbolTable(Top_ST);
  }

  // for semantic errors
  public static void error(ParserRuleContext ctx, String message) {
    System.err.println("line: " + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine()
        + " " + ctx.start.getText() + " " + message);
    System.exit(200);
  }
}