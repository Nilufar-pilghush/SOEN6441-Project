package main.java.warzone.entities.players;

import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.Player;
import main.java.warzone.utils.CmdUtils;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;
import main.java.constants.WarzoneConstants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;


public class HumanPlayerStrategy implements PlayerStrategy, Serializable {

    private BufferedReader d_InputScanner;

    private LogEntryBuffer d_LogEntryBuffer;


    public HumanPlayerStrategy() {
        d_InputScanner = new BufferedReader(new InputStreamReader(System.in));
        d_LogEntryBuffer = LogEntryBuffer.getInstance();
    }
}