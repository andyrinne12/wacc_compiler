package front_end.AST.statement;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import back_end.FunctionBody;
import back_end.operands.registers.Register;
import front_end.Visitor;
import front_end.types.*;

public class SwitchAST extends StatementAST {

    private final String identName;
    private final List<CaseAST> cases;
    private final StatementSequenceAST defaultStatSeq;
    
    public SwitchAST(ParserRuleContext ctx, String identName, List<CaseAST> cases, StatementSequenceAST defaultStatSeq) {
        super(ctx);
        this.cases = cases;
        this.identName = identName;
        this.defaultStatSeq = defaultStatSeq;
    }

    @Override
    public void check() {
        identObj = Visitor.ST.lookupAll(identName);
        if (identObj == null) {
            error("Variable " + identName + " on the switch statement has not been previously defined.");
            return;
        } else if (identObj instanceof FUNCTION) {
            error("Function identifier " + identName + " should not be used at the switch statement.");
            return;
        } 
        // ensure that ident is of a primitive type: int, string, char, bool
        else if (!((identObj instanceof INT) || (identObj instanceof STRING) || (identObj instanceof CHAR) || (identObj instanceof BOOLEAN))) {
            error("Variable " + identName + " is not a primitive type. Only int, string, char, and bool types are allowed.");
            return;
        }

        // set the switchIdentType of each case, and check each case
        for (CaseAST individualCase: cases) {
            individualCase.setSwitchIdentType(identObj.getType());
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
