lexer grammar WACCLexer;

fragment DIGIT: [0-9] ;
fragment ALPHA: [a-z] | [A-Z];
fragment ESC_CHAR: '\\' | '\'' | '"' | 'b' | 't' | 'n' | 'f' | 'r';
fragment CHAR: ~('\\' | '\'' | '"') | '\\' ESC_CHAR;
fragment USCORE: '_';

MINUS: '-';
PLUS: '+';
UN_OP: '!' | 'len' | 'ord' | 'chr';
BIN_OP:  '*' | '/' | '%' | '>' | '<' | '>=' | '<=' | '==' | '!=' | '&&' | '||';

IDENT: (ALPHA | USCORE) (ALPHA | USCORE | DIGIT)*;

BOOL_LTR: 'true' | 'false';
PAIR_LTR: 'null';
CHAR_LTR: '\'' CHAR '\'';
STR_LTR: '"' CHAR+ '"';

fragment INT_SGN: PLUS | MINUS;
INT_LTR: INT_SGN? DIGIT+;


BEGIN: 'begin';
END: 'end';

IS: 'is';
SKP: 'skip';
READ: 'read';
FREE: 'free';
RETURN: 'return';
EXIT: 'exit';
PRINT: 'print';
PRINTLN: 'println';
SCLN: ';';
CMA: ',';

IF: 'if';
FI: 'fi';
THEN: 'then';
ELSE: 'else';

WHILE: 'while';
DO: 'do';
DONE: 'done';

NEW_PR: 'newpair';
FST: 'fst';
SND: 'snd';
PAIR: 'pair';

LBR: '(';
RBR: ')';
LSBR: '[';
RSBR: ']';
LPR: '{';
RPR: '}';

WS: [ \n\r\t] -> skip;
COMM: '#' ~('\n' | '\r')* -> skip;



