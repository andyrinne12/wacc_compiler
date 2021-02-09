package semantics;

import antlr.WACCParser.ArgListContext;
import antlr.WACCParser.ArrayElemContext;
import antlr.WACCParser.ArrayElemEXPContext;
import antlr.WACCParser.ArrayElemLHSContext;
import antlr.WACCParser.ArrayLtrContext;
import antlr.WACCParser.ArrayLtrRHSContext;
import antlr.WACCParser.ArrayTypeARTPContext;
import antlr.WACCParser.ArrayTypePRTPContext;
import antlr.WACCParser.ArrayTypeTPContext;
import antlr.WACCParser.AsignSTContext;
import antlr.WACCParser.BeginSTContext;
import antlr.WACCParser.BinOpEXPContext;
import antlr.WACCParser.BinaryOpContext;
import antlr.WACCParser.BoolEXPContext;
import antlr.WACCParser.BracketEXPContext;
import antlr.WACCParser.CharEXPContext;
import antlr.WACCParser.ExitSTContext;
import antlr.WACCParser.ExpRHSContext;
import antlr.WACCParser.FirstElemPRContext;
import antlr.WACCParser.FreeSTContext;
import antlr.WACCParser.FuncCallRHSContext;
import antlr.WACCParser.FuncContext;
import antlr.WACCParser.IdentEXPContext;
import antlr.WACCParser.IdentLHSContext;
import antlr.WACCParser.IfSTContext;
import antlr.WACCParser.InitSTContext;
import antlr.WACCParser.NewPairRHSContext;
import antlr.WACCParser.PairElemLHSContext;
import antlr.WACCParser.PairElemRHSContext;
import antlr.WACCParser.PairLtrEXPContext;
import antlr.WACCParser.PairPRTPContext;
import antlr.WACCParser.PairTypeARTPContext;
import antlr.WACCParser.PairTypeContext;
import antlr.WACCParser.PairTypeTPContext;
import antlr.WACCParser.ParamContext;
import antlr.WACCParser.ParamListContext;
import antlr.WACCParser.PrimTypeARTPContext;
import antlr.WACCParser.PrimTypePRTPContext;
import antlr.WACCParser.PrimTypeTPContext;
import antlr.WACCParser.PrintSTContext;
import antlr.WACCParser.PrintlnSTContext;
import antlr.WACCParser.ProgContext;
import antlr.WACCParser.ReadSTContext;
import antlr.WACCParser.ReturnSTContext;
import antlr.WACCParser.SecondElemPRContext;
import antlr.WACCParser.SignedIntEXPContext;
import antlr.WACCParser.SkipSTContext;
import antlr.WACCParser.StatSeqSTContext;
import antlr.WACCParser.StrEXPContext;
import antlr.WACCParser.UnOpEXPContext;
import antlr.WACCParser.UnaryOpContext;
import antlr.WACCParser.WhileSTContext;
import antlr.WACCParserVisitor;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

