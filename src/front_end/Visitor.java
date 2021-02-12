package front_end;

import antlr.WACCParser.ArgListContext;
import antlr.WACCParser.ArrayElemContext;
import antlr.WACCParser.ArrayElemEXPContext;
import antlr.WACCParser.ArrayElemLHSContext;
import antlr.WACCParser.ArrayLtrRHSContext;
import antlr.WACCParser.ArrayTypeTPContext;
import antlr.WACCParser.AssignSTContext;
import antlr.WACCParser.BeginSTContext;
import antlr.WACCParser.BinOpEXPContext;
import antlr.WACCParser.BoolEXPContext;
import antlr.WACCParser.BracketEXPContext;
import antlr.WACCParser.CharEXPContext;
import antlr.WACCParser.ExitSTContext;
import antlr.WACCParser.ExpRHSContext;
import antlr.WACCParser.ExprContext;
import antlr.WACCParser.FreeSTContext;
import antlr.WACCParser.FuncCallRHSContext;
import antlr.WACCParser.FuncContext;
import antlr.WACCParser.IdentEXPContext;
import antlr.WACCParser.IdentLHSContext;
import antlr.WACCParser.IfSTContext;
import antlr.WACCParser.InitSTContext;
import antlr.WACCParser.NewPairRHSContext;
import antlr.WACCParser.PairElemContext;
import antlr.WACCParser.PairElemLHSContext;
import antlr.WACCParser.PairElemRHSContext;
import antlr.WACCParser.PairLtrEXPContext;
import antlr.WACCParser.PairTypeTPContext;
import antlr.WACCParser.ParamContext;
import antlr.WACCParser.ParamListContext;
import antlr.WACCParser.PrimTypeTPContext;
import antlr.WACCParser.PrintSTContext;
import antlr.WACCParser.PrintlnSTContext;
import antlr.WACCParser.ProgContext;
import antlr.WACCParser.ReadSTContext;
import antlr.WACCParser.ReturnSTContext;
import antlr.WACCParser.SignedIntEXPContext;
import antlr.WACCParser.SkipSTContext;
import antlr.WACCParser.StatContext;
import antlr.WACCParser.StatSeqSTContext;
import antlr.WACCParser.StrEXPContext;
import antlr.WACCParser.TypeContext;
import antlr.WACCParser.UnOpEXPContext;
import antlr.WACCParser.WhileSTContext;
import antlr.WACCParserBaseVisitor;
import antlr.WACCParserVisitor;
import front_end.AST.ASTNode;
import front_end.AST.ProgramAST;
import front_end.AST.assignment.ArrayElemAST;
import front_end.AST.assignment.ArrayLtrRightAST;
import front_end.AST.assignment.AssignmentAST;
import front_end.AST.assignment.AssignmentLeftAST;
import front_end.AST.assignment.AssignmentRightAST;
import front_end.AST.assignment.ExprRightAST;
import front_end.AST.assignment.FunctionCallRightAST;
import front_end.AST.assignment.IdentLeftAST;
import front_end.AST.assignment.InitializationAST;
import front_end.AST.assignment.NewPairRightAST;
import front_end.AST.assignment.PairElemAST;
import front_end.AST.expression.ArrayElemExprAST;
import front_end.AST.expression.BinaryOpExprAST;
import front_end.AST.expression.BoolExprAST;
import front_end.AST.expression.CharExprAST;
import front_end.AST.expression.ExpressionAST;
import front_end.AST.expression.IdentAST;
import front_end.AST.expression.PairLtrExprAST;
import front_end.AST.expression.SignedIntExprAST;
import front_end.AST.expression.StringExprAST;
import front_end.AST.expression.UnaryOpExprAST;
import front_end.AST.function.FunctionDeclAST;
import front_end.AST.function.ParamAST;
import front_end.AST.function.ParamListAST;
import front_end.AST.statement.Begin;
import front_end.AST.statement.Exit;
import front_end.AST.statement.Free;
import front_end.AST.statement.If;
import front_end.AST.statement.Print;
import front_end.AST.statement.Println;
import front_end.AST.statement.Read;
import front_end.AST.statement.Return;
import front_end.AST.statement.Sequence;
import front_end.AST.statement.Skip;
import front_end.AST.statement.Statement;
import front_end.AST.statement.While;
import front_end.AST.type.ArrayTypeAST;
import front_end.AST.type.PairTypeAST;
import front_end.AST.type.PrimTypeAST;
import front_end.AST.type.TypeAST;
import front_end.types.BOOLEAN;
import front_end.types.CHAR;
import front_end.types.INT;
import front_end.types.STRING;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class Visitor extends WACCParserBaseVisitor<ASTNode> implements WACCParserVisitor<ASTNode> {

  public static SymbolTable Top_ST;
  public static SymbolTable ST;

  public Visitor() {
    Top_ST = new SymbolTable(null);

    Top_ST.add("int", new INT());
    Top_ST.add("char", new CHAR());
    Top_ST.add("bool", new BOOLEAN());
    Top_ST.add("string", new STRING());

    ST = new SymbolTable(Top_ST);
  }

  // for semantic errors
  public static void error(ParserRuleContext ctx, String message) {
    //  System.err.println("line: " + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine()
    //      + " " + ctx.start.getText() + " " + message);
    System.out.println(message);
    System.exit(200);
  }

  @Override
  public ProgramAST visitProg(ProgContext ctx) {
    List<FuncContext> functions = ctx.func();
    List<FunctionDeclAST> functionDecASTs = new ArrayList<>();

    for (FuncContext func : functions) {
      FunctionDeclAST f = visitFunc(func);
      functionDecASTs.add(f);
    }

    Statement stat = (Statement) visit(ctx.stat());
    return new ProgramAST(ctx, functionDecASTs, stat);
  }

  @Override
  public Return visitReturnST(ReturnSTContext ctx) {
    FuncContext funcContext = getEnclosingFunctionContext(ctx);
    TypeAST returnType = (TypeAST) visit(funcContext.type());
    ExpressionAST expression = visitExpr(ctx.expr());

    Return returnAST = new Return(ctx, expression, returnType);
    returnAST.check();

    return returnAST;
  }

  private FuncContext getEnclosingFunctionContext(ParserRuleContext ctx) {
    ParserRuleContext parentContext = ctx.getParent();

    while (!(parentContext instanceof FuncContext)) {
      parentContext = parentContext.getParent();

      if (parentContext instanceof ProgContext) {
        error(ctx, "must be in a function");
      }
    }

    return (FuncContext) parentContext;
  }

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
    AssignmentAST assignment = new AssignmentAST(ctx, (AssignmentLeftAST) visit(ctx.lhs()),
        (AssignmentRightAST) visit(ctx.rhs()));
    assignment.check();
    return assignment;
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
    return new Skip(ctx);
  }

  @Override
  public Read visitReadST(ReadSTContext ctx) {
    AssignmentLeftAST lhs = (AssignmentLeftAST) visit(ctx.lhs());

    Read read = new Read(ctx, lhs);
    read.check();

    return read;
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

    return new Sequence(ctx, statASTs);
  }

  @Override
  public ASTNode visitInitST(InitSTContext ctx) {
    TypeAST type = (TypeAST) visit(ctx.type());
    IdentLeftAST ident = new IdentLeftAST(ctx, ctx.IDENT().getText());
    AssignmentRightAST rhs = (AssignmentRightAST) visit(ctx.rhs());
    InitializationAST initialization = new InitializationAST(ctx, type, ident, rhs);
    initialization.check();
    return initialization;
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
    } else if (ctx instanceof PairLtrEXPContext) {
      exprAST = visitPairLtrEXP((PairLtrEXPContext) ctx);
    } else if (ctx instanceof ArrayElemEXPContext) {
      exprAST = visitArrayElemEXP((ArrayElemEXPContext) ctx);
    } else if (ctx instanceof BinOpEXPContext) {
      exprAST = visitBinOpEXP((BinOpEXPContext) ctx);
    } else if (ctx instanceof StrEXPContext) {
      exprAST = visitStrEXP((StrEXPContext) ctx);
    } else if (ctx instanceof UnOpEXPContext) {
      exprAST = visitUnOpEXP((UnOpEXPContext) ctx);
    } else if (ctx instanceof BracketEXPContext) {
      exprAST = visitBracketEXP((BracketEXPContext) ctx);
    } else if (ctx instanceof BoolEXPContext) {
      exprAST = visitBoolEXP((BoolEXPContext) ctx);
    } else if (ctx instanceof IdentEXPContext) {
      exprAST = visitIdentEXP((IdentEXPContext) ctx);
    } else if (ctx instanceof CharEXPContext) {
      exprAST = visitCharEXP((CharEXPContext) ctx);
    }

    return exprAST;
  }

  @Override
  public SignedIntExprAST visitSignedIntEXP(SignedIntEXPContext ctx) {
    String intSign;
    if (ctx.MINUS() != null) {
      intSign = "-";
    } else {
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

    for (ExprContext e : exprContexts) {
      exprASTs.add(visitExpr(e));
    }

    ArrayElemExprAST arrElemExprAST = new ArrayElemExprAST(ctx, ctx.getText(), exprASTs);
    arrElemExprAST.check();
    return arrElemExprAST;
  }

  @Override
  public BinaryOpExprAST visitBinOpEXP(BinOpEXPContext ctx) {
    BinaryOpExprAST binaryOpExpr = new BinaryOpExprAST(ctx, visitExpr(ctx.expr(0)),
        visitExpr(ctx.expr(1)), ctx.binaryOp().getText());
    binaryOpExpr.check();
    return binaryOpExpr;
  }

  @Override
  public StringExprAST visitStrEXP(StrEXPContext ctx) {
    StringExprAST strExpr = new StringExprAST(ctx, ctx.getText());
    strExpr.check();
    return strExpr;
  }

  @Override
  public UnaryOpExprAST visitUnOpEXP(UnOpEXPContext ctx) {
    UnaryOpExprAST unaryOpExpr = new UnaryOpExprAST(ctx, visitExpr(ctx.expr()),
        ctx.unaryOp().getText());
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
  public IdentLeftAST visitIdentLHS(IdentLHSContext ctx) {
    return new IdentLeftAST(ctx, ctx.IDENT().getText());
  }

  @Override
  public ASTNode visitArrayElemLHS(ArrayElemLHSContext ctx) {
    return visitArrayElem(ctx.arrayElem());
  }

  @Override
  public ASTNode visitPairElemLHS(PairElemLHSContext ctx) {
    return visitPairElem(ctx.pairElem());
  }

  @Override
  public ASTNode visitExpRHS(ExpRHSContext ctx) {
    return new ExprRightAST(ctx, visitExpr(ctx.expr()));
  }

  @Override
  public ASTNode visitArrayLtrRHS(ArrayLtrRHSContext ctx) {
    List<ExpressionAST> array = new ArrayList<>();
    for (ExprContext expr : ctx.expr()) {
      array.add(visitExpr(expr));
    }
    return new ArrayLtrRightAST(ctx, array);
  }

  @Override
  public ASTNode visitNewPairRHS(NewPairRHSContext ctx) {
    ExprRightAST expr1 = new ExprRightAST(ctx, visitExpr(ctx.expr(0)));
    ExprRightAST expr2 = new ExprRightAST(ctx, visitExpr(ctx.expr(1)));
    return new NewPairRightAST(ctx, expr1, expr2);
  }

  @Override
  public ASTNode visitPairElemRHS(PairElemRHSContext ctx) {
    return visitPairElem(ctx.pairElem());
  }

  @Override
  public ASTNode visitArrayElem(ArrayElemContext ctx) {
    List<ExpressionAST> indices = new ArrayList<>();
    for (ExprContext expr : ctx.expr()) {
      indices.add(visitExpr(expr));
    }
    return new ArrayElemAST(ctx, indices);
  }

  @Override
  public ASTNode visitPairElem(PairElemContext ctx) {
    return new PairElemAST(ctx, visitExpr(ctx.expr()));
  }

  @Override
  public ASTNode visitFuncCallRHS(FuncCallRHSContext ctx) {
    List<ExpressionAST> argList = new ArrayList<>();
    for (ExprContext argExpr : ctx.argList().expr()) {
      argList.add(visitExpr(argExpr));
    }
    return new FunctionCallRightAST(ctx, ctx.IDENT().getText(), argList);
  }

  @Override
  public FunctionDeclAST visitFunc(FuncContext ctx) {
    Visitor.ST = new SymbolTable(Visitor.ST);

    // create FunctionDeclAST obj.
    TypeAST returnType = (TypeAST) visit(ctx.type());
    ParamListAST paramList = visitParamList(ctx.paramList());
    FunctionDeclAST function = new FunctionDeclAST(ctx, ctx.IDENT().getText(), returnType, paramList);
    function.check();

    // visit statements inside the function.
    Statement statement = (Statement) visit(ctx.stat());
    function.setStatement(statement);

    Visitor.ST = Visitor.ST.getParentST();

    return function;
  }

  @Override
  public ParamAST visitParam(ParamContext ctx) {
    ParamAST param = new ParamAST(ctx, (TypeAST) visit(ctx.type()), ctx.IDENT().getText());
    param.check();
    return param;
  }

  @Override
  public ParamListAST visitParamList(ParamListContext ctx) {
    if (ctx.param().isEmpty()) {
      return null;
    }
    else {
      List<ParamContext> parameters = ctx.param();
      List<ParamAST> paramASTs = new ArrayList<>();

      for (ParamContext p: parameters) {
        paramASTs.add(visitParam(p));
      }

      ParamListAST paramList = new ParamListAST(ctx, paramASTs);
      paramList.check();
      return paramList;
    }
  }

  @Override
  public ASTNode visitArgList(ArgListContext ctx) {
    return null;
  }

  // public TypeAST visitType(TypeContext ctx) {
  //   // TO-DO
  //   return null;
  // }

  @Override
  public PrimTypeAST visitPrimTypeTP(PrimTypeTPContext ctx) {
    PrimTypeAST primType = new PrimTypeAST(ctx, ctx.getText());
    primType.check();
    return primType;
  }

  @Override
  public ASTNode visitArrayTypeTP(ArrayTypeTPContext ctx) {
    TypeAST elemType = (TypeAST) visit(ctx.type());
    TypeAST arrayType = elemType;
    for (int i = 0; i < ctx.LBR().size(); i++) {
      arrayType = new ArrayTypeAST(ctx, elemType);
      elemType = arrayType;
    }
    arrayType.check();
    return arrayType;
  }

  @Override
  public ASTNode visitPairTypeTP(PairTypeTPContext ctx) {
    TypeAST type1 = (TypeAST) visit(ctx.type(0));
    TypeAST type2 = (TypeAST) visit(ctx.type(1));
    TypeAST pairType = new PairTypeAST(ctx, type1, type2);
    pairType.check();
    return pairType;
  }
}