package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * {@code InstrMove} class represents instruction that loads the value from
 * memory or register to another register.
 * <p>
 * This class is used with only one argument which can be register, indirect
 * address or memory location.
 * <p>
 * Example assembler code for this instruction is: &nbsp;&nbsp;&nbsp;&nbsp;
 * {@code move rX, rY} or {@code move rX, [rY+offset]} or
 * {@code move rX, memAddress}
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Instruction
 */
public class InstrMove implements Instruction {

    /** Argument holding information about destination. */
    private InstructionArgument dest;

    /** Argument holding information about source. */
    private InstructionArgument src;

    /**
     * Constructs a new {@code InstrIncrement} object with given arguments.
     * 
     * @param arguments
     *            the arguments of this instruction
     */
    public InstrMove(List<InstructionArgument> arguments) {
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("Expected 2 arguments!");
        }

        if (!arguments.get(0).isRegister()) {
            throw new IllegalArgumentException(
                    "First argument of move instruction can only be register"
                            + " or indirect address!");
        }

        this.dest = arguments.get(0);
        this.src = arguments.get(1);
    }

    /**
     * Executes this instruction on specified computer.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     */
    @Override
    public boolean execute(Computer computer) {
        if (!RegisterUtil.isIndirect((int) dest.getValue())) {
            moveToRegister(computer, dest, src);
        } else {
            moveToIndirectAddress(computer, dest, src);
        }

        return false;
    }

    /**
     * If destination argument represents the register this method is called to
     * move value from source.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     * @param destArg
     *            argument holding information about destination
     * @param srcArg
     *            argument holding information about source
     */
    private static void moveToRegister(
            Computer computer,
            InstructionArgument destArg,
            InstructionArgument srcArg) {
        int dest = RegisterUtil.getRegisterIndex((int) destArg.getValue());
        Object srcValue = getValueOfArgument(computer, srcArg);

        computer.getRegisters().setRegisterValue(dest, srcValue);
    }

    /**
     * If destination argument represents the indirect address this method is
     * called to move value from source.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     * @param destArg
     *            argument holding information about destination
     * @param srcArg
     *            argument holding information about source
     */
    private static void moveToIndirectAddress(
            Computer computer,
            InstructionArgument destArg,
            InstructionArgument srcArg) {
        int dest = RegisterUtil
                .getIndirectAddress(computer, (int) destArg.getValue());
        Object srcValue = getValueOfArgument(computer, srcArg);

        computer.getMemory().setLocation(dest, srcValue);
    }

    /**
     * Returns the value of a argument.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     * @param arg
     *            argument holding information about destination
     * @return the value of a argument
     */
    private static int getValueOfArgument(
            Computer computer,
            InstructionArgument arg) {
        if (arg.isNumber()) {
            return (int) arg.getValue();
        } else if (arg.isRegister()) {
            return (int) RegisterUtil.getValue(computer, (int) arg.getValue());
        } else {
            throw new IllegalArgumentException(
                    "You can't move string literal to register!");
        }
    }
}