public class SemanticChecker<T> extends AbstractParseTreeVisitor<T> implements
    WACCParserVisitor<T> {

  private ProgramState currentState;

  //TODO: reorder visitors and take out the unnecessary ones

  @Override
  public T visitProg(ProgContext ctx) {
    // FUNC TABLE CREATION ???
    currentState = new ProgramState(new FuncTable());
    return null;
  }

  /*TODO: a new program state must be created for each Function(adding the parameters at first)
  and for the Program Main Body */


  @Override
  public T visitReturnST(ReturnSTContext ctx) {
    return null;
  }

  @Override
  public T visitWhileST(WhileSTContext ctx) {
    return null;
  }

  @Override
  public T visitFreeST(FreeSTContext ctx) {
    return null;
  }

  @Override
  public T visitAsignST(AsignSTContext ctx) {
    return null;
  }

  @Override
  public T visitIfST(IfSTContext ctx) {
    return null;
  }

  @Override
  public T visitSkipST(SkipSTContext ctx) {
    return null;
  }

  @Override
  public T visitReadST(ReadSTContext ctx) {
    return null;
  }

  @Override
  public T visitBeginST(BeginSTContext ctx) {
    return null;
  }

  @Override
  public T visitPrintST(PrintSTContext ctx) {
    return null;
  }

  @Override
  public T visitExitST(ExitSTContext ctx) {
    return null;
  }

  @Override
  public T visitStatSeqST(StatSeqSTContext ctx) {
    return null;
  }

  @Override
  public T visitInitST(InitSTContext ctx) {
    return null;
  }

  @Override
  public T visitPrintlnST(PrintlnSTContext ctx) {
    return null;
  }

  @Override
  public T visitSignedIntEXP(SignedIntEXPContext ctx) {
    return null;
  }

  @Override
  public T visitPairLtrEXP(PairLtrEXPContext ctx) {
    return null;
  }

  @Override
  public T visitArrayElemEXP(ArrayElemEXPContext ctx) {
    return null;
  }

  @Override
  public T visitBinOpEXP(BinOpEXPContext ctx) {
    return null;
  }

  @Override
  public T visitStrEXP(StrEXPContext ctx) {
    return null;
  }

  @Override
  public T visitUnOpEXP(UnOpEXPContext ctx) {
    return null;
  }

  @Override
  public T visitBracketEXP(BracketEXPContext ctx) {
    return null;
  }

  @Override
  public T visitBoolEXP(BoolEXPContext ctx) {
    return null;
  }

  @Override
  public T visitIdentEXP(IdentEXPContext ctx) {
    return null;
  }

  @Override
  public T visitCharEXP(CharEXPContext ctx) {
    return null;
  }

  @Override
  public T visitIdentLHS(IdentLHSContext ctx) {
    return null;
  }

  @Override
  public T visitArrayElemLHS(ArrayElemLHSContext ctx) {
    return null;
  }

  @Override
  public T visitPairElemLHS(PairElemLHSContext ctx) {
    return null;
  }

  @Override
  public T visitExpRHS(ExpRHSContext ctx) {
    return null;
  }

  @Override
  public T visitArrayLtrRHS(ArrayLtrRHSContext ctx) {
    return null;
  }

  @Override
  public T visitNewPairRHS(NewPairRHSContext ctx) {
    return null;
  }

  @Override
  public T visitPairElemRHS(PairElemRHSContext ctx) {
    return null;
  }

  @Override
  public T visitFuncCallRHS(FuncCallRHSContext ctx) {
    return null;
  }

  @Override
  public T visitFunc(FuncContext ctx) {
    return null;
  }

  @Override
  public T visitParam(ParamContext ctx) {
    return null;
  }

  @Override
  public T visitParamList(ParamListContext ctx) {
    return null;
  }

  @Override
  public T visitArgList(ArgListContext ctx) {
    return null;
  }

  @Override
  public T visitPrimTypeTP(PrimTypeTPContext ctx) {
    return null;
  }

  @Override
  public T visitArrayTypeTP(ArrayTypeTPContext ctx) {
    return null;
  }

  @Override
  public T visitPairTypeTP(PairTypeTPContext ctx) {
    return null;
  }

  @Override
  public T visitArrayTypeARTP(ArrayTypeARTPContext ctx) {
    return null;
  }

  @Override
  public T visitPrimTypeARTP(PrimTypeARTPContext ctx) {
    return null;
  }

  @Override
  public T visitPairTypeARTP(PairTypeARTPContext ctx) {
    return null;
  }

  @Override
  public T visitArrayElem(ArrayElemContext ctx) {
    return null;
  }

  @Override
  public T visitArrayLtr(ArrayLtrContext ctx) {
    return null;
  }

  @Override
  public T visitPairType(PairTypeContext ctx) {
    return null;
  }

  @Override
  public T visitFirstElemPR(FirstElemPRContext ctx) {
    return null;
  }

  @Override
  public T visitSecondElemPR(SecondElemPRContext ctx) {
    return null;
  }

  @Override
  public T visitPrimTypePRTP(PrimTypePRTPContext ctx) {
    return null;
  }

  @Override
  public T visitArrayTypePRTP(ArrayTypePRTPContext ctx) {
    return null;
  }

  @Override
  public T visitPairPRTP(PairPRTPContext ctx) {
    return null;
  }

  @Override
  public T visitUnaryOp(UnaryOpContext ctx) {
    return null;
  }

  @Override
  public T visitBinaryOp(BinaryOpContext ctx) {
    return null;
  }
}