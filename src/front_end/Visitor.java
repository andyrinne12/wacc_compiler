package front_end;

import antlr.WACCParser.*;
import antlr.WACCParserBaseVisitor;
import antlr.WACCParserVisitor;

import front_end.AST.StatementAST.Begin;
import front_end.AST.StatementAST.Exit;
import front_end.AST.StatementAST.Free;
import front_end.AST.StatementAST.If;
import front_end.AST.StatementAST.Print;
import front_end.AST.StatementAST.Println;
import front_end.AST.StatementAST.Sequence;
import front_end.AST.StatementAST.Skip;
import front_end.AST.StatementAST.Statement;
import front_end.AST.StatementAST.While;
import front_end.AST.TypeAST.TypeAST;
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
  public ASTNode visitReturnST(ReturnSTContext ctx) {return null;}

  @Override
  public ASTNode visitWhileST(WhileSTContext ctx) {
    Visitor.ST = new SymbolTable(Visitor.ST);

    ExpressionAST expr = visitExpr(ctx.expr());
    Statement stat = (Statement) visit(ctx.stat());

    While whileAST = new While(ctx, expr, stat, ST);
    whileAST.check();
    Visitor.ST = Visitor.ST.getParentST();

    return whileAST;
  }

  @Override
  public Free visitFreeST(FreeSTContext ctx) {
    ExpressionAST expr = visitExpr(ctx.expr());

    Free free = new Free(ctx, expr);
    free.check();

    return free;
  }

  @Override
  public ASTNode visitAssignST(AssignSTContext ctx) {
    return null;
  }

  @Override
  public If visitIfST(IfSTContext ctx) {
    ExpressionAST expr = visitExpr(ctx.expr());
    Visitor.ST = new SymbolTable(Visitor.ST);

    Statement then = (Statement) visit(ctx.stat(0));
    SymbolTable thenST = Visitor.ST;
    Visitor.ST = Visitor.ST.getParentST();

    Visitor.ST = new SymbolTable(Visitor.ST);
    Statement elseSt = (Statement) visit(ctx.stat(1));
    SymbolTable elseST = Visitor.ST;
    Visitor.ST = Visitor.ST.getParentST();

    If ifAST = new If(ctx, expr, then, elseSt, thenST, elseST);
    ifAST.check();

    return ifAST;
  }

  @Override
  public Skip visitSkipST(SkipSTContext ctx) {
    Skip skip = new Skip(ctx);

    return skip;
  }

  @Override
  public ASTNode visitReadST(ReadSTContext ctx) {
    return null;
  }

  @Override
  public Begin visitBeginST(BeginSTContext ctx) {
    ST = new SymbolTable(ST);
    Statement stat = (Statement) visit(ctx.stat());

    Begin begin = new Begin(ctx, stat);
    ST = ST.getParentST();

    return begin;
  }

  @Override
  public Print visitPrintST(PrintSTContext ctx) {
    ExpressionAST expr = visitExpr(ctx.expr());

    Print print = new Print(ctx, expr);
    print.check();

    return print;
  }

  @Override
  public Exit visitExitST(ExitSTContext ctx) {
    ExpressionAST expr = visitExpr(ctx.expr());

    Exit exit = new Exit(ctx, expr);
    exit.check();

    return exit;
  }

  @Override
  public Sequence visitStatSeqST(StatSeqSTContext ctx) {
    List<StatContext> stats = ctx.stat();
    List<Statement> statASTs = new ArrayList<>();

    for (StatContext stat : stats) {
      statASTs.add((Statement) visit(stat));
    }

    Sequence sequence = new Sequence(ctx, statASTs);
    return sequence;
  }

  @Override
  public ASTNode visitInitST(InitSTContext ctx) {
    return null;
  }

  @Override
  public Println visitPrintlnST(PrintlnSTContext ctx) {
    ExpressionAST expr = visitExpr(ctx.expr());

    Println print = new Println(ctx, expr);
    print.check();

    return print;
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