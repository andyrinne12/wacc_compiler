package front_end.AST.statement;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import back_end.CodeGen;
import back_end.FunctionBody;
import back_end.instructions.Condition;
import back_end.instructions.Label;
import back_end.instructions.arithmetic.CMP;
import back_end.instructions.branch.B;
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
        ident.assemble(body, freeRegs);
        Register identReg = freeRegs.get(0); // register that holds the evaluated value of ident, 
        // which is the variable that we're trying to compare with all the case values.

        String endLabel = CodeGen.getLabel(); // used by break statements, if any.

        List<Register> freeRegs1 = freeRegs.subList(1, freeRegs.size());

        // For each case, we generate a label, then generate ARM code for comparing the ident value with the case's value.
        for (CaseAST individualCase: cases) {
            String caseLabel = CodeGen.getLabel();
            individualCase.setCaseLabel(caseLabel);
            individualCase.setEndLabel(endLabel);

            individualCase.getCaseExpr().assemble(body, freeRegs1);
            Register caseRegister = freeRegs1.get(0); // register that holds the evaluated value of each case.

            body.addInstr(new CMP(identReg, caseRegister));
            body.addInstr(new B(Condition.EQ, caseLabel));
        }
        
        // assemble each case
        for (CaseAST individualCase: cases) {
            individualCase.assemble(body, freeRegs);
        }

        // default
        body.addInstr(new Label(CodeGen.getLabel()));
        defaultStatSeq.assemble(body, freeRegs);

        body.addInstr(new Label(endLabel));
    }

}
