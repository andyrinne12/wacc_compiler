parser grammar WACCParser;

options {
  tokenVocab=WACCLexer;
}
//
//elem: (CHAR_LTR |  STR_LTR) elem;
//
//expr: expr binaryOper expr
//| INTEGER
//| OPEN_PARENTHESES expr CLOSE_PARENTHESES
//;

// EOF indicates that the program must consume to the end of the input.
prog: . EOF ;
