package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * {@code InstrEcho} class represents instruction that prints out values from
 * registers or memory.
 * <p>
 * This class is used with two arguments. First one is register descriptor and
 * second one is memory locations from which we load the new value.
 * <p>
 * Example assembler code for this instruction is: &nbsp;&nbsp;&nbsp;&nbsp;
 * {@code load rX, memAddress}
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Instruction
 */
public class InstrEcho implements Instruction {

    /** Register descriptor */
    private int descriptor;

    /**
     * Constructs a new {@code InstrEcho} object with given arguments.
     * 
     * @param arguments
     *            the arguments of this instruction
     */
    public InstrEcho(List<InstructionArgument> arguments) {
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Expected 1 arguments!");
        }

        if (!arguments.get(0).isRegister()) {
            throw new IllegalArgumentException("Type mismatch for argument 0!");
        }

        int registerDescriptor = (Integer) arguments.get(0).getValue();

        this.descriptor = registerDescriptor;
    }

    /**
     * Executes this instruction on specified computer.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     */
    @Override
    public boolean execute(Computer computer) {
        Object value = RegisterUtil.getValue(computer, descriptor);

        System.out.print(value);

        return false;
    }

}
