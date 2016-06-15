package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * {@code StackUtil} is a class used to help other classes with common stack
 * methods such as pop, push...
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public class StackUtil {

    /** Index of the stack register */
    private static final int STACK_INDEX = Registers.STACK_REGISTER_INDEX;

    /**
     * Pushes given value on the stack.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     * @param arg
     *            argument holding value that needs to be pushed
     */
    public static void push(Computer computer, InstructionArgument arg) {
        int address = (int) computer
                .getRegisters()
                .getRegisterValue(STACK_INDEX);
        Object value = RegisterUtil.getValue(computer, (int) arg.getValue());

        computer.getMemory().setLocation(address, value); // r15 <- rx

        computer.getRegisters().setRegisterValue(STACK_INDEX, --address);
    }

    /**
     * Pushes the program counter on the stack.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     */
    private static void push(Computer computer) {
        int address = (int) computer
                .getRegisters()
                .getRegisterValue(STACK_INDEX);
        Object value = computer.getRegisters().getProgramCounter();

        computer.getMemory().setLocation(address, value);

        computer.getRegisters().setRegisterValue(STACK_INDEX, --address);
    }

    /**
     * Removes and sets given register to returned value.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     * @param arg
     *            argument holding information about register that needs to
     *            store poped value
     */
    public static void pop(Computer computer, InstructionArgument arg) {
        int address = (int) computer
                .getRegisters()
                .getRegisterValue(STACK_INDEX);

        computer.getRegisters().setRegisterValue(STACK_INDEX, ++address);

        Object value = computer.getMemory().getLocation(address);

        RegisterUtil.setValue(computer, (int) arg.getValue(), value);
    }

    /**
     * Calls the subroutine with starting address specified by method parameter
     * and pushes the current program counter to stack.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     * @param newPC
     *            the new program counter address
     */
    public static void call(Computer computer, int newPC) {
        push(computer);

        computer.getRegisters().setProgramCounter(newPC - 1);
    }

    /**
     * Returns from the subroutine and returns the "old" program counter from
     * the stack.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     */
    public static void ret(Computer computer) {
        int address = (int) computer
                .getRegisters()
                .getRegisterValue(STACK_INDEX);

        computer.getRegisters().setRegisterValue(STACK_INDEX, ++address);

        Object value = computer.getMemory().getLocation(address);

        computer.getRegisters().setProgramCounter((int) value);
    }
}
