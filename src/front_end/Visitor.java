package front_end;

import antlr.WACCParser.*;
import antlr.WACCParserBaseVisitor;
import antlr.WACCParserVisitor;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import front_end.AST.*;
import front_end.types.*;

public class Visitor extends WACCParserBaseVisitor<ASTNode> {

  private static SymbolTable Top_ST;
  private static SymbolTable ST;

  public Visitor() {
    Top_ST = new SymbolTable(null);
    ST = Top_ST;

    ST.add("int", new INT());
    ST.add("char", new CHAR());
    ST.add("bool", new BOOLEAN());
    ST.add("string", new STRING());

  }

  @Override
  public ASTNode visitProg(ProgContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitReturnST(ReturnSTContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitWhileST(WhileSTContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitFreeST(FreeSTContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitAsignST(AsignSTContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitIfST(IfSTContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitSkipST(SkipSTContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitReadST(ReadSTContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitBeginST(BeginSTContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitPrintST(PrintSTContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitExitST(ExitSTContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitStatSeqST(StatSeqSTContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitInitST(InitSTContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitPrintlnST(PrintlnSTContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitSignedIntEXP(SignedIntEXPContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitPairLtrEXP(PairLtrEXPContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitArrayElemEXP(ArrayElemEXPContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitBinOpEXP(BinOpEXPContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitStrEXP(StrEXPContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitUnOpEXP(UnOpEXPContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitBracketEXP(BracketEXPContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitBoolEXP(BoolEXPContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitIdentEXP(IdentEXPContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitCharEXP(CharEXPContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitIdentLHS(IdentLHSContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitArrayElemLHS(ArrayElemLHSContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitPairElemLHS(PairElemLHSContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitExpRHS(ExpRHSContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitArrayLtrRHS(ArrayLtrRHSContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitNewPairRHS(NewPairRHSContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitPairElemRHS(PairElemRHSContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitFuncCallRHS(FuncCallRHSContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitFunc(FuncContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitParam(ParamContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitParamList(ParamListContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitArgList(ArgListContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitPrimTypeTP(PrimTypeTPContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitArrayTypeTP(ArrayTypeTPContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitPairTypeTP(PairTypeTPContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitArrayTypeARTP(ArrayTypeARTPContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitPrimTypeARTP(PrimTypeARTPContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitPairTypeARTP(PairTypeARTPContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitArrayElem(ArrayElemContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitArrayLtr(ArrayLtrContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitPairType(PairTypeContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitFirstElemPR(FirstElemPRContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitSecondElemPR(SecondElemPRContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitPrimTypePRTP(PrimTypePRTPContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitArrayTypePRTP(ArrayTypePRTPContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitPairPRTP(PairPRTPContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitUnaryOp(UnaryOpContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitBinaryOp(BinaryOpContext ctx) {
    return null;
  }

  // for semantic errors
  public static void error(ParserRuleContext ctx, String message) {
    System.err.println("line: " + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine()
            + " " + ctx.start.getText() + " " + message);
    System.exit(200);
}
}