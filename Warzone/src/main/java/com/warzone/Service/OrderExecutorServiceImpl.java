package main.java.com.warzone.Service;
import main.java.com.warzone.Entities.GamePhase;
import main.java.com.warzone.Entities.GameSession;
import main.java.com.warzone.Entities.Order;
import main.java.com.warzone.Entities.Player;

import java.util.Iterator;
import java.util.Map;

/**
 * Implements the {@link GamePhaseService} interface managing the order issuance phase in the Warzone game.
 * Initializes with the current game session and prepares to take input from players
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
     * The current game session instance.
     */
    private GameSession d_GameSession;

    /**
     * Constructor to initialize the OrderExecutionManager.
     */
    public OrderExecutorServiceImpl() {

        d_GameSession = GameSession.getInstance();
    }

    /**
     * Handles the execution phase of the game.
     *
     * @param p_CurrPhaseurrentPhase The current phase of the game.
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
