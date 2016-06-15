package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * {@code InstrCall} class represents instruction that tests if two given
 * registers hold the equal values.
 * <p>
 * This class is used with two argument and both of them must be registers. If
 * they hold equal values flag is going to be set to {@code true}.
 * <p>
 * Example assembler code for this instruction is: &nbsp;&nbsp;&nbsp;&nbsp;
 * {@code testIfEquals rX, rY}
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Instruction
 */
public class InstrTestEquals implements Instruction {

    /** 1st register descriptor. */
    private int registerDescriptor1;

    /** 2nd register descriptor. */
    private int registerDescriptor2;

    /**
     * Constructs a new {@code InstrTestEquals} object with given arguments.
     * 
     * @param arguments
     *            the arguments of this instruction
     */
    public InstrTestEquals(List<InstructionArgument> arguments) {
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("Expected 3 arguments!");
        }

        for (int i = 0; i < 2; i++) {
            if (!arguments.get(i).isRegister() || RegisterUtil
                    .isIndirect((Integer) arguments.get(i).getValue())) {
                throw new IllegalArgumentException(
                        "Type mismatch for argument " + i + "!");
            }
        }

        this.registerDescriptor1 = (int) arguments.get(0).getValue();
        this.registerDescriptor2 = (int) arguments.get(1).getValue();
    }

    /**
     * Executes this instruction on specified computer.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     */
    @Override
    public boolean execute(Computer computer) {
        Object value1 = RegisterUtil.getValue(computer, registerDescriptor1);
        Object value2 = RegisterUtil.getValue(computer, registerDescriptor2);

        computer.getRegisters().setFlag(value1.equals(value2));

        return false;
    }

}
