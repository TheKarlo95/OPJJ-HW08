package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * {@code InstrDecrement} class represents decrement instruction.
 * <p>
 * This class is used with only one argument and that argument must be a
 * register descriptor.
 * <p>
 * Example assembler code for this instruction is: &nbsp;&nbsp;&nbsp;&nbsp;
 * {@code decrement rX}
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Instruction
 */
public class InstrDecrement extends AbstractUnaryInstr {

    /**
     * Constructs a new {@code InstrDecrement} object with given arguments.
     * 
     * @param arguments
     *            the arguments of this instruction
     */
    public InstrDecrement(List<InstructionArgument> arguments) {
        super(arguments, v -> v - 1);
    }

}
