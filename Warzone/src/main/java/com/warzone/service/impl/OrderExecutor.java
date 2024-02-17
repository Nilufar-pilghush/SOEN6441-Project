package main.java.com.warzone.service.impl;

import main.java.com.warzone.entities.GamePhase;
import main.java.com.warzone.entities.GameSession;
import main.java.com.warzone.entities.Order;
import main.java.com.warzone.entities.Player;
import main.java.com.warzone.service.GamePhaseService;

public class OrderExecutor implements GamePhaseService {

    /**
     * The current game session instance.
     */
    private GameSession d_gameSession;

    /**
     * Constructor to initialize the OrderExecutionManager.
     */
    public OrderExecutor() {
        d_gameSession = GameSession.getInstance();
    }

    /**
     * Handles the execution phase of the game.
     *
     * @param p_currentPhase The current phase of the game.
     * @return The next phase after order execution.
     */
    public GamePhase handleExecutionPhase(GamePhase p_currentPhase) {
        System.out.println("Order execution service handler");

        // Iterate over players and execute their orders
        for (Player l_player : d_gameSession.getPlayers().values()) {
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
            l_playerOrder.execute(d_gameSession);
            l_playerOrder = p_player.nextOrder();
        }
    }

    @Override
    public GamePhase handleGamePhase(GamePhase p_CurrPhase) {
        return null;
    }
}
