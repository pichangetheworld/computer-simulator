package pichangetheworld.tententest;

import java.util.Stack;

/**
 * Tenten Assignment
 * Author: pchan
 * Date: 04/02/16
 */
public class Computer {
    private Instruction[] instructions;
    private int currentAddress;

    private Stack<Integer> stack;

    private StringBuilder output;

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
    public void executeInstruction() {
        if (currentAddress < 0 || currentAddress >= instructions.length ||
                instructions[currentAddress] == null) {
            currentAddress = 0;
        }

        Instruction i = instructions[currentAddress];

        int res;
        // call action at currentAddress
        switch (i.instruction) {
            case PUSH:
                res = i.argument;
                stack.push(res);
                break;
            case PRINT:
                res = stack.pop();
                print(res);
                break;
            case STOP:
                stop();
                break;
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
                break;
        }

        // increment currentAddress
        currentAddress++;
    }

    private void print(int p) {
        output.append(p).append(System.getProperty("line.separator"));
    }

    private void stop() {
        currentAddress = -1;
    }

    public String getOutput() {
        return output.toString();
    }
}
