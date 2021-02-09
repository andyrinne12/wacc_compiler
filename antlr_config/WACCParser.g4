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

prog: BEGIN (func)* stat END EOF ;

stat: SKP                             #skipST
   | type IDENT ASGN rhs              #asignST
   | lhs ASGN rhs                     #initST
   | READ lhs                         #readST
   | FREE expr                        #freeST
   | RETURN expr                      #returnST
   | EXIT expr                        #exitST
   | PRINT expr                       #printST
   | PRINTLN expr                     #printlnST
   | IF expr THEN stat ELSE stat FI   #ifST
   | WHILE expr DO stat DONE          #whileST
   | BEGIN stat END                   #beginST
   | stat SEMI stat                   #statSeqST
;

expr: BOOL_LTR                                                        #boolEXP
  | CHAR_LTR                                                          #charEXP
  | STR_LTR                                                           #strEXP
  | PAIR_LTR                                                          #pairLtrEXP
  | IDENT                                                             #identEXP
  | arrayElem                                                         #arrayElemEXP
  | expr binaryOp expr                                                #binOpEXP
  | (sign=(PLUS | MINUS)?) UINT_LTR {inBounds($sign, $UINT_LTR)}?     #signedIntEXP
  | unaryOp expr                                                      #unOpEXP
  | LBR expr RBR                                                      #bracketEXP
;

lhs: IDENT                           #identLHS
  | arrayElem                        #arrayElemLHS
  | pairElem                         #pairElemLHS
;

rhs: expr                            #expRHS
  | arrayLtr                         #arrayLtrRHS
  | NEW_PR LBR expr CMA expr RBR     #newPairRHS
  | pairElem                         #pairElemRHS
  | CALL IDENT LBR argList? RBR      #funcCallRHS
;

func: type IDENT LBR paramList? RBR IS stat END;

param: type IDENT;

paramList: param (CMA param)*;

argList: expr (CMA expr)*;

type: TYPE                           #primTypeTP
  | arrayType                        #arrayTypeTP
  | pairType                         #pairTypeTP
;

arrayType: TYPE LSBR RSBR            #primTypeARTP
        | arrayType LSBR RSBR        #arrayTypeARTP
        | pairType LSBR RSBR         #pairTypeARTP
;

arrayElem: IDENT (LSBR expr RSBR)+;

arrayLtr: LSBR (expr (CMA expr)*)? RSBR;

pairType: PAIR LBR pairElemType CMA pairElemType RBR;

pairElem: FST expr                  #firstElemPR
      | SND expr                    #secondElemPR
;

pairElemType: TYPE                  #primTypePRTP
          | arrayType               #arrayTypePRTP
          | PAIR                    #pairPRTP
;

unaryOp: op=(NOT | MINUS | LEN | ORD | CHR);

binaryOp: op=(STAR | DIV | MOD | GREATER | GREATER_EQUAL | LESSER | LESSER_EQUAL | EQUAL | NOT_EQUAL | AND | OR | PLUS | MINUS);