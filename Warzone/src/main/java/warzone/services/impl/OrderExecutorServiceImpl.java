package main.java.warzone.services.impl;
import main.java.warzone.entities.GamePhase;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.Order;
import main.java.warzone.entities.Player;
import main.java.warzone.services.GamePhaseService;

import java.util.Iterator;
import java.util.Map;

/**
 * Implements the {@link GamePhaseService} interface managing the order issuance phase in the Warzone main.java.game.
 * Initializes with the current main.java.game session and prepares to take input from players
 * for issuing orders.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */

public class OrderExecutorServiceImpl implements GamePhaseService {

    /**
     * The current main.java.game session instance.
     */
    private GameSession d_GameSession;

    /**
     * Constructor to initialize the OrderExecutionManager.
     */
    public OrderExecutorServiceImpl() {

        d_GameSession = GameSession.getInstance();
    }

    /**
     * Handles the execution phase of the main.java.game.
     *
     * @param p_CurrPhase The current phase of the main.java.game.
     * @return The next phase after order execution.
     */
    @Override
    public GamePhase handleGamePhase(GamePhase p_CurrPhase) {
        System.out.println("Execute orders service handler");

        // Loop over players & execute orders
        Iterator<Map.Entry<String, Player>> l_PlayerIterator = d_GameSession.getPlayers().entrySet().iterator();
        while (l_PlayerIterator.hasNext()) {
            Map.Entry<String, Player> l_PlayerEntry = l_PlayerIterator.next();
            Player l_Player = l_PlayerEntry.getValue();
            Order l_PlayerOrder = l_Player.nextOrder();
            while (l_PlayerOrder != null) {
                l_PlayerOrder.execute(d_GameSession);
                l_PlayerOrder = l_Player.nextOrder();
            }
        }
        return GamePhase.MAIN_GAME_LOOP;
    }

}
