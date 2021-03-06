package front_end.AST.statement;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import back_end.FunctionBody;
import back_end.instructions.Label;
import back_end.operands.registers.Register;
import front_end.AST.*;
import front_end.AST.expression.ExpressionAST;
import front_end.types.TYPE;

public class CaseAST extends ASTNode {

    private final ExpressionAST caseExpr;
    private final StatementSequenceAST statSeq;
    private TYPE switchIdentType; // the type of the identifier in the switch statement
    private String caseLabel;
    private String endLabel;
    
    public CaseAST(ParserRuleContext ctx, ExpressionAST caseExpr, StatementSequenceAST statSeq) {
        super(ctx);
        this.caseExpr = caseExpr;
        this.statSeq = statSeq;
    }
    
    @Override
    public void check() {
        // check that caseExpr is a valid expression
        caseExpr.check();

        // check that caseExpr is of the same type as the switchIdentType
        if (caseExpr.getEvalType() != switchIdentType) {
            error("Type mismatch. Expected type: " + switchIdentType + "; Actual type: " + caseExpr.getEvalType());
        }

        // check that the statements in statSeq are valid statements
        statSeq.check();
    }

    @Override
    public void assemble(FunctionBody body, List<Register> freeRegs) {
        // only need to assemble the statement sequence. The caseExpr is assembled in SwitchAST.
        body.addInstr(new Label(caseLabel));
        statSeq.setEndLabel(endLabel);
        statSeq.assemble(body, freeRegs);
    }

    public void setSwitchIdentType(TYPE type) {
        switchIdentType = type;
    }

    public void setCaseLabel(String caseLabel) {
        this.caseLabel = caseLabel;
    }

    public ExpressionAST getCaseExpr() {
        return caseExpr;
    }

    public void setEndLabel(String endLabel) {
        this.endLabel = endLabel;
    }

}
