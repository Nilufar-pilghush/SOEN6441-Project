package main.java.warzone.entities.players;

import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.Player;
import java.io.Serializable;

/**
 * Interface for player strategy.
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */

public interface PlayerStrategy extends Serializable {

    /**
     * Method to issue player order.
     *
     * @param p_Player Player to issue order.
     * @param p_GameSession GameSession instance
     */
    void issuePlayerOrder(Player p_Player, GameSession p_GameSession);


    /**
     * Method to return strategy name.
     *
     * @return String strategy name.
     */
    String getStrategyNameString();
}