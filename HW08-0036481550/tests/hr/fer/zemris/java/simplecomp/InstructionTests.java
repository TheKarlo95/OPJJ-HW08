package hr.fer.zemris.java.simplecomp;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrCall;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrLoad;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrMove;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrPop;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrPush;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrRet;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Test class for implementations of a {@link Instruction} interface.
 * <p>
 * Tested classes are:
 * <ul>
 * <li>{@link InstrLoad}
 * <li>{@link InstrMove}
 * <li>{@link InstrPush}
 * <li>{@link InstrPop}
 * <li>{@link InstrCall}
 * <li>{@link InstrRet}
 * </ul>
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Instruction
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("javadoc")
public class InstructionTests {

    @Mock
    private Registers regs;

    @Mock
    private Memory mem;

    @Mock
    private Computer comp;

    // Load tests

    Instruction load;

    private void init() {
        when(comp.getMemory()).thenReturn(mem);
        when(comp.getRegisters()).thenReturn(regs);
    }

    @Test
    public void testLoad() {
        init();

        when(mem.getLocation(2)).thenReturn(21);

        load = new InstrLoad(argsList(new Argument(0, 0), new Argument(2, 1)));
        load.execute(comp);

        verify(regs, times(1)).setRegisterValue(0, 21);
        verify(mem, times(1)).getLocation(2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLoadFail_FirstArgNotRegsiter() {
        init();

        load = new InstrLoad(
                argsList(new Argument(0x01000102, 0), new Argument(0, 1)));
        load.execute(comp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLoadFail_SecondArgNotAddress() {
        init();

        load = new InstrLoad(argsList(new Argument(0, 0), new Argument(0, 0)));
        load.execute(comp);
    }

    // Move tests

    Instruction move;

    @Test
    public void testMove_RegisterRegister() {
        init();

        when(regs.getRegisterValue(0)).thenReturn(0);
        when(regs.getRegisterValue(1)).thenReturn(5);

        load = new InstrMove(argsList(new Argument(0, 0), new Argument(1, 0)));
        load.execute(comp);

        verify(regs, times(1)).setRegisterValue(0, 5);
        verify(regs, times(1)).getRegisterValue(1);
    }

    @Ignore
    @Test
    public void testMove_RegisterIndirect() {
        init();

        when(regs.getRegisterValue(2)).thenReturn(0);

        load = new InstrMove(
                argsList(new Argument(0, 0), new Argument(0x01000102, 0)));
        load.execute(comp);

        verify(comp, times(2)).getRegisters();
        verify(regs, times(1)).setRegisterValue(0, 0);
        verify(regs, times(1)).getRegisterValue(1);
    }

    // Push tests

    @Test
    public void testPush() {
        init();

        when(regs.getRegisterValue(Registers.STACK_REGISTER_INDEX))
                .thenReturn(50);
        when(regs.getRegisterValue(1)).thenReturn(0);
        when(regs.getRegisterValue(0)).thenReturn(0);

        Instruction push = new InstrPush(argsList(new Argument(1, 0)));
        push.execute(comp);

        verify(mem, times(1)).setLocation(50, 0);
        verify(regs, times(1))
                .setRegisterValue(Registers.STACK_REGISTER_INDEX, 49);
    }

    // Pop tests

    @Test
    public void testPop() {
        init();

        when(regs.getRegisterValue(Registers.STACK_REGISTER_INDEX))
                .thenReturn(49);
        when(mem.getLocation(50)).thenReturn(5);

        Instruction pop = new InstrPop(argsList(new Argument(1, 0)));
        pop.execute(comp);

        verify(mem, times(1)).getLocation(50);
        verify(regs, times(1)).getRegisterValue(15);
        verify(regs, times(1)).setRegisterValue(1, 5);

    }

    // Call tests

    @Test
    public void testCall() {
        init();

        when(regs.getRegisterValue(Registers.STACK_REGISTER_INDEX))
                .thenReturn(50);
        when(regs.getProgramCounter()).thenReturn(3);
        when(mem.getLocation(31)).thenReturn(2);

        Instruction call = new InstrCall(argsList(new Argument(31, 1)));
        call.execute(comp);

        verify(regs, times(1)).setProgramCounter(31 - 1);
        verify(regs, times(1)).getProgramCounter();

        verify(regs, times(1))
                .setRegisterValue(Registers.STACK_REGISTER_INDEX, 49);
        verify(regs, times(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);

        verify(mem, times(1)).setLocation(50, 3);
    }

    @Test
    public void testReturn() {
        init();

        when(regs.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(100);
        when(mem.getLocation(101)).thenReturn(30);

        Instruction ret = new InstrRet(new ArrayList<>());

        boolean result = ret.execute(comp);

        verify(regs, times(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
        verify(regs, times(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX, 101);

        verify(mem, times(1)).getLocation(101);

        verify(regs, times(1)).setProgramCounter(30);

        assertEquals(false, result);
    }

    @Test
    public void test1() {
        Computer comp = new ComputerImpl(16, 256);
        RegisterUtil.setValue(comp, 0x01000102, 5);
        assertEquals(5, RegisterUtil.getValue(comp, 0x01000102));
    }

    private static List<InstructionArgument> argsList(Argument... args) {
        return Arrays.asList(args);
    }

    private class Argument implements InstructionArgument {

        private Object value;

        private boolean isRegister;
        private boolean isString;
        private boolean isNumber;

        public Argument(Object value, int mode) {
            super();
            this.value = value;

            switch (mode) {
                case 0:
                    isRegister = true;
                    break;
                case 1:
                    isNumber = true;
                    break;
                case 2:
                    isString = true;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public boolean isRegister() {
            return isRegister;
        }

        @Override
        public boolean isString() {
            // TODO Auto-generated method stub
            return isString;
        }

        @Override
        public boolean isNumber() {
            // TODO Auto-generated method stub
            return isNumber;
        }

        @Override
        public Object getValue() {
            return value;
        }

    }
}
