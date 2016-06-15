package hr.fer.zemris.java.simplecomp.impl;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * {@code MemoryImple} is a class that implements {@link Memory} interface that
 * represents the computer's memory.
 * <p>
 * Unlike traditional computer memory that is byte-oriented, our memory can
 * store one arbitrarily large object at each location. In this implementation
 * only {@code Integer} and {@code String} objects are allowed. Empty memory at
 * all locations have the value {@code null}.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Memory
 */
public class MemoryImpl implements Memory {

    /**
     * Size of the memory(number of objects that can be stored using this
     * object.
     */
    private int size;

    /**
     * Map for storing objects.
     */
    private Map<Integer, Object> memory;

    /**
     * Constructs a new {@code MemoryImpl} object with specified size.
     * 
     * @param size
     *            size of the memory(number of objects that can be stored using
     *            this object.
     */
    public MemoryImpl(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException(
                    String.format(
                            "Size of memory must be greater than 0. You"
                                    + " provided %d.",
                            size));
        }

        this.size = size;

        this.memory = new HashMap<>(Math.max(32, this.size / 8));
    }

    /**
     * Sets the value to the specified location.
     * 
     * @param location
     *            the memory address
     * @param value
     *            the object that needs to be saved to a specified address (can
     *            be {@code null})
     * @throws IndexOutOfBoundsException
     *             if memory address doesn't exist (less than 0 or greater than
     *             last existing memory location)
     */
    @Override
    public void setLocation(int location, Object value) {
        checkLocationInBounds(location);

        memory.put(location, value);
    }

    /**
     * Returns the value from the specified location.
     * 
     * @param location
     *            the memory address
     * @return the object from specified address (can be {@code null})
     * @throws IndexOutOfBoundsException
     *             if memory address doesn't exist (less than 0 or greater than
     *             last existing memory location)
     */
    @Override
    public Object getLocation(int location) {
        checkLocationInBounds(location);

        return memory.get(location);
    }

    /**
     * Checks if specified memory address is in bounds of this memory.
     * 
     * @param location
     *            the memory address
     * @throws IndexOutOfBoundsException
     *             if memory address doesn't exists (isn't in bounds of this
     *             memory)
     */
    private void checkLocationInBounds(int location) {
        if (location < 0 || location >= this.size)
            throw new IndexOutOfBoundsException(
                    String.format(
                            "You tried to insert value at memory location '%d'. Valid"
                                    + " locations are between '0'(inclusive) and '%d'"
                                    + " (exclusive)",
                            location,
                            this.size));
    }
}
