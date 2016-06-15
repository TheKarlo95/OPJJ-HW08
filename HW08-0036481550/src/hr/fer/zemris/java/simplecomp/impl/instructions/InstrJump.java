package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * {@code InstrJump} class represents instruction that sets the new value to
 * program counter.
 * <p>
 * This class is used with only one argument which represent the memory address
 * of the next instruction to be executed.
 * <p>
 * Example assembler code for this instruction is: &nbsp;&nbsp;&nbsp;&nbsp;
 * {@code jump memAddress}
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Instruction
 */
public class InstrJump implements Instruction {

    /** Memory address of a jump */
    private int location;

    /**
     * Constructs a new {@code InstrJump} object with given arguments.
     * 
     * @param arguments
     *            the arguments of this instruction
     */
    public InstrJump(List<InstructionArgument> arguments) {
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Expected 1 arguments!");
        }

        if (!arguments.get(0).isNumber()) {
            throw new IllegalArgumentException("Type mismatch for argument 0!");
        }

        this.location = (Integer) arguments.get(0).getValue();
    }

    /**
     * Executes this instruction on specified computer.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     */
    @Override
    public boolean execute(Computer computer) {
        computer.getRegisters().setProgramCounter(location - 1);

        return false;
    }
}
