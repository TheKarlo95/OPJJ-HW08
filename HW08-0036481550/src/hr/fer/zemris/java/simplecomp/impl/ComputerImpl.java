package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * {@code ComputerImpl} is a class representing simulation of a computer.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Computer
 */
public class ComputerImpl implements Computer {

    /**
     * Memory of this computer.
     */
    private Memory memory;

    /**
     * Registers of this computer.
     */
    private Registers registers;

    /**
     * Constructs a new {@code MemoryImpl} object with specified size.
     * 
     * @param size
     *            size of the memory used to store various objects
     * @param regsLen
     *            number of the registers
     */
    public ComputerImpl(int size, int regsLen) {
        this.memory = new MemoryImpl(size);
        this.registers = new RegistersImpl(regsLen);
    }

    /**
     * Returns the registers of this computer.
     * 
     * @return the registers of this computer
     */
    @Override
    public Registers getRegisters() {
        return registers;
    }

    /**
     * Returns the memory of this computer
     * 
     * @return the memory of this computer
     */
    @Override
    public Memory getMemory() {
        return memory;
    }

}
