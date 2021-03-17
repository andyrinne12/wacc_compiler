package front_end.AST.statement;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import back_end.FunctionBody;
import back_end.operands.registers.Register;
import front_end.AST.*;
import front_end.AST.expression.ExpressionAST;
import front_end.types.TYPE;

public class CaseAST extends ASTNode {

    private final ExpressionAST caseExpr;
    private final StatementSequenceAST statSeq;
    private TYPE switchIdentType; // the type of the identifier in the switch statement
    
    public CaseAST(ParserRuleContext ctx, ExpressionAST caseExpr, StatementSequenceAST statSeq) {
        super(ctx);
        this.caseExpr = caseExpr;
        this.statSeq = statSeq;
    }
    
    @Override
    public void check() {
        // check that caseExpr is a valid expression
        // check that caseExpr is of the same type as the switchIdentType
        // check that the statements in statSeq are valid statements
    }

    @Override
    public void assemble(FunctionBody body, List<Register> freeRegs) {
        
    }

    public void setSwitchIdentType(TYPE type) {
        switchIdentType = type;
    }

}
