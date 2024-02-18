package main.java.warzone.exceptions;


/**
 * The base exception class for Warzone custom exceptions.
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 *
 */
public class WarzoneBaseException extends Exception {

    /**
     * Constructor for the exception
     */
    public WarzoneBaseException() {
        super();
    }

    /**
     * Constructor for the exception
     *
     * @param p_ExceptionMessage is the exception message
     */
    public WarzoneBaseException(String p_ExceptionMessage) {
        super(p_ExceptionMessage);
    }
}
