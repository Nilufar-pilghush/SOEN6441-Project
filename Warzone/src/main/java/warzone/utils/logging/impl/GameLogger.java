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

    public GameLogger() {
        clearLogContent();
    }

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
        makeDirectoryIfAbsent("logs");
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

    /**
     * Creates the log directory if it does not exist.
     *
     * @param p_DirectoryName The name of the directory to create.
     */
    private static void makeDirectoryIfAbsent(String p_DirectoryName) {
        File l_LogDirectory = new File(p_DirectoryName);
        if (!l_LogDirectory.exists()) {
            l_LogDirectory.mkdirs();
        }
    }

    /**
     * Method to clear the existing logs in the log file.
     */
    private void clearLogContent() {
        makeDirectoryIfAbsent("logs");
        File l_LogFile = new File("logs" + "/" + "warzoneLogs.log");
        if (l_LogFile.exists()) {
            l_LogFile.delete();
        }
    }


}
