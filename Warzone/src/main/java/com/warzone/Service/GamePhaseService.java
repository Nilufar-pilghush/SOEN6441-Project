package main.java.com.warzone.Service;
import main.java.com.warzone.Entities.GamePhase;

/**
 * Interface defining essential operations for each game phase.
 * Specifies a method to handle the current game phase and transition to the next phase.
 * Implementations of this interface are responsible for managing game phases and their respective actions.
 * The single method in this interface, 'handleGamePhase', is invoked to process the current game phase and determine the next phase.
 *
 * @author Niloufar Pilgush
 *  @author Nasrin Maarefi
 *  @author Jerome Kithinji
 *  @author Ali sayed Salehi
 *  @author Fatemeh Chaji
 *  @version 1.0.0
 * @version 1.0.0
 */

public interface GamePhaseService {

    GamePhase handleGamePhase(GamePhase p_CurrPhase);
}
