package main.java.warzone.utils.logging;

/**
 * Interface defining an observer that can receive notifications.
 */
public interface Observer {

    /**
     * Method to notify the observer with a message.
     *
     * @param p_Notification the notification message.
     * @return true if the update was successful, false otherwise.
     */
    boolean update(String p_Notification);
}
