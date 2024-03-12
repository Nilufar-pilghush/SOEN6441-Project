package main.java.warzone.utils.logging.impl;

import main.java.warzone.utils.logging.Observer;

import java.io.*;
import java.util.Objects;

public class GameLogger implements Observer {

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
