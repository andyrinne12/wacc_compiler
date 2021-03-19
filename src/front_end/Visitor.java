package front_end;

import antlr.WACCParser.AddressRefContext;
import antlr.WACCParser.AddressRefEXPContext;
import antlr.WACCParser.ArgListContext;
import antlr.WACCParser.ArrayElemContext;
import antlr.WACCParser.ArrayElemEXPContext;
import antlr.WACCParser.ArrayElemLHSContext;
import antlr.WACCParser.ArrayLtrRHSContext;
import antlr.WACCParser.ArrayTypePETContext;
import antlr.WACCParser.ArrayTypeTPContext;
import antlr.WACCParser.AssignSTContext;
import antlr.WACCParser.BeginSTContext;
import antlr.WACCParser.BoolEXPContext;
import antlr.WACCParser.BracketEXPContext;
import antlr.WACCParser.BreakSTContext;
import antlr.WACCParser.CaseBodyContext;
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
import antlr.WACCParser.P1EXPContext;
import antlr.WACCParser.P2EXPContext;
import antlr.WACCParser.P3EXPContext;
import antlr.WACCParser.P4EXPContext;
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
import antlr.WACCParser.PlainIfSTContext;
import antlr.WACCParser.PointerArrayATContext;
import antlr.WACCParser.PointerDerefContext;
import antlr.WACCParser.PointerDerefEXPContext;
import antlr.WACCParser.PointerDerefLHSContext;
import antlr.WACCParser.PointerPTContext;
import antlr.WACCParser.PointerTypePETContext;
import antlr.WACCParser.PrimArrayATContext;
import antlr.WACCParser.PrimPTContext;
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
import antlr.WACCParser.SwitchCaseSTContext;
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
import front_end.AST.assignment.PointerDerefAST;
import front_end.AST.expression.AddressRefExprAST;
import front_end.AST.expression.ArrayElemExprAST;
import front_end.AST.expression.BinaryOpExprAST;
import front_end.AST.expression.BoolExprAST;
import front_end.AST.expression.CharExprAST;
import front_end.AST.expression.ExpressionAST;
import front_end.AST.expression.IdentAST;
import front_end.AST.expression.PairLtrExprAST;
import front_end.AST.expression.PointerDerefExprAST;
import front_end.AST.expression.SignedIntExprAST;
import front_end.AST.expression.StringExprAST;
import front_end.AST.expression.UnaryOpExprAST;
import front_end.AST.function.FunctionDeclAST;
import front_end.AST.function.ParamAST;
import front_end.AST.function.ParamListAST;
import front_end.AST.statement.BeginAST;
import front_end.AST.statement.BreakAST;
import front_end.AST.statement.CaseAST;
import front_end.AST.statement.ExitAST;
import front_end.AST.statement.FreeAST;
import front_end.AST.statement.IfAST;
import front_end.AST.statement.PrintAST;
import front_end.AST.statement.PrintlnAST;
import front_end.AST.statement.ReadAST;
import front_end.AST.statement.ReturnAST;
import front_end.AST.statement.SkipAST;
import front_end.AST.statement.StatementAST;
import front_end.AST.statement.StatementSequenceAST;
import front_end.AST.statement.SwitchAST;
import front_end.AST.statement.WhileAST;
import front_end.AST.type.ArrayTypeAST;
import front_end.AST.type.PairTypeAST;
import front_end.AST.type.PointerTypeAST;
import front_end.AST.type.PrimTypeAST;
import front_end.AST.type.TypeAST;
import front_end.types.BOOLEAN;
import front_end.types.CHAR;
import front_end.types.FUNCTION;
import front_end.types.INT;
import front_end.types.PARAM;
import front_end.types.POINTER;
import front_end.types.STRING;
import java.util.ArrayList;
import java.util.Collections;
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

    FUNCTION function = new FUNCTION(new POINTER(null),
        Collections.singletonList(new PARAM(Top_ST.lookup("int").getType())));
    Top_ST.add("malloc", function);

    ST = new SymbolTable(Top_ST);
  }

  // for semantic errors
  public static void error(ParserRuleContext ctx, String message) {
    System.err.println(ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine()
        + "  error: " + message);
    Main.EXIT_CODE = 200;
  }

  public static void warning(ParserRuleContext ctx, String message) {
    if (Main.WARNINGS_DISABLED) {
      return;
    }
    System.err.println(ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine()
        + "  warning: " + message);
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
    checkBreakStatement(ctx);
    return new BreakAST(ctx);
  }

  // ensures that break statements are only used inside while loops, or within switch-case statements.
  private void checkBreakStatement(ParserRuleContext ctx) {
    ParserRuleContext parentContext = ctx.getParent();

    while (!(parentContext instanceof WhileSTContext
        || parentContext instanceof SwitchCaseSTContext)) {
      parentContext = parentContext.getParent();

      if (parentContext instanceof ProgContext) {
        error(ctx, "The break statement must be in a while loop or a switch-case statement.");
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
    for (CaseBodyContext caseCtx : ctx.caseBody()) {
      cases.add(visitCaseBody(caseCtx));
    }

    ExpressionAST expr = (ExpressionAST) visit(ctx.expr());
    StatementSequenceAST statSeq = (StatementSequenceAST) visitStatSeq(ctx.statSeq());

    return new SwitchAST(ctx, expr, cases, statSeq);
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
  public ASTNode visitPointerDerefEXP(PointerDerefEXPContext ctx) {
    return new PointerDerefExprAST(ctx, (PointerDerefAST) visitPointerDeref(ctx.pointerDeref()));
  }

  @Override
  public ASTNode visitAddressRefEXP(AddressRefEXPContext ctx) {
    return visitAddressRef(ctx.addressRef());
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
  public ASTNode visitPointerDerefLHS(PointerDerefLHSContext ctx) {
    return visitPointerDeref(ctx.pointerDeref());
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
  public ASTNode visitPointerDeref(PointerDerefContext ctx) {
    return new PointerDerefAST(ctx, (AssignmentLeftAST) visit(ctx.lhs()));
  }

  @Override
  public ASTNode visitAddressRef(AddressRefContext ctx) {
    return new AddressRefExprAST(ctx, (AssignmentLeftAST) visit(ctx.lhs()));
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
    TypeAST elemType = new PrimTypeAST(ctx, ctx.TYPE().getText());
    TypeAST arrayType = elemType;
    for (int i = 0; i < ctx.LSBR().size(); i++) {
      arrayType = new ArrayTypeAST(ctx, elemType);
      elemType = arrayType;
    }
    return arrayType;
  }

  @Override
  public ASTNode visitPointerArrayAT(PointerArrayATContext ctx) {
    TypeAST elemType = (TypeAST) visit(ctx.pointerType());
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
  public ASTNode visitPointerTypePET(PointerTypePETContext ctx) {
    return visit(ctx.pointerType());
  }

  @Override
  public ASTNode visitPairTypePET(PairTypePETContext ctx) {
    return new PairTypeAST(ctx, null, null);
  }

  @Override
  public ASTNode visitArrayTypePET(ArrayTypePETContext ctx) {
    return new ArrayTypeAST(ctx, visitArrayTypePETHelper((PrimArrayATContext) ctx.arrayType()));
  }

  @Override
  public ASTNode visitPointerPT(PointerPTContext ctx) {
    return new PointerTypeAST(ctx, (TypeAST) visit(ctx.pointerType()));
  }

  @Override
  public ASTNode visitPrimPT(PrimPTContext ctx) {
    return new PointerTypeAST(ctx, new PrimTypeAST(ctx, ctx.TYPE().getText()));
  }

  private TypeAST visitArrayTypePETHelper(PrimArrayATContext ctx) {
    TypeAST elemType = new PrimTypeAST(ctx, ctx.TYPE().getText());
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