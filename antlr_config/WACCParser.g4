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

prog: BEGIN (func)* statSeq END EOF ;

stat: SKP                                 #skipST
   | type IDENT ASGN rhs                  #initST
   | lhs ASGN rhs                         #assignST
   | READ lhs                             #readST
   | FREE expr                            #freeST
   | RETURN expr                          #returnST
   | EXIT expr                            #exitST
   | PRINT expr                           #printST
   | PRINTLN expr                         #printlnST
   | IF expr THEN thenBody=statSeq ELSE elBody=statSeq FI #ifST
   | IF expr THEN statSeq FI              #plainIfST
   | WHILE expr DO statSeq DONE           #whileST
   | BEGIN statSeq END                    #beginST
   | BREAK                                #breakST
;

statSeq: stat (SEMI stat)*;

expr: BOOL_LTR                                                        #boolEXP
  | CHAR_LTR                                                          #charEXP
  | STR_LTR                                                           #strEXP
  | PAIR_LTR                                                          #pairLtrEXP
  | IDENT                                                             #identEXP
  | arrayElem                                                         #arrayElemEXP
  | (sign=(PLUS | MINUS)?) UINT_LTR {inBounds($sign, $UINT_LTR)}?     #signedIntEXP
  | LBR expr RBR                                                      #bracketEXP
  | unaryOp expr                                                      #unOpEXP
  | expr p1op expr                                                    #p1EXP
  | expr p2op expr                                                    #p2EXP
  | expr p3op expr                                                    #p3EXP
  | expr p4op expr                                                    #p4EXP
;

lhs: IDENT                           #identLHS
  | arrayElem                        #arrayElemLHS
  | pairElem                         #pairElemLHS
;

rhs: expr                                #expRHS
  | LSBR (expr (CMA expr)*)? RSBR        #arrayLtrRHS
  | NEW_PR LBR expr CMA expr RBR         #newPairRHS
  | pairElem                             #pairElemRHS
  | CALL IDENT LBR argList? RBR          #funcCallRHS
;

func: type IDENT LBR paramList? RBR IS statSeq END;

param: type IDENT;

paramList: param (CMA param)*;

argList: expr (CMA expr)*;

type: TYPE                          #primTypeTP
  | arrayType                       #arrayTypeTP
  | pairType                        #pairTypeTP
;

arrayElem: IDENT (LSBR expr RSBR)+;

arrayType: TYPE (LSBR RSBR)+        #primArrayAT
  |  pairType (LSBR RSBR)+          #pairArrayAT
 ;

pairElem: elem=(FST | SND) expr;

pairType: PAIR LBR pairElemType CMA pairElemType RBR;

pairElemType: TYPE                  #primTypePET
  | PAIR                            #pairTypePET
  | arrayType                       #arrayTypePET
;

unaryOp: op=(NOT | MINUS | LEN | ORD | CHR);

p4op: op=(AND | OR );
p3op: op=(GREATER | GREATER_EQUAL | LESSER | LESSER_EQUAL | EQUAL | NOT_EQUAL);
p2op: op=(PLUS | MINUS);
p1op: op=(STAR | DIV | MOD);