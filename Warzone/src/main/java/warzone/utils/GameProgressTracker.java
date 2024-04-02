
package main.java.warzone.utils;

import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.Continent;
import main.java.warzone.entities.Country;

import java.util.*;

public class GameProgressTracker {

    private GameSession d_CurrGameSession;
    private LogEntryBuffer d_LogEntryBuffer;

    public GameProgressTracker() {
        d_CurrGameSession = GameSession.getInstance();
        d_LogEntryBuffer = LogEntryBuffer.getInstance();
    }
}