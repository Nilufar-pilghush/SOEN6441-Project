package main.java.com.warzone.Service;

import main.java.com.warzone.Entities.GamePhase;
import main.java.com.warzone.Entities.GameSession;
import main.java.com.warzone.Entities.Order;
import main.java.com.warzone.Entities.Player;
import main.java.com.warzone.Service.GamePhaseService;

import java.util.Iterator;
import java.util.Map;

public class OrderExecutor {

    /**
     * The current game session instance.
     */
    private GameSession gameSession;

    /**
     * Constructor to initialize the OrderExecutionManager.
     */
    public OrderExecutor() {
        gameSession = GameSession.getInstance();
    }

    /**
     * Handles the execution phase of the game.
     *
     * @param currentPhase The current phase of the game.
     * @return The next phase after order execution.
     */
    public GamePhase handleExecutionPhase(GamePhase currentPhase) {
        System.out.println("Order execution service handler");

        // Iterate over players and execute their orders
        for (Player player : gameSession.getPlayers().values()) {
            executePlayerOrders(player);
        }

        return GamePhase.MAIN_GAME_LOOP;
    }

    /**
     * Executes orders for a specific player.
     *
     * @param player The player whose orders need to be executed.
     */
    private void executePlayerOrders(Player player) {
        Order playerOrder = player.nextOrder();
        while (playerOrder != null) {
            playerOrder.execute(gameSession);
            playerOrder = player.nextOrder();
        }
    }
}
