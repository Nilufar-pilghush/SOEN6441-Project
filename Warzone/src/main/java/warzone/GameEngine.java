package main.java.warzone;

import main.java.warzone.entities.GamePhase;
import main.java.warzone.exceptions.WarzoneBaseException;
import main.java.warzone.services.GamePhaseService;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;

/**
 * Class to handle different phase of main.java.game and drive the complete main.java.game.
 * The GameEngine class is responsible for managing the flow and different phases of the main.java.game.
 * It drives the complete main.java.game by recursively handling main.java.game phases until the main.java.game exits.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 *
 */

public class GameEngine {

    /**
     * LogEntryBuffer object for logging the user play data.
     */
    private LogEntryBuffer d_LogEntryBuffer;

    /**
     * Constructor to instantiate game engine
     */
    public GameEngine() {
        d_LogEntryBuffer = LogEntryBuffer.getInstance();
    }

    /**
     * Handles the given game phase. If the phase is not EXIT, it retrieves
     * the appropriate phase service to handle the phase, and recursively
     * handles the upcoming phase.
     *
     * @param p_GamePhase The current game phase to handle.
     * @throws WarzoneBaseException If there's a validation issue within the phase.
     */
    public void handleGamePhases(GamePhase p_GamePhase) throws WarzoneBaseException {
        if (p_GamePhase != GamePhase.EXIT) {
            GamePhaseService l_PhaseService = p_GamePhase.getWarzonePhase();
            GamePhase l_UpcomingPhase = l_PhaseService.handleGamePhase(p_GamePhase);
            handleGamePhases(l_UpcomingPhase);
        } else {
            d_LogEntryBuffer.logData("Exiting game play loop....");
        }
    }
}
