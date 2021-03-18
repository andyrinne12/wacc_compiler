lexer grammar WACCLexer;

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
CALL: 'call';
SEMI: ';';
CMA: ',';

IF: 'if';
FI: 'fi';
THEN: 'then';
ELSE: 'else';
BREAK: 'break';

SWITCH: 'switch';
CASE: 'case';
DEFAULT: 'default';
COLON: ':';

WHILE: 'while';
DO: 'do';
DONE: 'done';

NEW_PR: 'newpair';
FST: 'fst';
SND: 'snd';
PAIR: 'pair';

ASGN: '=';
LBR: '(';
RBR: ')';
LSBR: '[';
RSBR: ']';
LPR: '{';
RPR: '}';

TYPE: 'int' | 'bool' | 'char' | 'string';

SQ: '\'';
DQ: '"';
ESC: '\\';
fragment USCORE: '_';

fragment CHAR: ~('\\' | '\'' | '"') | ESC ESC_CHAR;
fragment ESC_CHAR: SQ | DQ | ESC | '0' | 'b' | 't' | 'n' | 'f' | 'r';

fragment DIGIT: [0-9] ;
fragment ALPHA: [a-z] | [A-Z];

PLUS: '+';
MINUS: '-';

NOT: '!';
LEN: 'len';
ORD: 'ord';
CHR: 'chr';

STAR: '*';
DIV: '/';
MOD: '%';
GREATER: '>';
GREATER_EQUAL: '>=';
LESSER: '<';
LESSER_EQUAL: '<=';
EQUAL: '==';
NOT_EQUAL: '!=';
AND: '&&';
OR: '||';

BOOL_LTR: 'true' | 'false';
PAIR_LTR: 'null';

IDENT: (ALPHA | USCORE) (ALPHA | USCORE | DIGIT)*;

CHAR_LTR: SQ CHAR SQ;
STR_LTR: DQ CHAR* DQ;

UINT_LTR: DIGIT+;

WS: [ \n\r\t] -> skip;
COMM: '#' ~('\n' | '\r')* -> skip;

NOISE: .; // any other case should be caught by this noise
