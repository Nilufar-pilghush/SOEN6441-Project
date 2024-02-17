package main.java.com.warzone.exceptions;

/**
 * This exception is thrown when there is a validation error in the program.
 *
 * @author Ali Sayed Salehi
 */
public class WarzoneValidationException extends WarzoneBaseException {

    /**
     * Constructor for the exception
     */
    public WarzoneValidationException() {
        super();
    }

    /**
     * Constructor for the exception
     *
     * @param p_ExceptionMessage is the exception message
     */
    public WarzoneValidationException(String p_ExceptionMessage) {
        super(p_ExceptionMessage);
    }
}
