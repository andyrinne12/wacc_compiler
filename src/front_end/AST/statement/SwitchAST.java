package front_end.AST.statement;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import back_end.FunctionBody;
import back_end.operands.registers.Register;
import front_end.Visitor;
import front_end.AST.expression.IdentAST;
import front_end.types.*;

public class SwitchAST extends StatementAST {

    private final IdentAST ident;
    private final List<CaseAST> cases;
    private final StatementSequenceAST defaultStatSeq;
    
    public SwitchAST(ParserRuleContext ctx, IdentAST ident, List<CaseAST> cases, StatementSequenceAST defaultStatSeq) {
        super(ctx);
        this.cases = cases;
        this.ident = ident;
        this.defaultStatSeq = defaultStatSeq;
    }

    @Override
    public void check() {
        ident.check();

        // ensure that ident is of a primitive type: int, string, char, bool
        TYPE identType = ident.getEvalType();
        if (!((identType instanceof INT) || (identType instanceof STRING) || (identType instanceof CHAR) || (identType instanceof BOOLEAN))) {
            error("Variable " + identType + " is not a primitive type. Only int, string, char, and bool types are allowed.");
            return;
        }

        // set the switchIdentType of each case, and check each case
        for (CaseAST individualCase: cases) {
            individualCase.setSwitchIdentType(identType);
            individualCase.check();
        }

        // check the defaultStatSeq
        defaultStatSeq.check();
    }

    @Override
    public void assemble(FunctionBody body, List<Register> freeRegs) {
        //TO-DO
    }

}
