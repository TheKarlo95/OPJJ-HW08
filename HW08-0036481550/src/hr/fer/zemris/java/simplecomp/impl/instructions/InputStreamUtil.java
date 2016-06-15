package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * {@code InputStreamUtil} is a class used to read from standard input.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public class InputStreamUtil {

    /** Reader of standarad input */
    private static final BufferedReader br = new BufferedReader(
            new InputStreamReader(new BufferedInputStream(System.in)));

    /**
     * Returns the next line.
     * 
     * @return the next line
     */
    public static String readLine() {
        String line = null;

        try {
            line = br.readLine();
        } catch (IOException e) {
            System.err.println();
            System.exit(-3);
        }

        return line;
    }

    /**
     * Closes the stream and releases any system resources associated with it.
     */
    public static void close() {
        try {
            br.close();
        } catch (IOException ignorable) {
        }
    }

}
