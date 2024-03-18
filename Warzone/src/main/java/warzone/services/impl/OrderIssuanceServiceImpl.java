package main.java.warzone.services.impl;
import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.GamePhase;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.Player;
import main.java.warzone.entities.orders.Order;
import main.java.warzone.services.GamePhaseService;
import main.java.warzone.utils.CmdUtils;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;

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
 * @version 2.0.0
 */

public class OrderIssuanceServiceImpl implements GamePhaseService {

    /**
     * Current game session instance
     */
    private GameSession d_GameSession;

    /**
     * LogEntryBuffer object to log the information and
     * notify all the observers
     */
    private LogEntryBuffer d_LogEntryBuffer;

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
        d_LogEntryBuffer = LogEntryBuffer.getInstance();
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
        d_LogEntryBuffer.logData("Issue order service handler");
        d_LogEntryBuffer.logData("Looping over players and asking them to issue orders");

        // Loop over players & ask them to issue orders
        Iterator<Map.Entry<String, Player>> l_PlayerIterator = d_GameSession.getPlayers().entrySet().iterator();
        while (l_PlayerIterator.hasNext()) {
            Map.Entry<String, Player> l_PlayerEntry = l_PlayerIterator.next();
            Player l_Player = l_PlayerEntry.getValue();
            issuePlayerOrder(l_Player);

        }
        return p_CurrPhase.validateAndMoveToNextState(GamePhase.MAIN_GAME_LOOP);
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
                d_LogEntryBuffer.logData("Player " + p_Player.getName() + " is issuing orders");
                d_LogEntryBuffer.logData("Available player reinforcements: " + p_Player.getNumberOfArmies());
                d_LogEntryBuffer.logData("******************************************************");
                d_LogEntryBuffer.logData("Use any of below commands:");
                d_LogEntryBuffer.logData("deploy countryID numArmies");
                d_LogEntryBuffer.logData("advance sourceCountryID targetCountryID numArmies");
                d_LogEntryBuffer.logData("bomb targetCountryID");
                d_LogEntryBuffer.logData("blockade countryID");
                d_LogEntryBuffer.logData("airlift sourceCountryID targetCountryID numArmies");
                d_LogEntryBuffer.logData("negotiate playerID");
                d_LogEntryBuffer.logData("******************************************************");

                String l_UserInput = d_InputScanner.nextLine();
                List<String> l_UserInputParts = CmdUtils.tokenizeUserInput(l_UserInput);
                String l_PrimaryAction = l_UserInputParts.get(0).toLowerCase();

