package main.java.warzone.services;

import main.java.warzone.entities.GamePhase;
import main.java.warzone.exceptions.WarzoneBaseException;

/**
 * Interface specifying the behaviors necessary for every game phase.
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public interface GamePhaseService {

    /**
     * Handles the given game phase.
     *
     * @param p_CurrPhase Current game phase.
     * @return Upcoming game phase.
     * @throws WarzoneBaseException If issue occurs while handling given phase.
     */
    GamePhase handleGamePhase(GamePhase p_CurrPhase) throws WarzoneBaseException;

}
