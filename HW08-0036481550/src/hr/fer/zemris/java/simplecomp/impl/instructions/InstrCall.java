package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * {@code InstrCall} class represents instruction that calls other sub-programs.
 * <p>
 * This class is used with only one argument which represent the memory address
 * of the first line of subroutine which is called. Current address is stored on
 * stack.
 * <p>
 * Example assembler code for this instruction is: &nbsp;&nbsp;&nbsp;&nbsp;
 * {@code call memAddress}
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Instruction
 */
public class InstrCall implements Instruction {

    /** New memory address whcih will be put to program counter */
    private int newProgramCounterAddress;

    /**
     * Constructs a new {@code InstrCall} object with given arguments.
     * 
     * @param arguments
     *            the arguments of this instruction
     */
    public InstrCall(List<InstructionArgument> arguments) {
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Expected 1 arguments!");
        }

        if (!arguments.get(0).isNumber()) {
            throw new IllegalArgumentException("Type mismatch for argument 0!");
        }

        this.newProgramCounterAddress = (int) arguments.get(0).getValue();
    }

    /**
     * Executes this instruction on specified computer.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     */
    @Override
    public boolean execute(Computer computer) {
        StackUtil.call(computer, newProgramCounterAddress);

        return false;
    }

}
