package back_end.instructions.logical;

import back_end.instructions.Condition;
import back_end.instructions.arithmetic.ArithmeticInstr;
import back_end.operands.Operand;

public class EOR extends ArithmeticInstr {

    private final Operand operand2;

    public EOR(Condition cond, boolean set, Operand rd, Operand rn, Operand operand2) {
        super("EOR", cond, set, rd, rn);
        this.operand2 = operand2;
    }

    public EOR(Operand rd, Operand rn, Operand operand2) {
        this(Condition.NONE, false, rd, rn, operand2);
    }

    @Override
    public String instrPrint() {
        return String.format("%s%s%s %s, %s, %s", name, cond, setMark, rd, rn, operand2);
    }
    
}
