package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * {@code InstrLoad} class represents instruction that loads the value from
 * memory to register.
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
public class InstrLoad implements Instruction {

    /** Register descriptor */
    private int descriptor;

    /** Memory location of value */
    private int valueLocation;

    /**
     * Constructs a new {@code InstrLoad} object with given arguments.
     * 
     * @param arguments
     *            the arguments of this instruction
     */
    public InstrLoad(List<InstructionArgument> arguments) {
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("Expected 2 arguments!");
        }

        if (!arguments.get(0).isRegister() || RegisterUtil
                .isIndirect((Integer) arguments.get(0).getValue())) {
            throw new IllegalArgumentException("Type mismatch for argument 0!");
        }

        if (!arguments.get(1).isNumber()) {
            throw new IllegalArgumentException("Type mismatch for argument 1!");
        }

        this.descriptor = RegisterUtil
                .getRegisterIndex((Integer) arguments.get(0).getValue());
        this.valueLocation = (Integer) arguments.get(1).getValue();
    }

    /**
     * Executes this instruction on specified computer.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     */
    @Override
    public boolean execute(Computer computer) {
        RegisterUtil.setValue(
                computer,
                descriptor,
                computer.getMemory().getLocation(valueLocation));

        return false;
    }

}
