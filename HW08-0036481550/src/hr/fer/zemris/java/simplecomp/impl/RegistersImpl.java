package hr.fer.zemris.java.simplecomp.impl;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * {@code RegistersImpl} is a class that implements {@link Registers} interface
 * that represents the computer's registers.
 * <p>
 * There are general-purpose registers (GPR), program counter (PC) and flag
 * register.
 * <p>
 * General-purpose registers are used for storing objects and are used with
 * {@link #getRegisterValue(int)} and {@link #setRegisterValue(int, Object)}.
 * <br>
 * Program counter is used for storing address of the next instruction and is
 * used with methods {@link #getProgramCounter()},
 * {@link #incrementProgramCounter()} and {@link #setProgramCounter(int)}. <br>
 * Flag register can only store one {@code boolean} value and is used with
 * methods {@link #getFlag()} and {@link #setFlag(boolean)}.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Registers
 */
public class RegistersImpl implements Registers {

    /**
     * Number of registers.
     */
    private int regsLen;

    /**
     * Flag register.
     */
    private boolean flag;

    /**
     * Program counter register.
     */
    private int programCounter;

    /**
     * General-purpose registers.
     */
    private Map<Integer, Object> registers;

    /**
     * Constructs a new {@code RegistersImpl} object with specified number of
     * general-purpose registers.
     * 
     * @param regsLen
     *            number of registers
     */
    public RegistersImpl(int regsLen) {
        if (regsLen <= Registers.STACK_REGISTER_INDEX) {
            throw new IllegalArgumentException(
                    String.format(
                            "Number of registers must be greater than 15. You"
                                    + " provided %d.",
                            regsLen));
        }

        this.regsLen = regsLen;
        this.registers = new HashMap<>(Math.max(8, this.regsLen / 8));
    }

    /**
     * Returns the value of the general-purpose register with specified
     * {@code index}.
     * 
     * @param index
     *            register index (starting from 0)
     * @return the value of the register
     * @throws IndexOutOfBoundsException
     *             if {@code index} is less than 0 (exclusive) or greater than
     *             number of registers (inclusive)
     */
    @Override
    public Object getRegisterValue(int index) {
        checkLocationInBounds(index);

        return registers.get(index);
    }

    /**
     * Sets the value of the General-purpose register with specified
     * {@code index}.
     * 
     * @param index
     *            register index (starting from 0)
     * @param value
     *            new value of the register
     * @throws IndexOutOfBoundsException
     *             if {@code index} is less than 0 (exclusive) or greater than
     *             number of registers (inclusive)
     */
    @Override
    public void setRegisterValue(int index, Object value) {
        checkLocationInBounds(index);

        registers.put(index, value);
    }

    /**
     * Returns the value of the program counter
     * 
     * @return the value of the program counter
     */
    @Override
    public int getProgramCounter() {
        return this.programCounter;
    }

    /**
     * Sets the value of the program counter.
     * 
     * @param value
     *            new value of the program counter
     * @throws IllegalArgumentException
     *             if value provided is less than 0
     */
    @Override
    public void setProgramCounter(int value) {
        if (value < 0) {
            throw new IllegalArgumentException(
                    String.format(
                            "Value of program counter can't be set to less than"
                                    + " 0. You provided %d.",
                            value));
        }

        this.programCounter = value;
    }

    /**
     * Increments the value of the program counter.
     */
    @Override
    public void incrementProgramCounter() {
        programCounter++;
    }

    /**
     * Returns the value of the flag register.
     * 
     * @return the value of the flag register
     */
    @Override
    public boolean getFlag() {
        return this.flag;
    }

    /**
     * Sets the new value of the flag register.
     * 
     * @param value
     *            new value of the flag register
     */
    @Override
    public void setFlag(boolean value) {
        flag = value;
    }

    /**
     * Checks if specified general-purpose register index exists.
     * 
     * @param index
     *            the memory address
     * @throws IndexOutOfBoundsException
     *             if general-purpose register index doesn't exists
     */
    private void checkLocationInBounds(int index) {
        if (index < 0 && index >= this.regsLen)
            throw new IndexOutOfBoundsException(
                    String.format(
                            "You tried to insert value at memory location '%d'. Valid"
                                    + " locations are between '0'(inclusive) and '%d'"
                                    + " (exclusive)",
                            index,
                            this.regsLen));
    }
}
