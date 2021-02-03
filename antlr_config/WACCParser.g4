parser grammar WACCParser;

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

expr: INT_LTR
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

type: .*?;

arrayType: type LSBR RSBR;

arrayElem: IDENT (LSBR expr RSBR)+;

arrayLtr: LSBR (expr (CMA expr)*)? RSBR;

pairType: PAIR LBR pairElemType CMA pairElemType RBR;

pairElem: FST expr
      | SND expr;

pairElemType: TYPE
          | arrayType
          | PAIR;
