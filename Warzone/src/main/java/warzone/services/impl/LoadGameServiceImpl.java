package main.java.warzone.services.impl;

import main.java.warzone.entities.GamePhase;
import main.java.warzone.services.GamePhaseService;
import main.java.warzone.utils.GameProgressTracker;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;
import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.exceptions.WarzoneBaseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Realization of the {@link GamePhaseService} that deals with the phase tied to
 * load saved game.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @since 3.0.0
 */
public class LoadGameServiceImpl implements GamePhaseService {

    /**
     * LogEntryBuffer object to log the information
     * and notifying all the observers
     */
    private LogEntryBuffer d_LogEntryBuffer;

    /**
     * Reader to take user input
     */
    private BufferedReader d_InputScanner;

    /**
     * Constructor to instantiate load game service
     */
    public LoadGameServiceImpl() {
        d_LogEntryBuffer = LogEntryBuffer.getInstance();
        d_InputScanner = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public GamePhase handleGamePhase(GamePhase p_CurrPhase) throws WarzoneBaseException {
        GameProgressTracker l_GameProgressTracker = new GameProgressTracker();
        l_GameProgressTracker.listSavedGameFiles();
        try {
            String l_Command = d_InputScanner.readLine();
            String[] l_UserInputParts = l_Command.split(WarzoneConstants.SPACE_REGEX);
            if (l_UserInputParts.length != 2 || !l_UserInputParts[0].equalsIgnoreCase(WarzoneConstants.LOAD_GAME)) {
                d_LogEntryBuffer.logData("Invalid user input, failed to load file");
                return GamePhase.START_UP;
            }
            String l_FileName = l_UserInputParts[1];
            if(!l_FileName.endsWith(WarzoneConstants.SAVE_GAME_EXTENSION)){
                l_FileName += WarzoneConstants.SAVE_GAME_EXTENSION;
            }
            return l_GameProgressTracker.loadGameProgress(l_FileName);
        } catch (IOException e) {
            d_LogEntryBuffer.logData("Invalid user input, failed to load file");
            return GamePhase.START_UP;
        }
    }
}

