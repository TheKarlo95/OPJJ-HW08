package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * {@code InstrMul} class represents multiplication instruction.
 * <p>
 * This class is used with three arguments which represent three registers.
 * First one is used for the result, second and third one is used for operands.
 * Result and the first operator must be register but second operand can also be
 * indirect address.
 * <p>
 * Example assembler code for this instruction is: &nbsp;&nbsp;&nbsp;&nbsp;
 * {@code mul rX, rY, rZ}
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Instruction
 * @see AbstractArithmeticInstr
 */
public class InstrMul extends AbstractArithmeticInstr {

    /**
     * Constructs a new {@code InstrAdd} object with given arguments.
     * 
     * @param arguments
     *            the arguments of this instruction
     */
    public InstrMul(List<InstructionArgument> arguments) {
        super(arguments, (v1, v2) -> v1 * v2);
    }

}
