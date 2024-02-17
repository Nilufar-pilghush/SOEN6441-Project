package main.java.com.warzone.Service.impl;
import main.java.com.warzone.Entities.*;
import main.java.com.warzone.Service.GamePhaseService;

import java.util.Iterator;
import java.util.Map;

/**
 * Implements the {@link GamePhaseService} Orchestrating reinforcement distribution based on owned territories and continent control, ensuring fair play and strategic depth. It updates game state, transitions phases, and reflects the dynamic nature of military allocation in gameplay, emphasizing the importance of strategic territory control and player progression.
 * Used to handle the reinforcement phase of the game.
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */

public class ReinforcementServiceImpl implements GamePhaseService {

    /**
     * Instance of the current game session
     */
    private GameSession d_GameSession;

    /**
     * Number of reinforcement armies assigned to a player
     */
    private int d_MinArmyReinforcements;

    /**
     * Initialization constructor for the ArmyReinforcementService
     */
    public ReinforcementServiceImpl() {
        d_GameSession = GameSession.getInstance();
        d_MinArmyReinforcements = 3;
    }

    /**
     * Manages the current game phase in relation to the army reinforcements for players
     * Carries out reinforcement operations and sets the stage for the next game phase
     *
     * @param p_CurrPhase the current game phase associated with reinforcements.
     * @return the next game phase after assigning reinforcements which transitions to {@link GamePhase#ISSUE_ORDERS}
     * @see {@link GamePhaseService#handleGamePhase(GamePhase)}
     */
    @Override
    public GamePhase handleGamePhase(GamePhase p_CurrPhase) {
        System.out.println("Army Reinforcement handler");
        this.checkIfPlayerWinner();
        this.addReinforcementsToPlayers();
        this.reinforcementsByContinentOwnership();

        return GamePhase.MAIN_GAME_LOOP;
    }

    /**
     * Method to add army reinforcements to players
     */
    private void addReinforcementsToPlayers() {
        Iterator<Map.Entry<String, Player>> l_Players = d_GameSession.getPlayers().entrySet().iterator();
        while (l_Players.hasNext()) {
            Map.Entry<String, Player> l_CurrPlayer = l_Players.next();
            Player l_Player = l_CurrPlayer.getValue();
            l_Player.addArmies(Math.max(Math.floorDiv(l_Player.getOwnedCountries().size(), 3), d_MinArmyReinforcements));
        }
    }


    /**
     * Checks if any player has won the game.
     * Iterates through all players in the current game session
     * to determine if a player owns all countries,indicating they have won.
     */
    private void checkIfPlayerWinner() {
        Iterator<Map.Entry<String, Player>> l_Players = d_GameSession.getPlayers().entrySet().iterator();
        while (l_Players.hasNext()) {
            Map.Entry<String, Player> l_CurrPlayer = l_Players.next();
            Player l_Player = l_CurrPlayer.getValue();
            if (l_Player.getOwnedCountries().size() == d_GameSession.getCountriesInSession().size()) {
                System.out.println("Player " + l_Player.getName() + "has won the game!");
                System.exit(0);
            }
        }
    }

    /**
     * Method to update continent ownership and assigning reinforcements based on continent ownership
     * If a continent is entirely owned by a single player, that player is granted bonus armies as per the continent's control value.
     */
    private void reinforcementsByContinentOwnership() {
        Iterator<Map.Entry<String, Continent>> l_Continents = d_GameSession.getContinentsInSession().entrySet().iterator();
        while (l_Continents.hasNext()) {
            Map.Entry<String, Continent> l_CurrContinent = l_Continents.next();
            Continent l_Continent = l_CurrContinent.getValue();
            String l_ContinentOwner = l_Continent.getOwner();
            if (l_ContinentOwner != null) {
                Player l_ContinentPlayerOwner = d_GameSession.getPlayers().get(l_ContinentOwner);
                l_ContinentPlayerOwner.addArmies(l_Continent.getControlValue());
            }
            else {
                boolean l_IsContinentOwned = true;
                String l_ContinentOwnerPlayer = null;
                Iterator<Map.Entry<String, Country>> l_Countries = l_Continent.getCountries().entrySet().iterator();
                while (l_Countries.hasNext()) {
                    Map.Entry<String, Country> l_CurrCountry = l_Countries.next();
                    Country l_Country = l_CurrCountry.getValue();
                    String l_CountryOwner = l_Country.getOwner();
                    if (l_ContinentOwnerPlayer == null && l_CountryOwner != null) {
                        l_ContinentOwnerPlayer = l_CountryOwner;
                    }
                    else if (l_CountryOwner == null || !l_CountryOwner.equals(l_ContinentOwnerPlayer)) {
                        l_IsContinentOwned = false;
                        break;
                    }
                }
            if (l_IsContinentOwned) {
                Player l_FinalContinentOwner = d_GameSession.getPlayers().get(l_ContinentOwnerPlayer);
                l_Continent.setOwner(l_ContinentOwnerPlayer);
                l_FinalContinentOwner.addArmies(l_Continent.getControlValue());
            }
            }
        }

    }


}
