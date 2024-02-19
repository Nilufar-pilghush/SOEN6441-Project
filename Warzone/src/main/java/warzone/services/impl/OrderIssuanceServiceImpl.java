package main.java.warzone.services.impl;
import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.GamePhase;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.Player;
import main.java.warzone.services.GamePhaseService;
import main.java.warzone.utils.CmdUtils;

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

public class OrderIssuanceServiceImpl implements GamePhaseService {

    /**
     * Current game session instance
     */
    private GameSession d_GameSession;

    /**
     * Scanner to take user input
     */
    private Scanner d_InputScanner;

    /**
     * Constructor to initialize IssueOrdersService
     */
    public OrderIssuanceServiceImpl(){
        d_InputScanner = new Scanner(System.in);
        d_GameSession = GameSession.getInstance();
    }


    /**
     * Manages the present game phase and carries out tasks related to giving orders.
     *
     * @param p_CurrPhase the ongoing game phase
     * @return the subsequent game phase post the tasks. In this version, it consistently returns {@link GamePhase#EXIT}
     */
    @Override
    public GamePhase handleGamePhase(GamePhase p_CurrPhase) {
        Scanner l_InputScanner = new Scanner(System.in);
        System.out.println("Issue order service handler");
        System.out.println("Looping over players and asking them to issue orders");

        // Loop over players & ask them to issue orders
        Iterator<Map.Entry<String, Player>> l_PlayerIterator = d_GameSession.getPlayers().entrySet().iterator();
        while (l_PlayerIterator.hasNext()) {
            Map.Entry<String, Player> l_PlayerEntry = l_PlayerIterator.next();
            Player l_Player = l_PlayerEntry.getValue();
            issuePlayerOrder(l_Player);

        }
        return GamePhase.MAIN_GAME_LOOP;
    }

    /**
     * Method to issue player order.
     *
     * @param p_Player Player to issue order.
     */
    private void issuePlayerOrder(Player p_Player) {
        boolean l_isPlayerDoneIssuingOrders = false;

        while(!l_isPlayerDoneIssuingOrders) {
            try {
                System.out.println("Player " + p_Player.getName() + " is issuing orders");
                System.out.println("Available player reinforcements: " + p_Player.getNumberOfArmies());
                System.out.println("Use any of below commands:");
                System.out.println("deploy countryID numArmies");
                System.out.println("attack sourceCountryID targetCountryID numArmies");


                String l_UserInput = d_InputScanner.nextLine();
                List<String> l_UserInputParts = CmdUtils.tokenizeUserInput(l_UserInput);
                String l_PrimaryAction = l_UserInputParts.get(0).toLowerCase();

                switch (l_PrimaryAction) {
                    case WarzoneConstants.DEPLOY -> {
                        deployOrderHandler(l_UserInputParts, p_Player);
                    }
                    case WarzoneConstants.ATTACK -> {
                        attackOrderHandler(l_UserInputParts, p_Player);
                    }
                }
                System.out.println("Do you want to issue more orders? (Y/N)");
                String l_UserInputMore = d_InputScanner.nextLine();
                if (l_UserInputMore.equalsIgnoreCase("N")) {
                    l_isPlayerDoneIssuingOrders = true;
                }
            }catch (Exception e){
                System.out.println("Invalid command");
            }
        }

    }

    /**
     * Method to handle deploy orders.
     *
     * @param p_UserInputParts List of user input parts.
     * @param p_Player Player to add order.
     */
    private void deployOrderHandler(List<String> p_UserInputParts, Player p_Player) {
        String l_CountryName = p_UserInputParts.get(1);
        int l_NumArmies = Integer.parseInt(p_UserInputParts.get(2));

        // Check if player owns the country
        if (!p_Player.ownsCountry(l_CountryName)) {
            // If does not own country then check if country is neighbour to one of player's countries
            if(Collections.disjoint(p_Player.getOwnedCountries(), d_GameSession.getCountriesInSession().get(l_CountryName).getAdjacentCountries().values())){
                System.out.println("You do not own any countries adjacent to " + l_CountryName);
                return;
            }
            // If owns adjacent country then check if target country is not owned by anyone
            else if(d_GameSession.getCountriesInSession().get(l_CountryName).getOwner() != null){
                System.out.println("Country is owned by another player, please use the attack command to capture this country");
                return;
            }
            // If all above validations passed then allow deploy command
            else{
                System.out.println("Can deploy armies to unowned country " + l_CountryName);
            }

        }
        if (l_NumArmies > p_Player.getNumberOfArmies()) {
            System.out.println("You do not have enough armies to deploy");
            return;
        }
        p_Player.addArmies(-l_NumArmies);
        p_Player.addDeployOrder(
                l_CountryName,
                l_NumArmies
        );
    }

    /**
     * Method to handle attack orders.
     *
     * @param p_UserInputParts List of user input parts.
     * @param p_Player Player to add order.
     */
    private void attackOrderHandler(List<String> p_UserInputParts, Player p_Player) {
        String l_SourceCountryName = p_UserInputParts.get(1);
        String l_TargetCountryName = p_UserInputParts.get(2);
        int l_NumArmies = Integer.parseInt(p_UserInputParts.get(3));
        // Check if player owns the country
        if (!p_Player.ownsCountry(l_SourceCountryName)){
            System.out.println("You do not own the source country " + l_SourceCountryName);
        }
        if (!p_Player.ownsCountry(l_TargetCountryName)) {
            // If does not own country then check if country is neighbour to one of player's countries
            if(Collections.disjoint(p_Player.getOwnedCountries(), d_GameSession.getCountriesInSession().get(l_TargetCountryName).getAdjacentCountries().values())){
                System.out.println("You do not own any countries adjacent to " + l_TargetCountryName);
                return;
            }
            // If all above validations passed then allow deploy command
            else{
                System.out.println("Can deploy armies to country " + l_TargetCountryName);
            }

        }
        // Do not allow if source country has less than 1 armies remaining
        if(l_NumArmies >= d_GameSession.getCountriesInSession().get(l_SourceCountryName).getNumberOfArmies()){
            System.out.println("Cannot attack. You need to have minimum 1 army remaining in each country");
            return;
        }
        p_Player.addAttackOrder(
                l_SourceCountryName,
                l_TargetCountryName,
                l_NumArmies
        );

    }

}

