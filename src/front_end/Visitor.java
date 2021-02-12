package front_end;

import antlr.WACCParser.ArgListContext;
import antlr.WACCParser.ArrayElemContext;
import antlr.WACCParser.ArrayElemEXPContext;
import antlr.WACCParser.ArrayElemLHSContext;
import antlr.WACCParser.ArrayLtrRHSContext;
import antlr.WACCParser.ArrayTypePETContext;
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
import antlr.WACCParser.PairArrayATContext;
import antlr.WACCParser.PairElemContext;
import antlr.WACCParser.PairElemLHSContext;
import antlr.WACCParser.PairElemRHSContext;
import antlr.WACCParser.PairLtrEXPContext;
import antlr.WACCParser.PairTypeContext;
import antlr.WACCParser.PairTypePETContext;
import antlr.WACCParser.PairTypeTPContext;
import antlr.WACCParser.ParamContext;
import antlr.WACCParser.ParamListContext;
import antlr.WACCParser.PrimArrayATContext;
import antlr.WACCParser.PrimTypePETContext;
import antlr.WACCParser.PrimTypeTPContext;
import antlr.WACCParser.PrintSTContext;
import antlr.WACCParser.PrintlnSTContext;
import antlr.WACCParser.ProgContext;
import antlr.WACCParser.ReadSTContext;
import antlr.WACCParser.ReturnSTContext;
import antlr.WACCParser.SignedIntEXPContext;
import antlr.WACCParser.SkipSTContext;
import antlr.WACCParser.StatContext;
import antlr.WACCParser.StatSeqContext;
import antlr.WACCParser.StrEXPContext;
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
import front_end.AST.assignment.PairElemLeftAST;
import front_end.AST.assignment.PairElemRightAST;
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
import front_end.AST.statement.Skip;
import front_end.AST.statement.Statement;
import front_end.AST.statement.StatementSequenceAST;
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
    System.err.println("line: " + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine()
        + " " + ctx.start.getText() + " " + message);
    Main.EXIT_CODE = 200;
  }

  @Override
  public ProgramAST visitProg(ProgContext ctx) {
    List<FuncContext> functions = ctx.func();
    List<FunctionDeclAST> functionDecASTs = new ArrayList<>();

    for (FuncContext func : functions) {
      FunctionDeclAST f = visitFunc(func);
      functionDecASTs.add(f);
    }

    StatementSequenceAST body = (StatementSequenceAST) visitStatSeq(ctx.statSeq());

    return new ProgramAST(ctx, functionDecASTs, body);
  }

  @Override
  public FunctionDeclAST visitFunc(FuncContext ctx) {
    SymbolTable symbolTable = new SymbolTable(Visitor.ST);
    Visitor.ST = symbolTable;

    TypeAST returnType = (TypeAST) visit(ctx.type());
    ParamListAST paramList = visitParamList(ctx.paramList());
    StatementSequenceAST statSeq = (StatementSequenceAST) visitStatSeq(ctx.statSeq());

    Visitor.ST = symbolTable.getParentST();

    return new FunctionDeclAST(ctx, ctx.IDENT().getText(), returnType,
        paramList, statSeq, symbolTable);
  }

  @Override
  public ParamAST visitParam(ParamContext ctx) {
    return new ParamAST(ctx, (TypeAST) visit(ctx.type()), ctx.IDENT().getText());
  }

  @Override
  public ParamListAST visitParamList(ParamListContext ctx) {
    List<ParamAST> paramASTs = new ArrayList<>();
    if(ctx != null && ctx.param() != null)
    for (ParamContext p : ctx.param()) {
      paramASTs.add(visitParam(p));
    }
    return new ParamListAST(ctx, paramASTs);
  }

  @Override
  public ASTNode visitStatSeq(StatSeqContext ctx) {
    SymbolTable symbolTable = new SymbolTable(Visitor.ST);
    Visitor.ST = symbolTable;

    List<Statement> statSeq = new ArrayList<>();
    for (StatContext statCtx : ctx.stat()) {
      statSeq.add((Statement) visit(statCtx));
    }

    Visitor.ST = symbolTable.getParentST();

    return new StatementSequenceAST(ctx, symbolTable, statSeq);
  }

  @Override
  public Return visitReturnST(ReturnSTContext ctx) {
    ParserRuleContext tmpContext = getEnclosingFunctionContext(ctx);
    if (tmpContext instanceof ProgContext) {
      return null;
    }
    FuncContext funcContext = (FuncContext) tmpContext;
    TypeAST returnType = (TypeAST) visit(funcContext.type());
    ExpressionAST expression = visitExpr(ctx.expr());

    return new Return(ctx, expression, returnType);
  }

  private ParserRuleContext getEnclosingFunctionContext(ParserRuleContext ctx) {
    ParserRuleContext parentContext = ctx.getParent();

    while (!(parentContext instanceof FuncContext)) {
      parentContext = parentContext.getParent();

      if (parentContext instanceof ProgContext) {
        error(ctx, "The return statement must be in a function");
        break;
      }
    }

    return parentContext;
  }

  @Override
  public ASTNode visitWhileST(WhileSTContext ctx) {
    ExpressionAST expr = visitExpr(ctx.expr());
    StatementSequenceAST statSeq = (StatementSequenceAST) visitStatSeq(ctx.statSeq());
    return new While(ctx, expr, statSeq);
  }

  @Override
  public Free visitFreeST(FreeSTContext ctx) {
    ExpressionAST expr = visitExpr(ctx.expr());
    return new Free(ctx, expr);
  }

  @Override
  public ASTNode visitAssignST(AssignSTContext ctx) {
    ASTNode lhsNode = visit(ctx.lhs());
    ASTNode rhsNode = visit(ctx.rhs());
    if (rhsNode instanceof PairElemAST) {
      rhsNode = new PairElemRightAST(ctx, (PairElemAST) rhsNode);
    }
    if (lhsNode instanceof PairElemAST) {
      lhsNode = new PairElemLeftAST(ctx, (PairElemAST) lhsNode);
    }
    AssignmentLeftAST lhs = (AssignmentLeftAST) lhsNode;
    AssignmentRightAST rhs = (AssignmentRightAST) rhsNode;
    return new AssignmentAST(ctx, lhs, rhs);
  }

  @Override
  public If visitIfST(IfSTContext ctx) {
    ExpressionAST expr = visitExpr(ctx.expr());
    StatementSequenceAST thenBody = (StatementSequenceAST) visit(ctx.thenBody);
    StatementSequenceAST elseBody = (StatementSequenceAST) visit(ctx.elBody);
    return new If(ctx, expr, thenBody, elseBody);
  }

  @Override
  public Skip visitSkipST(SkipSTContext ctx) {
    return new Skip(ctx);
  }

  @Override
  public Read visitReadST(ReadSTContext ctx) {
    ASTNode lhsNode = visit(ctx.lhs());
    if (lhsNode instanceof PairElemAST) {
      lhsNode = new PairElemLeftAST(ctx, (PairElemAST) lhsNode);
    }
    AssignmentLeftAST lhs = (AssignmentLeftAST) lhsNode;
    return new Read(ctx, lhs);
  }

  @Override
  public Begin visitBeginST(BeginSTContext ctx) {
    StatementSequenceAST statSeq = (StatementSequenceAST) visit(ctx.statSeq());
    return new Begin(ctx, statSeq);
  }

  @Override
  public Print visitPrintST(PrintSTContext ctx) {
    ExpressionAST expr = visitExpr(ctx.expr());
    return new Print(ctx, expr);
  }

  @Override
  public Exit visitExitST(ExitSTContext ctx) {
    ExpressionAST expr = visitExpr(ctx.expr());
    return new Exit(ctx, expr);
  }

  @Override
  public ASTNode visitInitST(InitSTContext ctx) {
    TypeAST type = (TypeAST) visit(ctx.type());
    IdentLeftAST ident = new IdentLeftAST(ctx, ctx.IDENT().getText());
    ASTNode rhsNode = visit(ctx.rhs());
    if (rhsNode instanceof PairElemAST) {
      rhsNode = new PairElemRightAST(ctx, (PairElemAST) rhsNode);
    }
    AssignmentRightAST rhs = (AssignmentRightAST) rhsNode;
    return new InitializationAST(ctx, type, ident, rhs);
  }

  @Override
  public Println visitPrintlnST(PrintlnSTContext ctx) {
    ExpressionAST expr = visitExpr(ctx.expr());
    return new Println(ctx, expr);
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

    return new SignedIntExprAST(ctx, intSign, ctx.UINT_LTR().toString());
  }

  @Override
  public PairLtrExprAST visitPairLtrEXP(PairLtrEXPContext ctx) {
    return new PairLtrExprAST(ctx, ctx.getText());
  }

  @Override
  public ArrayElemExprAST visitArrayElemEXP(ArrayElemEXPContext ctx) {
    ArrayElemAST arrayElemAST = (ArrayElemAST) visitArrayElem(ctx.arrayElem());
    return new ArrayElemExprAST(ctx, arrayElemAST);
  }

  @Override
  public BinaryOpExprAST visitBinOpEXP(BinOpEXPContext ctx) {
    return new BinaryOpExprAST(ctx, visitExpr(ctx.expr(0)),
        visitExpr(ctx.expr(1)), ctx.binaryOp().getText());
  }

  @Override
  public StringExprAST visitStrEXP(StrEXPContext ctx) {
    return new StringExprAST(ctx, ctx.getText());
  }

  @Override
  public UnaryOpExprAST visitUnOpEXP(UnOpEXPContext ctx) {
    return new UnaryOpExprAST(ctx, visitExpr(ctx.expr()),
        ctx.unaryOp().getText());
  }

  @Override
  public ExpressionAST visitBracketEXP(BracketEXPContext ctx) {
    return visitExpr(ctx.expr());
  }

  @Override
  public BoolExprAST visitBoolEXP(BoolEXPContext ctx) {
    return new BoolExprAST(ctx, ctx.getText());
  }

  @Override
  public IdentAST visitIdentEXP(IdentEXPContext ctx) {
    return new IdentAST(ctx, ctx.getText());
  }

  @Override
  public CharExprAST visitCharEXP(CharEXPContext ctx) {
    return new CharExprAST(ctx, ctx.getText());
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

    if (ctx.argList() != null) {
      for (ExprContext argExpr : ctx.argList().expr()) {
        argList.add(visitExpr(argExpr));
      }
    }
    return new FunctionCallRightAST(ctx, ctx.IDENT().getText(), argList);
  }

  @Override
  public ASTNode visitArgList(ArgListContext ctx) {
    return null;
  }

  @Override
  public ASTNode visitPrimTypeTP(PrimTypeTPContext ctx) {
    return new PrimTypeAST(ctx, ctx.TYPE().getText());
  }

  @Override
  public ASTNode visitArrayTypeTP(ArrayTypeTPContext ctx) {
    return visit(ctx.arrayType());
  }

  @Override
  public ASTNode visitPairTypeTP(PairTypeTPContext ctx) {
    return visit(ctx.pairType());
  }

  @Override
  public ASTNode visitPrimArrayAT(PrimArrayATContext ctx) {
    TypeAST elemType = (TypeAST) new PrimTypeAST(ctx, ctx.TYPE().getText());
    TypeAST arrayType = elemType;
    for (int i = 0; i < ctx.LSBR().size(); i++) {
      arrayType = new ArrayTypeAST(ctx, elemType);
      elemType = arrayType;
    }
    return arrayType;
  }

  @Override
  public ASTNode visitPairArrayAT(PairArrayATContext ctx) {
    TypeAST elemType = (TypeAST) visitPairType(ctx.pairType());
    TypeAST arrayType = elemType;
    for (int i = 0; i < ctx.LSBR().size(); i++) {
      arrayType = new ArrayTypeAST(ctx, elemType);
      elemType = arrayType;
    }
    return arrayType;
  }

  @Override
  public ASTNode visitPairType(PairTypeContext ctx) {
    TypeAST type1 = (TypeAST) visit(ctx.pairElemType(0));
    TypeAST type2 = (TypeAST) visit(ctx.pairElemType(1));
    return new PairTypeAST(ctx, type1, type2);
  }

  @Override
  public ASTNode visitPrimTypePET(PrimTypePETContext ctx) {
    return new PrimTypeAST(ctx, ctx.TYPE().getText());
  }

  @Override
  public ASTNode visitPairTypePET(PairTypePETContext ctx) {
    return new PairTypeAST(ctx, null, null);
  }

  @Override
  public ASTNode visitArrayTypePET(ArrayTypePETContext ctx) {
    System.out.println("in visitArrayTypePET");
    return new ArrayTypeAST(ctx, visitArrayTypePETHelper((PrimArrayATContext) ctx.arrayType()));
  }

  private TypeAST visitArrayTypePETHelper(PrimArrayATContext ctx) {
    TypeAST elemType = (TypeAST) new PrimTypeAST(ctx, ctx.TYPE().getText());
    TypeAST arrayType = elemType;
    for (int i = 1; i < ctx.LSBR().size(); i++) {
      System.out.println(i);
      arrayType = new ArrayTypeAST(ctx, elemType);
      elemType = arrayType;
    }
    arrayType.check();
    return arrayType;
  }


}