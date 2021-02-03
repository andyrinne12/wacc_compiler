parser grammar WACCParser;

options {
  tokenVocab=WACCLexer;
}

stat: SKP
   | TYPE IDENT ASGN rhs
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

expr: .*?;

lhs: .*?;
rhs: .*?;

prog: BEGIN stat END EOF ;
