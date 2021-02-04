parser grammar WACCParser;

@parser::members {
    private boolean inBounds(Token t) {
        long n = Long.parseLong(t.getText());
        boolean result = n >= Integer.MIN_VALUE && n <= Integer.MAX_VALUE;
        if (!result) {
          System.out.println("Integer value " + n + " at line " + t.getLine() + " is too large for a 32-bit signed integer.");
        }
        return result;
    }
}

options {
  tokenVocab=WACCLexer;
}

stat: SKP
   | type IDENT ASGN rhs
   | lhs ASGN rhs
   | READ lhs
   | FREE expr
   | RETURN expr
   | EXIT expr
   | PRINT expr
   | PRINTLN expr
   | IF expr THEN stat ELSE stat FI
   | WHILE expr DO stat DONE
   | BEGIN stat END
   | stat SEMI stat;

expr: INT_LTR {inBounds($INT_LTR)}?
  | BOOL_LTR
  | CHAR_LTR
  | STR_LTR
  | PAIR_LTR
  | IDENT
  | arrayElem
  | UN_OP expr
  | expr BIN_OP expr
  | LBR expr RBR;

lhs: IDENT
  | arrayElem
  | pairElem;

rhs: expr
  | arrayLtr
  | NEW_PR LBR expr CMA expr RBR
  | pairElem
  | CALL IDENT LBR argList? RBR;

prog: BEGIN stat END EOF ;

func: type IDENT LBR paramList? RBR IS stat END;

param: type IDENT;

paramList: param (CMA param)*;

argList: expr (CMA expr)*;

type: TYPE
  | arrayType
  | pairType;

arrayType: TYPE LSBR RSBR
        | arrayType LSBR RSBR
        | pairType LSBR RSBR;

arrayElem: IDENT (LSBR expr RSBR)+;

arrayLtr: LSBR (expr (CMA expr)*)? RSBR;

pairType: PAIR LBR pairElemType CMA pairElemType RBR;

pairElem: FST expr
      | SND expr;

pairElemType: TYPE
          | arrayType
          | PAIR;
