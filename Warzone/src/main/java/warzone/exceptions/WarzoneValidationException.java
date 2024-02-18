package main.java.warzone.exceptions;

/**
 * This exception is thrown when there is a validation error in the program.
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 *
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
