package main.java.warzone.utils.logging.impl;

import main.java.warzone.utils.logging.Observer;

import java.io.*;
import java.util.Objects;

/**
 * Implements the Observer interface to provide logging functionality.
 * This logger is specifically designed for game event logging, writing
 * notifications to a log file.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class GameLogger implements Observer {

    /**
     * Handles the update notifications by logging them to a file.
     * This method is called whenever an observed object changes and needs
     * to notify its observers.
     *
     * @param p_NotificationMessage The message to log, indicating a specific
     *                              game event or state change.
     * @return Always returns true to indicate successful logging.
     */
    @Override
    public boolean update(String p_NotificationMessage) {
        File l_LogDirectory = new File("LOGS_DIR");
        if (!l_LogDirectory.exists()) {
            l_LogDirectory.mkdirs();
        }
        PrintWriter l_LogWriter = null;
        try {
            l_LogWriter = new PrintWriter(new BufferedWriter(new FileWriter("logs" + "/" + "warzoneLogs.log", true)));
            l_LogWriter.println(p_NotificationMessage);
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (l_LogWriter != null) {
                l_LogWriter.close();
            }
        }
        return true;
    }
}
