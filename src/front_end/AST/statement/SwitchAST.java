package front_end.AST.statement;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import back_end.FunctionBody;
import back_end.operands.registers.Register;
import front_end.AST.expression.IdentAST;

public class SwitchAST extends StatementAST {

    private final IdentAST ident;
    private final List<CaseAST> cases;
    private final StatementSequenceAST defaultStatSeq;
    
    public SwitchAST(ParserRuleContext ctx, List<CaseAST> cases, IdentAST ident, StatementSequenceAST defaultStatSeq) {
        super(ctx);
        this.cases = cases;
        this.ident = ident;
        this.defaultStatSeq = defaultStatSeq;
    }

    @Override
    public void check() {
        // check that ident is of a primitive type: int, string, char, bool
        // set the switchIdentType of each case, and check each case
        // check the defaultStatSeq
    }

    @Override
    public void assemble(FunctionBody body, List<Register> freeRegs) {
        
    }

}
