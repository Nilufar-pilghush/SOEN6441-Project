package main.java.com.warzone.Service;

import main.java.com.warzone.Entities.GamePhase;
import main.java.com.warzone.Entities.GameSession;
import main.java.com.warzone.Entities.Player;
import main.java.com.warzone.Entities.Country;
import main.java.com.warzone.Service.GamePhaseService;
import main.java.com.warzone.Utils.CmdUtils;

import java.util.*;
import java.util.*;

public class OrderIssuance implements GamePhaseService{

    private GameSession d_gameSession;
    private Scanner d_inputScanner;

    public OrderIssuance() {
        d_inputScanner = new Scanner(System.in);
        d_gameSession = GameSession.getInstance();
    }

    @Override
    public GamePhase handleGamePhase(GamePhase p_currentPhase) {
        System.out.println("Handling order issuance phase");
        System.out.println("Iterating through players and prompting them to issue orders");

        // Loop over players & request orders
        for (Player l_player : d_gameSession.getPlayers().values()) {
            issuePlayerOrders(l_player);
        }

        return GamePhase.MAIN_GAME_LOOP;
    }

    /**
     * Issues orders from a specific player.
     *
     * @param p_player The player to request orders from.
     */
    private void issuePlayerOrders(Player p_player) {
        boolean l_isPlayerDoneIssuingOrders = false;

        while (!l_isPlayerDoneIssuingOrders) {
            try {
                System.out.println("Player " + p_player.getName() + " can issue orders now");
                System.out.println("You have " + p_player.getNumberOfArmies() + " armies available for reinforcement");
                System.out.println("Enter one of the following commands:");
                System.out.println("1. deploy countryID numArmies");
                System.out.println("2. attack sourceCountryID targetCountryID numArmies");

                String l_userInput = d_inputScanner.nextLine();
                List<String> l_userInputParts = Arrays.asList(l_userInput.split("\\s+"));
                String l_primaryAction = l_userInputParts.get(0).toLowerCase();

                switch (l_primaryAction) {
                    case "deploy":
                        deployOrderHandler(l_userInputParts, p_player);
                        break;
                    case "attack":
                        attackOrderHandler(l_userInputParts, p_player);
                        break;
                    default:
                        System.out.println("Invalid command");
                }

                System.out.println("Do you wish to issue more orders? (Y/N)");
                String l_moreUserInput = d_inputScanner.nextLine();
                l_isPlayerDoneIssuingOrders = l_moreUserInput.equalsIgnoreCase("N");
            } catch (Exception e) {
                System.out.println("Invalid command");
            }
        }
    }


    private void deployOrderHandler(List<String> p_userInputParts, Player p_player) {
        String l_countryName = p_userInputParts.get(1);
        int l_numArmies = Integer.parseInt(p_userInputParts.get(2));

        // Check if player owns the country
        if (!p_player.ownsCountry(l_countryName)) {
            if (!isAdjacentToOwnedCountry(p_player, l_countryName)) {
                System.out.println("No adjacent countries owned by you to deploy armies to " + l_countryName);
                return;
            }
            else if (isCountryOwnedByAnotherPlayer(l_countryName)) {
                System.out.println("Cannot deploy armies to " + l_countryName + ". It is owned by another player.");
                return;
            } else {
                System.out.println("Deploying armies to unowned country " + l_countryName);
            }
        }

        if (l_numArmies > p_player.getNumberOfArmies()) {
            System.out.println("Insufficient armies. You have only " + p_player.getNumberOfArmies() + " available.");
            return;
        }

        p_player.addArmies(-l_numArmies);
        p_player.addDeployOrder(l_countryName, l_numArmies);
    }

    private void attackOrderHandler(List<String> p_userInputParts, Player p_player) {
        String l_sourceCountryName = p_userInputParts.get(1);
        String l_targetCountryName = p_userInputParts.get(2);
        int l_numArmies = Integer.parseInt(p_userInputParts.get(3));

        // Check if player owns the source country
        if (!p_player.ownsCountry(l_sourceCountryName)) {
            System.out.println("You do not own the source country " + l_sourceCountryName);
            return;
        }

        // Check if player owns the target country or it's adjacent to any of the player's countries
        if (!p_player.ownsCountry(l_targetCountryName) && !isAdjacentToOwnedCountry(p_player, l_targetCountryName)) {
            System.out.println("No adjacent countries owned by you to attack " + l_targetCountryName);
            return;
        }

        // Do not allow if the source country has less than 1 army remaining
        if (l_numArmies >= d_gameSession.getCountriesInSession().get(l_sourceCountryName).getNumberOfArmies()) {
            System.out.println("Cannot attack. You need to have at least 1 army remaining in each country.");
            return;
        }

        p_player.addAttackOrder(l_sourceCountryName, l_targetCountryName, l_numArmies);
    }


    // Helper method to check if the country is adjacent to any of the player's countries
    private boolean isAdjacentToOwnedCountry(Player p_player, String l_countryName) {
        Set<String> l_ownedCountries = p_player.getOwnedCountries();
        Map<Long, String> l_adjacentCountries = d_gameSession.getCountriesInSession().get(l_countryName).getAdjacentCountries();
        return l_adjacentCountries.values().stream().anyMatch(adjacentCountry -> l_ownedCountries.contains(adjacentCountry.get_Name()));
    }

    // Helper method to check if the country is owned by another player
    private boolean isCountryOwnedByAnotherPlayer(String l_countryName) {
        return d_gameSession.getCountriesInSession().get(l_countryName).getOwner() != null;
    }
}
