package back_end.operands.registers;

import back_end.operands.Operand;

public class Register implements Operand {
    
    private int index; // used to represent a register number.

    public static Register SP = new Register(13); // stack pointer
    public static Register LR = new Register(14); // link register
    public static Register PC = new Register(15); // program counter

    public Register(int index) {
        this.index = index;
    }

}
