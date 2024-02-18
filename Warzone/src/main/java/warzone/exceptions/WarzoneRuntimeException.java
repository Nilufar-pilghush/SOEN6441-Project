package main.java.warzone.exceptions;

/**
 * This exception is thrown when there is a runtime error in the program.
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */

public class WarzoneRuntimeException extends WarzoneBaseException {

    /**
     * Constructor for the exception
     */
    public WarzoneRuntimeException() {
        super();
    }

    /**
     * Constructor for the exception
     *
     * @param p_ExceptionMessage is the exception message
     */
    public WarzoneRuntimeException(String p_ExceptionMessage) {
        super(p_ExceptionMessage);
    }
}

