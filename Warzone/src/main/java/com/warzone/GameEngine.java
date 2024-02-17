package main.java.com.warzone;

import main.java.com.warzone.Entities.GamePhase;
import main.java.com.warzone.Exceptions.WarzoneBaseException;
import main.java.com.warzone.Service.GamePhaseService;

/**
 * Class to handle different segments of game and drive the complete game.
 * The GameEngine class is responsible for managing the flow and different segments of the game.
 * It drives the complete game by recursively handling game segments until the game exits.
 *
 * @author Ali Sayed Salehi
 */
public class GameEngine {

    /**
     * Handles the given game segment. If the segment is not EXIT, it retrieves
     * the appropriate segment service to handle the segment, and recursively
     * handles the upcoming segment.
     *
     * @param p_GamePhase The current game segment to handle.
     * @throws WarzoneBaseException If there's a validation issue within the segment.
     */
    public void handleGamePhases(GamePhase p_GamePhase) throws WarzoneBaseException {
        if (p_GamePhase != GamePhase.EXIT) {
            GamePhaseService l_PhaseService = p_GamePhase.getWarzonePhase(p_GamePhase);
            GamePhase l_UpcomingPhase = l_PhaseService.handleGamePhase(p_GamePhase);
            handleGamePhases(l_UpcomingPhase);
        } else {
            System.out.println("Exiting game play loop....");
        }
    }
}