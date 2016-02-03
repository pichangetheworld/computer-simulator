package pichangetheworld.tententest;

/**
 * Tenten Assignment
 * Author: pchan
 * Date: 04/02/16
 */
public class Instruction {
    private InstructionType instruction;
    private int argument;

    public static Instruction createInstance(InstructionType instruction, int arg) {
        Instruction i = new Instruction();
        i.instruction = instruction;
        i.argument = arg;
        return i;
    }

    public String getInstructionString() {
        switch (instruction) {
            case PUSH:
                return instruction.toString() + " " + argument;
            case PRINT:
            default:
                return instruction.toString();
        }
    }
}
