package main.java.warzone.utils.logging;

/**
 * Interface specifying the contract of the observer.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public interface Observer {

    /**
     * Method to update the observer about the change
     *
     * @param p_Notification Notification for the observer
     * @return True if logging was successful
     */
    boolean update(String p_Notification);
}
