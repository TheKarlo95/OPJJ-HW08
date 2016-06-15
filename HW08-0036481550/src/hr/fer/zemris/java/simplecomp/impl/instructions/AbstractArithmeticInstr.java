package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import java.util.function.BinaryOperator;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * {@code AbstractArithmeticInstr} abstract class represents arithmetic
 * operations.
 * <p>
 * This class implements <i>{@link Instruction}}</i> and is used to implement
 * instructions for doing arithmetic operations with two operands.
 * <p>
 * This class is used with three arguments which represent three registers.
 * First one is used for the result, second and third one is used for operands.
 * 
 * @author Marko Čupić
 * @author Karlo Vrbić
 * @version 1.0
 * @see Instruction
 * @see InstrAdd
 * @see InstrMul
 */
public abstract class AbstractArithmeticInstr implements Instruction {

    /** Register descriptor for the result register */
    private int descriptor1;

    /** Register descriptor for the first operand */
    private int descriptor2;

    /** Register descriptor for the second operand */
    private int descriptor3;

    /**
     * Arithmetic operation that is going to be performed on the two operands
     */
    private BinaryOperator<Integer> operation;

    /**
     * Constructs a new {@code AbstractArithmeticInstr} object with given
     * arguments and operation.
     * 
     * @param arguments
     *            the arguments of this instruction
     * @param operation
     *            operation of this instruction
     */
    AbstractArithmeticInstr(List<InstructionArgument> arguments,
            BinaryOperator<Integer> operation) {
        if (arguments.size() != 3) {
            throw new IllegalArgumentException("Expected 3 arguments!");
        }

        for (int i = 0; i < 3; i++) {
            if (!arguments.get(i).isRegister() || RegisterUtil
                    .isIndirect((Integer) arguments.get(i).getValue())) {
                throw new IllegalArgumentException(
                        "Type mismatch for argument " + i + "!");
            }
        }

        this.descriptor1 = (Integer) arguments.get(0).getValue();
        this.descriptor2 = (Integer) arguments.get(1).getValue();
        this.descriptor3 = (Integer) arguments.get(2).getValue();
        this.operation = operation;
    }

    /**
     * Executes this instruction on specified computer.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     */
    @Override
    public boolean execute(Computer computer) {
        Object val1 = RegisterUtil.getValue(computer, descriptor2);

        Object val2 = RegisterUtil.getValue(computer, descriptor3);

        computer.getRegisters().setRegisterValue(
                RegisterUtil.getRegisterIndex(descriptor1),
                operation.apply((Integer) val1, (Integer) val2));

        return false;
    }
}
