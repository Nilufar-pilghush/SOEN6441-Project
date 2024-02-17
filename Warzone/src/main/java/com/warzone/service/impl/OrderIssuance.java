package main.java.com.warzone.service.impl;

import main.java.com.warzone.entities.GamePhase;
import main.java.com.warzone.entities.GameSession;
import main.java.com.warzone.entities.Player;
import main.java.com.warzone.service.GamePhaseService;

import java.util.*;

public class OrderIssuance implements GamePhaseService {

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
        String l_SourceCountryName = p_userInputParts.get(1);
        String l_TargetCountryName = p_userInputParts.get(2);
        int l_NumArmies = Integer.parseInt(p_userInputParts.get(3));

        // Check if player owns the country
        if (!p_player.ownsCountry(l_SourceCountryName)) {
                System.out.println("No adjacent countries owned by you to deploy armies to " + l_SourceCountryName);
                return;
        }
        if (!p_player.ownsCountry(l_TargetCountryName)) {
            if(Collections.disjoint(p_player.getOwnedCountries(), d_gameSession.getCountriesInSession().get(l_TargetCountryName).getAdjacentCountries().values())){

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
            if(Collections.disjoint(p_player.getOwnedCountries(), d_gameSession.getCountriesInSession().get(l_targetCountryName).getAdjacentCountries().values())){
                System.out.println("You do not own any countries adjacent to " + l_targetCountryName);
                return;
            }
            // If all above validations passed then allow deploy command
            else{
                System.out.println("Can deploy armies to country " + l_targetCountryName);
            }

        }

        // Do not allow if the source country has less than 1 army remaining
        if (l_numArmies >= d_gameSession.getCountriesInSession().get(l_sourceCountryName).getNumberOfArmies()) {
            System.out.println("Cannot attack. You need to have at least 1 army remaining in each country.");
            return;
        }

        p_player.addAttackOrder(l_sourceCountryName, l_targetCountryName, l_numArmies);
    }


}
