package main.java.warzone.services.impl;
import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.*;
import main.java.warzone.services.GamePhaseService;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 *Implements the {@link GamePhaseService} Orchestrating reinforcement distribution based on owned
 * territories and continent control, ensuring fair play and strategic depth. It updates main.java.game state,
 * transitions phases, and reflects the dynamic nature of military allocation in gameplay, emphasizing
 * the importance of strategic territory control and player progression.
 *
 * Used to handle the reinforcement phase of the main.java.game.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */


public class ReinforcementServiceImpl implements GamePhaseService {

    /**
     * Current game session instance
     */
    private GameSession d_GameSession;

    /**
     * Number of reinforcement armies
     */
    private int d_MinimumReinforcementArmies;

    /**
     * LogEntryBuffer object to log the information and
     * notify all the observers
     */
    private LogEntryBuffer d_LogEntryBuffer;

    /**
     * Constructor to initialize ReinforcementService
     */
    public ReinforcementServiceImpl(){
        d_GameSession = GameSession.getInstance();
        d_MinimumReinforcementArmies = 3;
        d_LogEntryBuffer = LogEntryBuffer.getInstance();
    }

    /**
     * Manages the present game phase related to reinforcements.
     * Executes the reinforcement procedures and dictates the following game phase.
     *
     * @param p_CurrPhase the ongoing game phase tied to reinforcements.
     * @return the next game phase after handling reinforcements, in this context it's {@link GamePhase#ISSUE_ORDERS}.
     */
    @Override
    public GamePhase handleGamePhase(GamePhase p_CurrPhase) {
        d_LogEntryBuffer.logData("Reinforcement service handler");
        this.checkWinningCondition();
        this.reinforcePlayers();
        this.awardCards();
        this.clearDiplomacies();
        this.continentOwnershipAndReinforcements();

        return p_CurrPhase.validateAndMoveToNextState(GamePhase.MAIN_GAME_LOOP);
    }

    /**
     * Method to check winning condition
     */
    private void checkWinningCondition(){
        // Loop over players & check if any player has won
        Iterator<Map.Entry<String, Player>> l_PlayerIterator = d_GameSession.getPlayers().entrySet().iterator();
        while (l_PlayerIterator.hasNext()) {
            Map.Entry<String, Player> l_PlayerEntry = l_PlayerIterator.next();
            Player l_Player = l_PlayerEntry.getValue();
            if (l_Player.getOwnedCountries().size() == d_GameSession.getCountriesInSession().size()) {
                d_LogEntryBuffer.logData("Player " + l_Player.getName() + " has won the game!");
                System.exit(0);
            }
        }
    }

    /**
     * Method to reinforce players
     */
    private void reinforcePlayers(){
        // Loop over players & add reinforcements
        Iterator<Map.Entry<String, Player>> l_PlayerIterator = d_GameSession.getPlayers().entrySet().iterator();
        while (l_PlayerIterator.hasNext()) {
            Map.Entry<String, Player> l_PlayerEntry = l_PlayerIterator.next();
            Player l_Player = l_PlayerEntry.getValue();

            l_Player.addArmies(Math.max(Math.floorDiv(l_Player.getOwnedCountries().size(), 3), d_MinimumReinforcementArmies));
        }
    }

    /**
     * Method to update continent ownership and reinforcements
     */
    private void continentOwnershipAndReinforcements(){
        // Loop over continents & add continent control bonus
        Iterator<Map.Entry<String, Continent>> l_ContinentIterator = d_GameSession.getContinentsInSession().entrySet().iterator();
        while (l_ContinentIterator.hasNext()) {
            Map.Entry<String, Continent> l_ContinentEntry = l_ContinentIterator.next();
            Continent l_Continent = l_ContinentEntry.getValue();
            String l_ContinentOwner = l_Continent.getOwner();
            if (l_ContinentOwner != null) {
                Player l_ContinentOwnerPlayer = d_GameSession.getPlayers().get(l_ContinentOwner);
                l_ContinentOwnerPlayer.addArmies(l_Continent.getControlValue());
            }
            else{
                // Loop over all countries and check if only single players owns all countries
                boolean l_IsContinentOwned = true;
                String l_ContinentOwnerPlayerName = null;
                Iterator<Map.Entry<String, Country>> l_CountryIterator = l_Continent.getCountries().entrySet().iterator();
                while (l_CountryIterator.hasNext()) {
                    Map.Entry<String, Country> l_CountryEntry = l_CountryIterator.next();
                    Country l_Country = l_CountryEntry.getValue();
                    String l_CountryOwner = l_Country.getOwner();
                    if (l_ContinentOwnerPlayerName == null && l_CountryOwner != null) {
                        l_ContinentOwnerPlayerName = l_CountryOwner;
                    }
                    else if (l_CountryOwner == null || !l_CountryOwner.equals(l_ContinentOwnerPlayerName)) {
                        l_IsContinentOwned = false;
                        break;
                    }
                }
                if (l_IsContinentOwned) {
                    Player l_ContinentOwnerPlayer = d_GameSession.getPlayers().get(l_ContinentOwnerPlayerName);
                    l_Continent.setOwner(l_ContinentOwnerPlayerName);
                    l_ContinentOwnerPlayer.addArmies(l_Continent.getControlValue());
                }
            }
        }
    }

    /**
     * Method to award cards to players
     */
    private void awardCards() {
        // Loop over players & award cards
        Iterator<Map.Entry<String, Player>> l_PlayerIterator = d_GameSession.getPlayers().entrySet().iterator();
        while (l_PlayerIterator.hasNext()) {
            Map.Entry<String, Player> l_PlayerEntry = l_PlayerIterator.next();
            Player l_Player = l_PlayerEntry.getValue();
            if (l_Player.isEarnedCardThisTurn()) {
                // Draw card
                Random l_Random = new Random();
                int l_RandomCardIndex = l_Random.nextInt(WarzoneConstants.CARDS_LIST.length);
                l_Player.addCard(WarzoneConstants.CARDS_LIST[l_RandomCardIndex]);
                d_LogEntryBuffer.logData("Player " + l_Player.getName() + " has earned a card of type " + WarzoneConstants.CARDS_LIST[l_RandomCardIndex]);
                l_Player.setEarnedCardThisTurn(false);
            }
        }
    }

    /**
     * Method to clear diplomacies
     */
    private void clearDiplomacies() {
        // Loop over players & clear diplomacies
        Iterator<Map.Entry<String, Player>> l_PlayerIterator = d_GameSession.getPlayers().entrySet().iterator();
        while (l_PlayerIterator.hasNext()) {
            Map.Entry<String, Player> l_PlayerEntry = l_PlayerIterator.next();
            Player l_Player = l_PlayerEntry.getValue();
            l_Player.resetDiplomacyPlayers();
        }
    }
}

