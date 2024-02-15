package main.java.com.warzone.Service;

import main.java.com.warzone.Entities.GamePhase;
import main.java.com.warzone.Entities.GameSession;
import main.java.com.warzone.Entities.Player;
import main.java.com.warzone.Service.GamePhaseService;
import main.java.com.warzone.Utils.CmdUtils;

import java.util.*;
public class OrderIssuance implements GamePhaseService{

    /**
     * The current game session instance.
     */
    private final GameSession gameSession;

    /**
     * Scanner for user input.
     */
    private final Scanner inputScanner;

    /**
     * Constructor to initialize the OrderIssuanceManager.
     */
    public OrderIssuance() {
        inputScanner = new Scanner(System.in);
        gameSession = GameSession.getInstance();
    }

    /**
     * Manages the current game phase and handles tasks related to order issuance.
     *
     * @param currentPhase The ongoing game phase.
     * @return The subsequent game phase after completing the tasks.
     */
    @Override
    public GamePhase handleGamePhase(GamePhase currentPhase) {
        System.out.println("Order issuance service handler");
        System.out.println("Looping over players and requesting orders");

        // Loop over players & request orders
        for (Player player : gameSession.getPlayers().values()) {
            requestPlayerOrders(player);
        }

        return GamePhase.MAIN_GAME_LOOP;
    }

    /**
     * Requests orders from a specific player.
     *
     * @param player The player to request orders from.
     */
    private void requestPlayerOrders(Player player) {
        boolean isPlayerDoneIssuingOrders = false;

        while (!isPlayerDoneIssuingOrders) {
            try {
                System.out.println("Player " + player.getName() + " is issuing orders");
                System.out.println("Available player reinforcements: " + player.getNumberOfArmies());
                System.out.println("Use any of the following commands:");
                System.out.println("deploy countryID numArmies");
                System.out.println("attack sourceCountryID targetCountryID numArmies");

                String userInput = inputScanner.nextLine();
                List<String> userInputParts = Arrays.asList(userInput.split("\\s+"));
                String primaryAction = userInputParts.get(0).toLowerCase();

                switch (primaryAction) {
                    case "deploy":
                        deployOrderHandler(userInputParts, player);
                        break;
                    case "attack":
                        attackOrderHandler(userInputParts, player);
                        break;
                    default:
                        System.out.println("Invalid command");
                }

                System.out.println("Do you want to issue more orders? (Y/N)");
                String userInputMore = inputScanner.nextLine();
                isPlayerDoneIssuingOrders = userInputMore.equalsIgnoreCase("N");
            } catch (Exception e) {
                System.out.println("Invalid command");
            }
        }
    }

    /**
     * Handles the deployment of armies for a player.
     *
     * @param userInputParts The parsed user input parts.
     * @param player         The player issuing the deploy order.
     */
    private void deployOrderHandler(List<String> userInputParts, Player player) {
        // Implement deployment logic here

    }

    /**
     * Handles the attack orders issued by a player.
     *
     * @param userInputParts The parsed user input parts.
     * @param player         The player issuing the attack order.
     */
    private void attackOrderHandler(List<String> userInputParts, Player player) {
        // Implement attack logic here

    }

}
