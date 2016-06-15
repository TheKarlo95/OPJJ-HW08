package hr.fer.zemris.java.simplecomp;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.simplecomp.impl.instructions.InputStreamUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

/**
 * {@code Simulator} simulates the compilation and running of a program.
 * <p>
 * If pat to source of a program isn't specified user will be prompted to enter
 * it during runtime.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public class Simulator {

    /**
     * Starting point of a program.
     * 
     * @param args
     *            Command-line argument
     */
    public static void main(String[] args) {

        Path path = null;

        if (args.length == 1) {
            path = Paths.get(args[0]);
        } else if (args.length == 0) {
            path = Paths.get(getArgument());
        } else {
            System.err.printf(
                    "You should provide 1 or 0 arguments! You provided %d.",
                    args.length);
            System.exit(1);
        }

        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            System.err.printf(
                    "Path '%s' doesn't exists or doesn't point to a file!",
                    path.toString());
            System.exit(2);
        }

        Computer comp = new ComputerImpl(256, 16);

        InstructionCreator creator = new InstructionCreatorImpl(
                "hr.fer.zemris.java.simplecomp.impl.instructions");

        try {
            ProgramParser.parse(path.toString(), comp, creator);
        } catch (Exception e) {
            System.err.printf(
                    "Unknown exception occured while parsing a file %s!%n",
                    path.getFileName());
            e.printStackTrace();
            System.exit(2);
        }

        ExecutionUnit exec = new ExecutionUnitImpl();

        exec.go(comp);
    }

    /**
     * Gets user input and returns it as string.
     * 
     * @return the string user input
     */
    private static String getArgument() {
        System.out.println("Please provide path to assembler program: ");
        String arg = null;

        arg = InputStreamUtil.readLine();

        return arg;
    }

}
