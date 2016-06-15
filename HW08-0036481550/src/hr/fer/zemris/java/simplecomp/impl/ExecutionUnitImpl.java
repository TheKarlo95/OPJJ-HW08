package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;

/**
 * {@code ExecutionUnitImpl} is a execution unit of a computer. This class is
 * "running" a program.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see ExecutionUnit
 */
public class ExecutionUnitImpl implements ExecutionUnit {

    /**
     * Runs a program written in the computer's memory.
     * 
     * @param computer
     *            the computer on which we execute this instruction
     * @return {@code true} always
     */
    @Override
    public boolean go(Computer computer) {
        computer.getRegisters().setProgramCounter(0);

        boolean exit = false;
        int pc = 0;
        while (true) {
            pc = computer.getRegisters().getProgramCounter();

            Instruction instr = (Instruction) computer
                    .getMemory()
                    .getLocation(pc);

            exit = instr.execute(computer);

            if (exit) {
                break;
            }

            computer.getRegisters().incrementProgramCounter();
        }

        return true;
    }

}
