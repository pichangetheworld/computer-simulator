package pichangetheworld.tententest.computer;

import java.util.List;
import java.util.Stack;

import pichangetheworld.tententest.models.Instruction;

/**
 * Tenten Assignment
 * Author: pchan
 * Date: 04/02/16
 */
public class Computer {
    private final Instruction[] instructions;
    private int currentAddress;

    private final Stack<Integer> stack;

    private final StringBuilder output;

    public Computer(int numAddresses) {
        instructions = new Instruction[numAddresses];
        currentAddress = 0;

        // stack
        stack = new Stack<>();

        // output
        output = new StringBuilder();
        output.append("RESULTS:").append(System.getProperty("line.separator"));
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

    public int getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(int currentAddress) {
        this.currentAddress = currentAddress;
    }

    // here we go
    // returns: whether the computer is still active or not
    public boolean executeInstruction() {
        if (currentAddress < 0 || currentAddress >= instructions.length ||
                instructions[currentAddress] == null) {
            return false;
        }

        Instruction i = instructions[currentAddress];

        int res;
        // call action at currentAddress
        switch (i.instruction) {
            case PUSH:
                res = i.argument;
                stack.push(res);

                // increment currentAddress
                currentAddress++;
                break;
            case PRINT:
                res = stack.pop();
                print(res);

                // increment currentAddress
                currentAddress++;
                break;
            case STOP:
                stop();
                return false;
            case RET:
                res = stack.pop();
                currentAddress = res;
                break;
            case CALL:
                res = i.argument;
                currentAddress = res;
                break;
            case MULT:
                int res1 = stack.pop();
                int res2 = stack.pop();
                res = res1 * res2;
                stack.push(res);

                // increment currentAddress
                currentAddress++;
                break;
        }
        return true;
    }

    private void print(int p) {
        output.append(p).append(System.getProperty("line.separator"));
    }

    private void stop() {
        currentAddress = -1;
    }

    public void reset() {
        for (int i = 0; i < instructions.length; ++i) {
            instructions[i] = null;
        }
        currentAddress = 0;

        stack.clear();

        // reset output
        output.setLength(0);
        output.append("RESULTS:").append(System.getProperty("line.separator"));
    }

    public String getOutput() {
        return output.toString();
    }

    public List<Integer> getCurrentStackState() {
        return stack;
    }
}