                switch (l_PrimaryAction) {
                    case WarzoneConstants.DEPLOY -> {
                        deployOrderHandler(l_UserInputParts, p_Player);
                    }
                    case WarzoneConstants.ADVANCE -> {
                        advanceOrderHandler(l_UserInputParts, p_Player);
                    }
                    case WarzoneConstants.BOMB -> {
                        bombOrderHandler(l_UserInputParts, p_Player);
                    }
                    case WarzoneConstants.BLOCKADE -> {
                        blockadeOrderHandler(l_UserInputParts, p_Player);
                    }
                    case WarzoneConstants.AIRLIFT -> {
                        airliftOrderHandler(l_UserInputParts, p_Player);
                    }
                    case WarzoneConstants.NEGOTIATE -> {
                        negotiateOrderHandler(l_UserInputParts, p_Player);
                    }
                }
                d_LogEntryBuffer.logData("Do you want to issue more orders? (Y/N)");
                String l_UserInputMore = d_InputScanner.nextLine();
                if (l_UserInputMore.equalsIgnoreCase("N")) {
                    l_isPlayerDoneIssuingOrders = true;
                }
            }catch (Exception e){
                d_LogEntryBuffer.logData("Invalid command");
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
            // If country is not owned then check if country is neighbour to one of player's countries
            if(Collections.disjoint(p_Player.getOwnedCountries(), d_GameSession.getCountriesInSession().get(l_CountryName).getAdjacentCountries().values())){
                d_LogEntryBuffer.logData("You do not own any countries adjacent to " + l_CountryName);
                return;
            }
            // If country adjacent is owned then check if target country is not owned by anyone
            else if(d_GameSession.getCountriesInSession().get(l_CountryName).getOwner() != null){
                d_LogEntryBuffer.logData("Country is owned by another player, please use the attack command to capture this country");
                return;
            }
            // If all above validations passed then allow deploy command
            else{
                d_LogEntryBuffer.logData("Can deploy armies to unowned country " + l_CountryName);
            }

        }
        if (l_NumArmies > p_Player.getNumberOfArmies()) {
            d_LogEntryBuffer.logData("You do not have enough armies to deploy");
            return;
        }
        p_Player.addArmies(-l_NumArmies);
        p_Player.addDeployOrder(
                l_CountryName,
                l_NumArmies
        );
    }

    /**
     * Method to handle advance orders.
     *
     * @param p_UserInputParts List of user input parts.
     * @param p_Player Player to add order.
     */
    private void advanceOrderHandler(List<String> p_UserInputParts, Player p_Player) {
        String l_SourceCountryName = p_UserInputParts.get(1);
        String l_TargetCountryName = p_UserInputParts.get(2);
        int l_NumArmies = Integer.parseInt(p_UserInputParts.get(3));
        // Check if player owns the country
        if (!p_Player.ownsCountry(l_SourceCountryName)){
            d_LogEntryBuffer.logData("You do not own the source country " + l_SourceCountryName);
        }
        if (!p_Player.ownsCountry(l_TargetCountryName)) {
            // If country is not owned then check if country is neighbour to one of player's countries
            if(Collections.disjoint(p_Player.getOwnedCountries(), d_GameSession.getCountriesInSession().get(l_TargetCountryName).getAdjacentCountries().values())){
                d_LogEntryBuffer.logData("You do not own any countries adjacent to " + l_TargetCountryName);
                return;
            }
            // If all above validations passed then allow deploy command
            else{
                d_LogEntryBuffer.logData("Can deploy armies to country " + l_TargetCountryName);
            }

        }
        // Do not allow if source country has less than 1 armies remaining
        if(l_NumArmies >= d_GameSession.getCountriesInSession().get(l_SourceCountryName).getNumberOfArmies()){
            d_LogEntryBuffer.logData("Cannot attack. You need to have minimum 1 army remaining in each country");
            return;
        }
        // Do not allow if player has diplomacy with target country owner
        String l_TargetCountryOwner = d_GameSession.getCountriesInSession().get(p_UserInputParts.get(2)).getOwner();
        if (l_TargetCountryOwner != null && p_Player.hasDiplomacyWith(l_TargetCountryOwner)) {
            d_LogEntryBuffer.logData("Cannot attack. You are negotiating with the owner " + l_TargetCountryOwner);
            return;
        }
        p_Player.addAttackOrder(
                l_SourceCountryName,
                l_TargetCountryName,
                l_NumArmies
        );
    }

    /**
     * Method to handle bomb orders.
     *
     * @param p_UserInputParts List of user input parts.
     * @param p_Player         Player to add order.
     */
    private void bombOrderHandler(List<String> p_UserInputParts, Player p_Player) {
        if (!d_GameSession.isCountryExists(p_UserInputParts.get(1))) {
            d_LogEntryBuffer.logData("Country does not exist");
            return;
        }

        // Do not allow if player has diplomacy with target country owner
        String l_TargetCountryOwner = d_GameSession.getCountriesInSession().get(p_UserInputParts.get(1)).getOwner();
        if (l_TargetCountryOwner != null && p_Player.hasDiplomacyWith(l_TargetCountryOwner)) {
            d_LogEntryBuffer.logData("Cannot bomb. You are negotiating with the owner " + l_TargetCountryOwner);
            return;
        }

        // If player owns card then allow the order
        if (p_Player.useCard(WarzoneConstants.BOMB)) {
            p_Player.addBombOrder(
                    p_UserInputParts.get(1)
            );
        } else {
            d_LogEntryBuffer.logData("You do not own the bomb card");
            return;
        }
    }

    /**
     * Method to handle blockade orders.
     *
     * @param p_UserInputParts List of user input parts.
     * @param p_Player         Player to add order.
     */
    private void blockadeOrderHandler(List<String> p_UserInputParts, Player p_Player) {
        if (!d_GameSession.isCountryExists(p_UserInputParts.get(1))) {
            d_LogEntryBuffer.logData("Country does not exist");
            return;
        }
        if (p_Player.useCard(WarzoneConstants.BLOCKADE)) {
            p_Player.addBlockadeOrder(
                    p_UserInputParts.get(1)
            );
        } else {
            d_LogEntryBuffer.logData("You do not own the blockade card!");
            return;
        }
    }

    /**
     * Method to handle negotiate orders.
     *
     * @param p_UserInputParts List of user input parts.
     * @param p_Player         Player to add order.
     */
    private void negotiateOrderHandler(List<String> p_UserInputParts, Player p_Player) {
        // Do not permit to diplomatically negotiate with self
        if (p_UserInputParts.get(1).equals(p_Player.getName())) {
            d_LogEntryBuffer.logData("Cannot negotiate with self");
            return;
        }

        // Do not permit to negotiate if attack order is already issued
        for (Order l_Order : p_Player.getOrderList()) {
            if (l_Order.getOrderType().equals(WarzoneConstants.ADVANCE)) {
                String l_TargetCountry = l_Order.getOrderDetails().getTargetCountry();
                String l_TargetCountryOwner = d_GameSession.getCountriesInSession().get(l_TargetCountry).getOwner();
                if (l_TargetCountryOwner != null && l_TargetCountryOwner.equals(p_UserInputParts.get(1))) {
                    d_LogEntryBuffer.logData("Cannot negotiate with " + p_UserInputParts.get(1) + " as you have already issued attack order on " + l_TargetCountry);
                    return;
                }
            }
        }

        if (p_Player.useCard(WarzoneConstants.NEGOTIATE)) {
            p_Player.addDiplomacyOrder(
                    p_UserInputParts.get(1)
            );
        } else {
            d_LogEntryBuffer.logData("You do not own the negotiate card");
            return;
        }
    }

    /**
     * Method to handle airlift orders.
     *
     * @param p_UserInputParts List of user input parts.
     * @param p_Player         Player to add order.
     */
    private void airliftOrderHandler(List<String> p_UserInputParts, Player p_Player) {
        String l_SourceCountryName = p_UserInputParts.get(1);
        int l_NumArmies = Integer.parseInt(p_UserInputParts.get(3));
        // Check if player owns the country
        if (!p_Player.ownsCountry(l_SourceCountryName)) {
            d_LogEntryBuffer.logData("You do not own the source country " + l_SourceCountryName);
        }

        // Do not permit if source country has less than 1 army remaining
        if (l_NumArmies >= d_GameSession.getCountriesInSession().get(l_SourceCountryName).getNumberOfArmies()) {
            d_LogEntryBuffer.logData("Cannot airlift. You need to have the minimum 1 army remaining in each country");
            return;
        }

        // Do not permit if player has diplomacy with target country owner
        String l_TargetCountryOwner = d_GameSession.getCountriesInSession().get(p_UserInputParts.get(2)).getOwner();
        if (l_TargetCountryOwner != null && p_Player.hasDiplomacyWith(l_TargetCountryOwner)) {
            d_LogEntryBuffer.logData("Cannot airlift. You are negotiating with the owner " + l_TargetCountryOwner);
            return;
        }

        if (p_Player.useCard(WarzoneConstants.AIRLIFT)) {
            p_Player.addAirliftOrder(
                    p_UserInputParts.get(1),
                    p_UserInputParts.get(2),
                    Integer.parseInt(p_UserInputParts.get(3))
            );
        } else {
            d_LogEntryBuffer.logData("You do not own the airlift card!");
            return;
        }
    }
}

