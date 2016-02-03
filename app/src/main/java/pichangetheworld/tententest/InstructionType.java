package pichangetheworld.tententest;

/**
 * Eminent Domain AS
 * Author: pchan
 * Date: 04/02/16
 */
public enum InstructionType {
    PUSH,
    PRINT;

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
