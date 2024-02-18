package main.java.warzone;


        import main.java.warzone.entities.GamePhase;
        import main.java.warzone.exceptions.WarzoneBaseException;
        import main.java.warzone.services.GamePhaseService;

/**
 * Class to handle different segments of main.java.game and drive the complete main.java.game.
 * The GameEngine class is responsible for managing the flow and different segments of the main.java.game.
 * It drives the complete main.java.game by recursively handling main.java.game segments until the main.java.game exits.
 *
 * @author Ali Sayed Salehi
 */
public class GameEngine {

    /**
     * Handles the given main.java.game segment. If the segment is not EXIT, it retrieves
     * the appropriate segment service to handle the segment, and recursively
     * handles the upcoming segment.
     *
     * @param p_GamePhase The current main.java.game segment to handle.
     * @throws WarzoneBaseException If there's a validation issue within the segment.
     */
    public void handleGamePhases(GamePhase p_GamePhase) throws WarzoneBaseException {
        if (p_GamePhase != GamePhase.EXIT) {
            GamePhaseService l_PhaseService = p_GamePhase.getWarzonePhase(p_GamePhase);
            GamePhase l_UpcomingPhase = l_PhaseService.handleGamePhase(p_GamePhase);
            handleGamePhases(l_UpcomingPhase);
        } else {
            System.out.println("Exiting main.java.game play loop....");
        }
    }
}