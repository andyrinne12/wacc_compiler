parser grammar WACCParser;

@parser::members {
    private boolean inBounds(Token sign,Token t) {
        long n = Long.parseLong(t.getText());
        long MAX = Integer.MAX_VALUE;
        if(sign != null){
          String sgn = sign.getText();
          if(sgn.equals("-")){
          MAX +=1;
          }
        }
        boolean result = n <= MAX;
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

expr: BOOL_LTR
  | CHAR_LTR
  | STR_LTR
  | PAIR_LTR
  | IDENT
  | arrayElem
  | expr binaryOp expr
  | (sign=(PLUS | MINUS)?) UINT_LTR {inBounds($sign, $UINT_LTR)}?
  | unaryOp expr
  | LBR expr RBR;

lhs: IDENT
  | arrayElem
  | pairElem;

rhs: expr
  | arrayLtr
  | NEW_PR LBR expr CMA expr RBR
  | pairElem
  | CALL IDENT LBR argList? RBR;

prog: BEGIN (func)* stat END EOF ;

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

unaryOp: NOT | MINUS | LEN | ORD | CHR;

binaryOp: STAR | DIV | MOD | GREATER | GREATER_EQUAL | LESSER | LESSER_EQUAL | EQUAL | NOT_EQUAL | AND | OR | PLUS | MINUS;