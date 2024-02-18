package main.java.warzone.services.impl;
import main.java.warzone.entities.GamePhase;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.Player;
import main.java.warzone.services.GamePhaseService;

import java.util.*;

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

public class OrderIssuanceServiceImpl implements GamePhaseService{


    /**
     * Represents the current main.java.game session.
     */
    private Scanner d_InputScanner;

    /**
     * Scanner object to read user input.
     */
    private GameSession d_GameSession;


    /**
     * Constructs an OrderIssuanceService object.
     * Initializes the input scanner with System.in and retrieves the current main.java.game session.
     */
    public OrderIssuanceServiceImpl() {
        d_InputScanner = new Scanner(System.in);
        d_GameSession = GameSession.getInstance();
    }


    /**
     * Handles the order issuance phase for the main.java.game.
     * Iterates through players and prompts them to issue orders.
     *
     * @param p_currentPhase The current phase of the main.java.game.
     * @return The next main.java.game phase after handling order issuance.
     */
    @Override
    public GamePhase handleGamePhase(GamePhase p_currentPhase) {
        System.out.println("Handling order issuance phase");
        System.out.println("Iterating through players and prompting them to issue orders");

        // Loop over players & request orders
        for (Player l_player : d_GameSession.getPlayers().values()) {
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

                String l_userInput = d_InputScanner.nextLine();
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
                String l_moreUserInput = d_InputScanner.nextLine();
                l_isPlayerDoneIssuingOrders = l_moreUserInput.equalsIgnoreCase("N");
            } catch (Exception e) {
                System.out.println("Invalid command");
            }
        }
    }

    /**
     * Handles the deploy order from a player.
     *
     * @param p_userInputParts The parsed user input parts.
     * @param p_player         The player issuing the deploy order.
     */
    private void deployOrderHandler(List<String> p_userInputParts, Player p_player) {
        String l_SourceCountryName = p_userInputParts.get(1);
        String l_TargetCountryName = p_userInputParts.get(2);
        int l_NumArmies = Integer.parseInt(p_userInputParts.get(3));

        // Check if player owns the country
        if (!p_player.ownsCountry(l_SourceCountryName)) {
                System.out.println("No adjacent countries owned by you to deploy armies to " + l_SourceCountryName);
                return;
        }
        if (!p_player.ownsCountry(l_TargetCountryName)) {
            if(Collections.disjoint(p_player.getOwnedCountries(), d_GameSession.getCountriesInSession().get(l_TargetCountryName).getAdjacentCountries().values())){

                System.out.println("Cannot deploy armies to " + l_TargetCountryName + ". It is owned by another player.");
                return;
            } else {
                System.out.println("Deploying armies to unowned country " + l_TargetCountryName);
            }
        }

        if (l_NumArmies > p_player.getNumberOfArmies()) {
            System.out.println("Insufficient armies. You have only " + p_player.getNumberOfArmies() + " available.");
            return;
        }

        p_player.addAttackOrder(l_SourceCountryName, l_TargetCountryName, l_NumArmies);
    }

    /**
     * Handles the attack order from a player.
     *
     * @param p_userInputParts The parsed user input parts.
     * @param p_player         The player issuing the attack order.
     */
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
        if (!p_player.ownsCountry(l_targetCountryName)) {
            // If does not own country then check if country is neighbour to one of player's countries
            if(Collections.disjoint(p_player.getOwnedCountries(), d_GameSession.getCountriesInSession().get(l_targetCountryName).getAdjacentCountries().values())){
                System.out.println("You do not own any countries adjacent to " + l_targetCountryName);
                return;
            }
            // If all above validations passed then allow deploy command
            else{
                System.out.println("Can deploy armies to country " + l_targetCountryName);
            }

        }

        /* Do not allow if the source country has less than 1 army remaining */
        if (l_numArmies >= d_GameSession.getCountriesInSession().get(l_sourceCountryName).getNumberOfArmies()) {
            System.out.println("Cannot attack. You need to have at least 1 army remaining in each country.");
            return;
        }

        p_player.addAttackOrder(l_sourceCountryName, l_targetCountryName, l_numArmies);
    }


}
