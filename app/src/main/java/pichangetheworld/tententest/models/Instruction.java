package pichangetheworld.tententest.models;

/**
 * Tenten Assignment
 * Author: pchan
 * Date: 04/02/16
 */
public class Instruction {
    public InstructionType instruction;
    public int argument;

    public static Instruction createInstance(InstructionType instruction, int arg) {
        Instruction i = new Instruction();
        i.instruction = instruction;
        i.argument = arg;
        return i;
    }

    public String getInstructionString() {
        switch (instruction) {
            case PUSH:
            case CALL:
                return instruction.toString() + " " + argument;
            case PRINT:
            default:
                return instruction.toString();
        }
    }
}
