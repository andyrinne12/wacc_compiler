package front_end;

import antlr.WACCParser.*;
import antlr.WACCParserBaseVisitor;
import antlr.WACCParserVisitor;
import front_end.AST.ASTNode;
import front_end.AST.ProgramAST;
import front_end.AST.assignment.*;
import front_end.AST.expression.*;
import front_end.AST.function.*;
import front_end.AST.statement.*;
import front_end.AST.type.*;
import front_end.types.*;
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
    System.err.println("Error at " + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine()
        + " -- " + message);
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
    if (ctx != null && ctx.param() != null) {
      for (ParamContext p : ctx.param()) {
        paramASTs.add(visitParam(p));
      }
    }
    return new ParamListAST(ctx, paramASTs);
  }

  @Override
  public ASTNode visitStatSeq(StatSeqContext ctx) {
    SymbolTable symbolTable = new SymbolTable(Visitor.ST);
    Visitor.ST = symbolTable;

    List<StatementAST> statSeq = new ArrayList<>();
    for (StatContext statCtx : ctx.stat()) {
      statSeq.add((StatementAST) visit(statCtx));
    }

    Visitor.ST = symbolTable.getParentST();

    return new StatementSequenceAST(ctx, symbolTable, statSeq);
  }

  @Override
  public ReturnAST visitReturnST(ReturnSTContext ctx) {
    ParserRuleContext tmpContext = getEnclosingFunctionContext(ctx);
    if (tmpContext instanceof ProgContext) {
      return null;
    }

    FuncContext funcContext = (FuncContext) tmpContext;
    TypeAST returnType = (TypeAST) visit(funcContext.type());
    ExpressionAST expression = (ExpressionAST) visit(ctx.expr());

    return new ReturnAST(ctx, expression, returnType);
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
  public BreakAST visitBreakST(BreakSTContext ctx) {
    checkForEnclosingWhileContext(ctx);
    return new BreakAST(ctx);
  }

  private void checkForEnclosingWhileContext(ParserRuleContext ctx) {
    ParserRuleContext parentContext = ctx.getParent();

    while (!(parentContext instanceof WhileSTContext)) {
      parentContext = parentContext.getParent();

      if (parentContext instanceof ProgContext) {
        error(ctx, "The break statement must be in a while loop");
        break;
      }
    }

  }

  @Override
  public ASTNode visitWhileST(WhileSTContext ctx) {
    ExpressionAST expr = (ExpressionAST) visit(ctx.expr());
    StatementSequenceAST statSeq = (StatementSequenceAST) visitStatSeq(ctx.statSeq());
    return new WhileAST(ctx, expr, statSeq);
  }

  @Override
  public FreeAST visitFreeST(FreeSTContext ctx) {
    ExpressionAST expr = (ExpressionAST) visit(ctx.expr());
    return new FreeAST(ctx, expr);
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
  public SwitchAST visitSwitchCaseST(SwitchCaseSTContext ctx) {   
    List<CaseAST> cases = new ArrayList<>();
    for (CaseBodyContext caseCtx: ctx.caseBody()) {
      cases.add(visitCaseBody(caseCtx));
    }

    StatementSequenceAST statSeq = (StatementSequenceAST) visitStatSeq(ctx.statSeq());

    return new SwitchAST(ctx, ctx.IDENT().getText(), cases, statSeq);
  }

  @Override
  public CaseAST visitCaseBody(CaseBodyContext ctx) {
    ExpressionAST expr = (ExpressionAST) visit(ctx.expr());
    StatementSequenceAST statSeq = (StatementSequenceAST) visit(ctx.statSeq());
    return new CaseAST(ctx, expr, statSeq);
  }

  @Override
  public IfAST visitIfST(IfSTContext ctx) {
    ExpressionAST expr = (ExpressionAST) visit(ctx.expr());
    StatementSequenceAST thenBody = (StatementSequenceAST) visit(ctx.thenBody);
    StatementSequenceAST elseBody = (StatementSequenceAST) visit(ctx.elBody);
    return new IfAST(ctx, expr, thenBody, elseBody);
  }

  @Override
  public IfAST visitPlainIfST(PlainIfSTContext ctx) {
    ExpressionAST expr = (ExpressionAST) visit(ctx.expr());
    StatementSequenceAST thenBody = (StatementSequenceAST) visit(ctx.statSeq());

    return new IfAST(ctx, expr, thenBody, null);
  }

  @Override
  public SkipAST visitSkipST(SkipSTContext ctx) {
    return new SkipAST(ctx);
  }

  @Override
  public ReadAST visitReadST(ReadSTContext ctx) {
    ASTNode lhsNode = visit(ctx.lhs());
    if (lhsNode instanceof PairElemAST) {
      lhsNode = new PairElemLeftAST(ctx, (PairElemAST) lhsNode);
    }
    AssignmentLeftAST lhs = (AssignmentLeftAST) lhsNode;
    return new ReadAST(ctx, lhs);
  }

  @Override
  public BeginAST visitBeginST(BeginSTContext ctx) {
    StatementSequenceAST statSeq = (StatementSequenceAST) visit(ctx.statSeq());
    return new BeginAST(ctx, statSeq);
  }

  @Override
  public PrintAST visitPrintST(PrintSTContext ctx) {
    ExpressionAST expr = (ExpressionAST) visit(ctx.expr());
    return new PrintAST(ctx, expr);
  }

  @Override
  public ExitAST visitExitST(ExitSTContext ctx) {
    ExpressionAST expr = (ExpressionAST) visit(ctx.expr());
    return new ExitAST(ctx, expr);
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
  public PrintlnAST visitPrintlnST(PrintlnSTContext ctx) {
    ExpressionAST expr = (ExpressionAST) visit(ctx.expr());
    return new PrintlnAST(ctx, expr);
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
  public ASTNode visitP4EXP(P4EXPContext ctx) {
    return new BinaryOpExprAST(ctx, (ExpressionAST) visit(ctx.expr(0)),
        (ExpressionAST) visit(ctx.expr(1)), ctx.p4op().getText());

  }

  @Override
  public ASTNode visitP3EXP(P3EXPContext ctx) {
    return new BinaryOpExprAST(ctx, (ExpressionAST) visit(ctx.expr(0)),
        (ExpressionAST) visit(ctx.expr(1)), ctx.p3op().getText());
  }

  @Override
  public ASTNode visitP2EXP(P2EXPContext ctx) {
    return new BinaryOpExprAST(ctx, (ExpressionAST) visit(ctx.expr(0)),
        (ExpressionAST) visit(ctx.expr(1)), ctx.p2op().getText());

  }

  @Override
  public ASTNode visitP1EXP(P1EXPContext ctx) {
    return new BinaryOpExprAST(ctx, (ExpressionAST) visit(ctx.expr(0)),
        (ExpressionAST) visit(ctx.expr(1)), ctx.p1op().getText());

  }

  @Override
  public StringExprAST visitStrEXP(StrEXPContext ctx) {
    return new StringExprAST(ctx, ctx.getText());
  }

  @Override
  public UnaryOpExprAST visitUnOpEXP(UnOpEXPContext ctx) {
    return new UnaryOpExprAST(ctx, (ExpressionAST) visit(ctx.expr()),
        ctx.unaryOp().getText());
  }

  @Override
  public ExpressionAST visitBracketEXP(BracketEXPContext ctx) {
    return (ExpressionAST) visit(ctx.expr());
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
    return new ExprRightAST(ctx, (ExpressionAST) visit(ctx.expr()));
  }

  @Override
  public ASTNode visitArrayLtrRHS(ArrayLtrRHSContext ctx) {
    List<ExpressionAST> array = new ArrayList<>();
    for (ExprContext expr : ctx.expr()) {
      array.add((ExpressionAST) visit(expr));
    }
    return new ArrayLtrRightAST(ctx, array);
  }

  @Override
  public ASTNode visitNewPairRHS(NewPairRHSContext ctx) {
    ExprRightAST expr1 = new ExprRightAST(ctx, (ExpressionAST) visit(ctx.expr(0)));
    ExprRightAST expr2 = new ExprRightAST(ctx, (ExpressionAST) visit(ctx.expr(1)));
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
      indices.add((ExpressionAST) visit(expr));
    }
    return new ArrayElemAST(ctx, indices);
  }

  @Override
  public ASTNode visitPairElem(PairElemContext ctx) {
    return new PairElemAST(ctx, (ExpressionAST) visit(ctx.expr()));
  }

  @Override
  public ASTNode visitFuncCallRHS(FuncCallRHSContext ctx) {
    List<ExpressionAST> argList = new ArrayList<>();

    if (ctx.argList() != null) {
      for (ExprContext argExpr : ctx.argList().expr()) {
        argList.add((ExpressionAST) visit(argExpr));
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