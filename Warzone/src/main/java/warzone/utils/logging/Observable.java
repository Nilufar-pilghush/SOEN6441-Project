package main.java.warzone.utils.logging;

/**
 * Interface specifying the contract of the observable
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public interface Observable {

    /**
     * Method to inform the observers about the notification
     *
     * @param p_Notification Message to be informed
     */
    void informObservers(String p_Notification);

    /**
     * Method to register new observer
     *
     * @param p_Observer Observer to be registered
     * @return True if register was successful
     */
    boolean registerObserver(Observer p_Observer);
}
