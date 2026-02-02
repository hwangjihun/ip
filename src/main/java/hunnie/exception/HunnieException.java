package hunnie.exception;

/**
 * Represents an exception specific to the Hunnie application.
 * This exception is thrown when errors occur during task operations or command execution.
 */
public class HunnieException extends Exception {

    /**
     * Creates a new Hunnie exception with the specified error message.
     *
     * @param message Error message describing what went wrong.
     */
    public HunnieException(String message) {
        super(message);
    }
}
