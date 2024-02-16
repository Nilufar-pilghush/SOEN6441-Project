package main.java.com.warzone.Service;

import main.java.com.warzone.Entities.GamePhase;
import main.java.com.warzone.Entities.GameSession;
import main.java.com.warzone.Entities.Player;
import main.java.com.warzone.Entities.Country;
import main.java.com.warzone.Service.GamePhaseService;
import main.java.com.warzone.Utils.CmdUtils;

import java.util.*;
public class OrderIssuance implements GamePhaseService{

    private final GameSession gameSession;
    private final Scanner inputScanner;

    public OrderIssuance() {
        inputScanner = new Scanner(System.in);
        gameSession = GameSession.getInstance();
    }

    @Override
    public GamePhase handleGamePhase(GamePhase currentPhase) {
        System.out.println("Handling order issuance phase");
        System.out.println("Iterating through players and prompting them to issue orders");

        // Loop over players & request orders
        for (Player player : gameSession.getPlayers().values()) {
            issuePlayerOrders(player);
        }

        return GamePhase.MAIN_GAME_LOOP;
    }

    /**
     * Issues orders from a specific player.
     *
     * @param player The player to request orders from.
     */
    private void issuePlayerOrders(Player player) {
        boolean isPlayerDoneIssuingOrders = false;

        while (!isPlayerDoneIssuingOrders) {
            try {
                System.out.println("Player " + player.getName() + " can issue orders now");
                System.out.println("You have " + player.getNumberOfArmies() + " armies available for reinforcement");
                System.out.println("Enter one of the following commands:");
                System.out.println("1. deploy countryID numArmies");
                System.out.println("2. attack sourceCountryID targetCountryID numArmies");

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

                System.out.println("Do you wish to issue more orders? (Y/N)");
                String moreUserInput = inputScanner.nextLine();
                isPlayerDoneIssuingOrders = moreUserInput.equalsIgnoreCase("N");
            } catch (Exception e) {
                System.out.println("Invalid command");
            }
        }
    }

    private void deployOrderHandler(List<String> userInputParts, Player player) {
        String countryName = userInputParts.get(1);
        int numArmies = Integer.parseInt(userInputParts.get(2));

        // Check if player owns the country
        if (!player.ownsCountry(countryName)) {
            if (!isAdjacentToOwnedCountry(player, countryName)) {
                System.out.println("No adjacent countries owned by you to deploy armies to " + countryName);
                return;
            }
            else if (isCountryOwnedByAnotherPlayer(countryName)) {
                System.out.println("Cannot deploy armies to " + countryName + ". It is owned by another player.");
                return;
            } else {
                System.out.println("Deploying armies to unowned country " + countryName);
            }
        }

        if (numArmies > player.getNumberOfArmies()) {
            System.out.println("Insufficient armies. You have only " + player.getNumberOfArmies() + " available.");
            return;
        }

        player.addArmies(-numArmies);
        player.addDeployOrder(countryName, numArmies);
    }

    private void attackOrderHandler(List<String> userInputParts, Player player) {
        String sourceCountryName = userInputParts.get(1);
        String targetCountryName = userInputParts.get(2);
        int numArmies = Integer.parseInt(userInputParts.get(3));

        // Check if player owns the source country
        if (!player.ownsCountry(sourceCountryName)) {
            System.out.println("You do not own the source country " + sourceCountryName);
            return;
        }

        // Check if player owns the target country or it's adjacent to any of the player's countries
        if (!player.ownsCountry(targetCountryName) && !isAdjacentToOwnedCountry(player, targetCountryName)) {
            System.out.println("No adjacent countries owned by you to attack " + targetCountryName);
            return;
        }

        // Do not allow if the source country has less than 1 army remaining
        if (numArmies >= gameSession.getCountriesInSession().get(sourceCountryName).getNumberOfArmies()) {
            System.out.println("Cannot attack. You need to have at least 1 army remaining in each country.");
            return;
        }

        player.addAttackOrder(sourceCountryName, targetCountryName, numArmies);
    }

    // Helper method to check if the country is adjacent to any of the player's countries
    private boolean isAdjacentToOwnedCountry(Player player, String countryName) {
        Set<String> ownedCountries = player.getOwnedCountries();
        Map<String, Country> adjacentCountries = gameSession.getCountriesInSession().get(countryName).getAdjacentCountries();
        return adjacentCountries.values().stream().anyMatch(adjacentCountry -> ownedCountries.contains(adjacentCountry.getName()));
    }

    // Helper method to check if the country is owned by another player
    private boolean isCountryOwnedByAnotherPlayer(String countryName) {
        return gameSession.getCountriesInSession().get(countryName).getOwner() != null;
    }


}
