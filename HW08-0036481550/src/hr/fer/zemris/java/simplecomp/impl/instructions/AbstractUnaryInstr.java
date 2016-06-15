package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import java.util.function.UnaryOperator;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * {@code AbstractUnaryInstr} abstract class represents operations with only one
 * operand.
 * <p>
 * This class implements <i>{@link Instruction}}</i> and is used to implement
 * instructions for doing arithmetic operations with only one operand.
 * <p>
 * This class is used with three arguments which represent three registers.
 * First one is used for the result, second and third one is used for operands.
 * 
 * @author Marko Čupić
 * @author Karlo Vrbić
 * @version 1.0
 * @see Instruction
 * @see InstrIncrement
 * @see InstrDecrement
 */
public abstract class AbstractUnaryInstr implements Instruction {

    /** Register descriptor for the result and operand register */
    private int descriptor;

    /** Arithmetic operation that is going to be performed on the operand */
    private UnaryOperator<Integer> operation;

    /**
     * Constructs a new {@code AbstractUnaryInstr} object with given arguments
     * and operation.
     * 
     * @param arguments
     *            the arguments of this instruction
     * @param operation
     *            operation of this instruction
     */
    AbstractUnaryInstr(List<InstructionArgument> arguments,
            UnaryOperator<Integer> operation) {
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Expected 1 arguments!");
        }

        if (!arguments.get(0).isRegister() || RegisterUtil
                .isIndirect((Integer) arguments.get(0).getValue())) {
            throw new IllegalArgumentException("Type mismatch for argument 0!");
        }

        this.descriptor = (Integer) arguments.get(0).getValue();

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
        Object val = RegisterUtil.getValue(computer, descriptor);

        RegisterUtil.setValue(
                computer,
                RegisterUtil.getRegisterIndex(descriptor),
                operation.apply((Integer) val));

        return false;
    }
}
