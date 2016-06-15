package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * {@code InstrPush} class represents instruction that pushes values to stack.
 * <p>
 * This class is used with only one argument which represent the register in
 * which the pushed value is stored.
 * <p>
 * Example assembler code for this instruction is: &nbsp;&nbsp;&nbsp;&nbsp;
 * {@code push rX}
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Instruction
 */
public class InstrPush implements Instruction {

    /** Argument of this instruction */
    private InstructionArgument arg;

    /**
     * Constructs a new {@code InstrPop} object with given arguments.
     * 
     * @param arguments
     *            the arguments of this instruction
     */
    public InstrPush(List<InstructionArgument> arguments) {
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Expected 1 arguments!");
        }

        if (!arguments.get(0).isRegister() || RegisterUtil
                .isIndirect((Integer) arguments.get(0).getValue())) {
            throw new IllegalArgumentException("Type mismatch for argument 0!");
        }

        this.arg = arguments.get(0);
    }

    /**
     * Executes this instruction on specified computer.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     */
    @Override
    public boolean execute(Computer computer) {
        StackUtil.push(computer, arg);

        return false;
    }

}
