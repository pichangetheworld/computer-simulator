package pichangetheworld.tententest;

/**
 * Tenten Assignment
 * Author: pchan
 * Date: 04/02/16
 */
public enum InstructionType {
    PUSH,
    PRINT,
    STOP,
    RET,
    CALL,
    MULT;

    @Override
    public String toString() {
        switch (this) {
            case PUSH:
                return "PUSH";
            case PRINT:
                return "PRINT";
            default:
                return "Unknown Instruction";
        }
    }
}
