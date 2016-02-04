package pichangetheworld.tententest;

/**
 * Tenten Assignment
 * Author: pchan
 * Date: 04/02/16
 */
public class Computer {
    private Instruction[] instructions;
    private int currentAddress;

    public Computer(int numAddresses) {
        instructions = new Instruction[numAddresses];
        currentAddress = 0;
    }

    public Instruction[] getInstructions() {
        return instructions;
    }

    public void addInstruction(Instruction instruction) {
        if (currentAddress < instructions.length) {
            instructions[currentAddress] = instruction;
            currentAddress++;
        }
    }

    public void setCurrentAddress(int currentAddress) {
        this.currentAddress = currentAddress;
    }

    // here we go
    public void executeInstruction() {
        // call action at currentAddress
        // increment currentAddress
    }
}
