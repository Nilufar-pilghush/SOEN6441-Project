package main.java.warzone.utils.logging.impl;

import main.java.warzone.utils.logging.Observable;
import main.java.warzone.utils.logging.Observer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A realization of {@link Observable} which informs all the observing observers
 * about the change.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class LogEntryBuffer implements Observable, Serializable {

    /**
     * Singleton log entry buffer object
     */
    private static LogEntryBuffer d_LogEntryBuffer;

    /**
     * List of observers to be informed about the change
     */
    private List<Observer> d_Observers = new ArrayList<>();

    /**
     * Private constructor to implement singleton
     * design pattern so that there is only one instance
     * of LogEntryBuffer
     */
    private LogEntryBuffer() {

    }

    /**
     * Method to get singleton instance of LogEntryBuffer
     *
     * @return Singleton LogEntryBuffer instance
     */
    public static LogEntryBuffer getInstance() {
        if (Objects.isNull(d_LogEntryBuffer)) {
            d_LogEntryBuffer = new LogEntryBuffer();
        }
        return d_LogEntryBuffer;
    }

    /**
     * Method to log data and inform the observers
     *
     * @param p_Message Message to be logged
     */
    public void logData(String p_Message) {
        informObservers(p_Message);
    }

    @Override
    public void informObservers(String p_Notification) {
        for (Observer observer : d_Observers) {
            observer.update(p_Notification);
        }
    }

    @Override
    public boolean registerObserver(Observer p_Observer) {
        this.d_Observers.add(p_Observer);
        return true;
    }
}
