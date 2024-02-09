package com.warzone.Exceptions;


public class WarzoneBaseException extends Exception {

    public WarzoneBaseException() {
        super();
    }

    public WarzoneBaseException(String p_ExceptionMessage) {
        super(p_ExceptionMessage);
    }
}
