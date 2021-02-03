parser grammar WACCParser;

options {
  tokenVocab=WACCLexer;
}

stat: SKP | stat SEMI stat;

prog: .*? EOF ;
