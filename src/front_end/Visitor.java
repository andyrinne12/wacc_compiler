package front_end;

import antlr.WACCParser.*;
import antlr.WACCParserBaseVisitor;
import antlr.WACCParserVisitor;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import front_end.AST.*;
import front_end.AST.ExpressionAST.*;
import front_end.AST.FunctionDeclaration.*;
import front_end.AST.TypeAST.PrimTypeAST;
import front_end.types.*;

public class Visitor extends WACCParserBaseVisitor<ASTNode> {

  public static SymbolTable Top_ST;
  public static SymbolTable ST;

  public Visitor() {
    Top_ST = new SymbolTable(null);

    ST.add("int", new INT());
    ST.add("char", new CHAR());
    ST.add("bool", new BOOLEAN());
    ST.add("string", new STRING());

    SymbolTable nextST = new SymbolTable(Top_ST);
    ST = nextST;
  }

  @Override
  public ASTNode visitProg(ProgContext ctx) {
    // List<FuncContext> functions = ctx.func();
    // List<FunctionDeclAST> functionDecASTs = new ArrayList<>();

    // for (FuncContext f: functions) {
    //   FunctionDeclAST functionDeclAST = visitFunc(f);
    // }
    
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
  public ASTNode visitAssignST(AssignSTContext ctx) {
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

  public ExpressionAST visitExpr(ExprContext ctx) {
    ExpressionAST exprAST = null;

    if (ctx instanceof SignedIntEXPContext) {
      exprAST = visitSignedIntEXP((SignedIntEXPContext) ctx);
    }
    else if (ctx instanceof PairLtrEXPContext) {
      exprAST = visitPairLtrEXP((PairLtrEXPContext) ctx);
    }
    else if (ctx instanceof ArrayElemEXPContext) {
      exprAST = visitArrayElemEXP((ArrayElemEXPContext) ctx);
    }
    else if (ctx instanceof BinOpEXPContext) {
      // to-do
    }
    else if (ctx instanceof StrEXPContext) {
      exprAST = visitStrEXP((StrEXPContext) ctx);
    }
    else if (ctx instanceof UnOpEXPContext) {
      // to-do
    }
    else if (ctx instanceof BracketEXPContext) {
      exprAST = visitBracketEXP((BracketEXPContext) ctx);
    }
    else if (ctx instanceof BoolEXPContext) {
      exprAST = visitBoolEXP((BoolEXPContext) ctx);
    }
    else if (ctx instanceof IdentEXPContext) {
      exprAST = visitIdentEXP((IdentEXPContext) ctx);
    }
    else if (ctx instanceof CharEXPContext) {
      exprAST = visitCharEXP((CharEXPContext) ctx);
    }

    return exprAST;
  }

  @Override
  public SignedIntExprAST visitSignedIntEXP(SignedIntEXPContext ctx) {
    String intSign;
    if (ctx.MINUS() != null) {
      intSign = "-";
    }
    else {
      intSign = "+";
    }

    SignedIntExprAST signedInt = new SignedIntExprAST(ctx, intSign, ctx.UINT_LTR().toString());
    signedInt.check();
    return signedInt;
  }

  @Override
  public PairLtrExprAST visitPairLtrEXP(PairLtrEXPContext ctx) {
    return new PairLtrExprAST(ctx, ctx.getText());
  }

  @Override
  public ArrayElemExprAST visitArrayElemEXP(ArrayElemEXPContext ctx) {
    List<ExprContext> exprContexts = ctx.arrayElem().expr();
    List<ExpressionAST> exprASTs = new ArrayList<>();

    for (ExprContext e: exprContexts) {
      exprASTs.add(visitExpr(e));
    }

    ArrayElemExprAST arrElemExprAST = new ArrayElemExprAST(ctx, ctx.getText(), exprASTs);
    arrElemExprAST.check();
    return arrElemExprAST;
  }

  @Override
  public ASTNode visitBinOpEXP(BinOpEXPContext ctx) {
    return null;
  }

  @Override
  public StringExprAST visitStrEXP(StrEXPContext ctx) {
    StringExprAST strExpr = new StringExprAST(ctx, ctx.getText());
    strExpr.check();
    return strExpr;
  }

  @Override
  public UnaryOpExprAST visitUnOpEXP(UnOpEXPContext ctx) {
    UnaryOpExprAST unaryOpExpr = new UnaryOpExprAST(ctx, visitExpr(ctx.expr()), ctx.unaryOp().getText());
    unaryOpExpr.check();
    return unaryOpExpr;
  }

  @Override
  public ExpressionAST visitBracketEXP(BracketEXPContext ctx) {
    return visitExpr(ctx);
  }

  @Override
  public BoolExprAST visitBoolEXP(BoolEXPContext ctx) {
    BoolExprAST boolExpr = new BoolExprAST(ctx, ctx.getText());
    boolExpr.check();
    return boolExpr;
  }

  @Override
  public IdentAST visitIdentEXP(IdentEXPContext ctx) {
    IdentAST identExpr = new IdentAST(ctx, ctx.getText());
    identExpr.check();
    return identExpr;
  }

  @Override
  public CharExprAST visitCharEXP(CharEXPContext ctx) {
    CharExprAST charExpr = new CharExprAST(ctx, ctx.getText());
    charExpr.check();
    return charExpr;
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
  public FunctionDeclAST visitFunc(FuncContext ctx) {
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
  public PrimTypeAST visitPrimTypeTP(PrimTypeTPContext ctx) {
    PrimTypeAST primType = new PrimTypeAST(ctx, ctx.getText());
    primType.check();
    return primType;
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