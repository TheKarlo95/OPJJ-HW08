package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * {@code InstrHalt} class represents instruction that halts the proccessor and
 * prevents it from running any more instructions.
 * <p>
 * This class is used without arguments.
 * <p>
 * Example assembler code for this instruction is: &nbsp;&nbsp;&nbsp;&nbsp;
 * {@code halt}
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Instruction
 */
public class InstrHalt implements Instruction {

    /**
     * Constructs a new {@code InstrHalt} object with given arguments.
     * 
     * @param arguments
     *            the arguments of this instruction
     */
    public InstrHalt(List<InstructionArgument> arguments) {
        if (arguments.size() != 0) {
            throw new IllegalArgumentException("Expected 0 arguments!");
        }
    }

    /**
     * Executes this instruction on specified computer.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     */
    @Override
    public boolean execute(Computer computer) {
        return true;
    }
}