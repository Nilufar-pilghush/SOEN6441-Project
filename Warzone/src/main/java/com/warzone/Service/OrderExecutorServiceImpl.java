package main.java.com.warzone.Service;
import main.java.com.warzone.Entities.GamePhase;
import main.java.com.warzone.Entities.GameSession;
import main.java.com.warzone.Entities.Order;
import main.java.com.warzone.Entities.Player;

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
     * @param p_currentPhase The current phase of the game.
     * @return The next phase after order execution.
     */
    public GamePhase handleGamePhase(GamePhase p_currentPhase) {
        System.out.println("Order execution service handler");

        // Iterate over players and execute their orders
        for (Player l_player : d_GameSession.getPlayers().values()) {
            executePlayerOrders(l_player);
        }

        return GamePhase.MAIN_GAME_LOOP;
    }

    /**
     * Executes orders for a specific player.
     *
     * @param p_player The player whose orders need to be executed.
     */
    private void executePlayerOrders(Player p_player) {
        Order l_playerOrder = p_player.nextOrder();
        while (l_playerOrder != null) {
            l_playerOrder.execute(d_GameSession);
            l_playerOrder = p_player.nextOrder();
        }
    }
}
