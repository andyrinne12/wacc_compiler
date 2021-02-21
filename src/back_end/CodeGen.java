package back_end;

import java.util.ArrayList;
import java.util.List;

import back_end.instructions.Instruction;

public class CodeGen {
    
    public static List<Instruction> dataSection;
    public static List<Instruction> mainSection;
    
    public CodeGen() {
        dataSection = new ArrayList<>();
        mainSection = new ArrayList<>();
    }

    public void writeToFile(String fileName) {
        // TO-DO: writing of the generated ARM code into a textfile.
    }

}
