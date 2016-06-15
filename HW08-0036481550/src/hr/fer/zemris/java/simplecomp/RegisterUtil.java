package hr.fer.zemris.java.simplecomp;

import hr.fer.zemris.java.simplecomp.models.Computer;

/**
 * {@code RegisterUtil} is class that provides some static methods used when
 * interacting with register descriptors.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public class RegisterUtil {

    /** Register index mask */
    private static final int REGISTER_INDEX_MASK = 0x000000FF;

    /** Register indirect mask */
    private static final int REGISTER_INDIRECT_MASK = 0x01000000;

    /** Register offset mask */
    private static final int REGISTER_OFFSET_MASK = 0x00FFFF00;

    /**
     * Extracts register index(bits 0 to 7) from register descriptor.
     * 
     * @param registerDescriptor
     *            integer value containing information about register index, if
     *            addressing is indirect and register offset.
     * @return register index(bits 0 to 7)
     */
    public static int getRegisterIndex(int registerDescriptor) {
        return registerDescriptor & REGISTER_INDEX_MASK;
    }

    /**
     * Extracts register indirect flag(bit 24) from register descriptor.
     * 
     * @param registerDescriptor
     *            integer value containing information about register index, if
     *            addressing is indirect and register offset.
     * @return {@code true} if register indirect flag(bit 24) is 1;
     *         {@code false} otherwise
     */
    public static boolean isIndirect(int registerDescriptor) {
        return (registerDescriptor & REGISTER_INDIRECT_MASK) != 0;
    }

    /**
     * Extracts register offset(bits 8 to 23) from register descriptor.
     * 
     * @param registerDescriptor
     *            integer value containing information about register index, if
     *            addressing is indirect and register offset.
     * @return register offset(bits 8 to 23)
     */
    public static int getRegisterOffset(int registerDescriptor) {
        int output = (registerDescriptor & REGISTER_OFFSET_MASK) >>> 8;

        return (output >>> 15) != 1 ? output : output + 0xFFFF0000;
    }

    /**
     * Returns the indirect address from a register descriptor.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     * @param registerDescriptor
     *            register descriptor
     * @return the memory address
     */
    public static int getIndirectAddress(
            Computer computer,
            int registerDescriptor) {
        int index = getRegisterIndex(registerDescriptor);
        int baseAddress = (int) getValue(computer, index);

        return baseAddress + getRegisterOffset(registerDescriptor);
    }

    /**
     * Returns the value of a register.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     * @param registerDescriptor
     *            register descriptor
     * @return the value of a register
     */
    public static Object getValue(Computer computer, int registerDescriptor) {
        Object value = null;
        if (!RegisterUtil.isIndirect(registerDescriptor)) {
            int index = RegisterUtil.getRegisterIndex(registerDescriptor);

            value = computer.getRegisters().getRegisterValue(index);

            if (value == null) { // if register is non-existent
                computer.getRegisters().setRegisterValue(index, 0);
                value = computer.getRegisters().getRegisterValue(index);
            }
        } else {
            int location = RegisterUtil
                    .getIndirectAddress(computer, registerDescriptor);

            value = computer.getMemory().getLocation(location);

            if (value == null) { // if memory location is non-existent
                computer.getMemory().setLocation(location, 0);
                value = computer.getMemory().getLocation(location);
            }
        }

        return value;
    }

    /**
     * Sets the new value of a register.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     * @param registerDescriptor
     *            register descriptor
     * @param value
     *            new value of a register
     */
    public static void setValue(
            Computer computer,
            int registerDescriptor,
            Object value) {
        if (!RegisterUtil.isIndirect(registerDescriptor)) {
            int index = RegisterUtil.getRegisterIndex(registerDescriptor);

            computer.getRegisters().setRegisterValue(index, value);
        } else {
            int location = RegisterUtil
                    .getIndirectAddress(computer, registerDescriptor);

            computer.getMemory().setLocation(location, value);
        }
    }
}
