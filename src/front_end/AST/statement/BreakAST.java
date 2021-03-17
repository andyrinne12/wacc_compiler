package front_end.AST.statement;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import back_end.FunctionBody;
import back_end.instructions.Condition;
import back_end.instructions.branch.B;
import back_end.operands.registers.Register;

// Roles of the break statement:
// exit the encompassing while loop
public class BreakAST extends StatementAST {
    
    private String endLabel; // allows break statements to know where to jump to, in order to exit a while loop.

    public BreakAST(ParserRuleContext ctx) {
        super(ctx);
    }

    @Override
    public void check() {
        // the check to see if we're inside a while loop, has been done during syntactic analysis.
        // no need to do anything here.
    }

    public void assemble(FunctionBody body, List<Register> freeRegs) {
        body.addInstr(new B(Condition.NONE, endLabel));
    }

    public void setEndLabel(String endLabel) {
        this.endLabel = endLabel;
    }
}
