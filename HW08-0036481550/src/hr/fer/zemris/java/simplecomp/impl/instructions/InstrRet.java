package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * {@code InstrRet} class represents instruction that returns from subroutines.
 * <p>
 * This class is used with only one argument which represent the memory address
 * of the line where subroutine was called.
 * <p>
 * Example assembler code for this instruction is: &nbsp;&nbsp;&nbsp;&nbsp;
 * {@code ret rX}
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Instruction
 */
public class InstrRet implements Instruction {

    /**
     * Constructs a new {@code InstrRet} object with given arguments.
     * 
     * @param arguments
     *            the arguments of this instruction
     */
    public InstrRet(List<InstructionArgument> arguments) {
        if (arguments.size() != 0) {
            throw new IllegalArgumentException("Expected 1 arguments!");
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
        StackUtil.ret(computer);

        return false;
    }

}
