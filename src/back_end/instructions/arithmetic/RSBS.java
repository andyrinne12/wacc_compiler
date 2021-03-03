package back_end.instructions.arithmetic;

import back_end.operands.Operand;
import back_end.operands.immediate.ImmInt;

// In our compiler, RSBS is only used to negate an integer.

public class RSBS extends ArithmeticInstr {
    
    private final Operand operand2;

    public RSBS(boolean set, Operand rd, Operand rn) {
        super("RSBS", set, rd, rn);
        this.operand2 = new ImmInt(0);
    }

    @Override
    public String instrPrint() {
        return String.format("%s%s%s %s, %s, %s", name, cond, setMark, rd, rn, operand2);
    }

}
