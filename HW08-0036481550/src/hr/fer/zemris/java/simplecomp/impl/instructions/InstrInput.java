package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * {@code InstrInput} class represents instruction that asks user for input and
 * stores that input to specified memory location.
 * <p>
 * This class is used with only one argument and that argument must be a memory
 * location.
 * <p>
 * Example assembler code for this instruction is: &nbsp;&nbsp;&nbsp;&nbsp;
 * {@code input memAddress}
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Instruction
 */
public class InstrInput implements Instruction {

    /** Memory location where input is going to be stored. */
    private InstructionArgument inputLocation;

    /**
     * Constructs a new {@code InstrInput} object with given arguments.
     * 
     * @param arguments
     *            the arguments of this instruction
     */
    public InstrInput(List<InstructionArgument> arguments) {
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Expected 1 arguments!");
        }

        if (!arguments.get(0).isNumber()) {
            throw new IllegalArgumentException("Type mismatch for argument 0!");
        }

        this.inputLocation = arguments.get(0);
    }

    /**
     * Executes this instruction on specified computer.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     */
    @Override
    public boolean execute(Computer computer) {
        String line = InputStreamUtil.readLine();

        Integer number = null;
        try {
            number = Integer.parseInt(line);
        } catch (NumberFormatException nfe) {
            computer.getRegisters().setFlag(false);
            return false;
        }

        computer.getRegisters().setFlag(true);

        computer.getMemory().setLocation((int) inputLocation.getValue(), number);

        return false;
    }

}