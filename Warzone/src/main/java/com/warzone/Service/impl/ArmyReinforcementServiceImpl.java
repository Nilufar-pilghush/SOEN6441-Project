package main.java.com.warzone.Service.impl;

import main.java.com.warzone.Entities.*;
import main.java.com.warzone.Service.GamePhaseService;

import java.util.Iterator;
import java.util.Map;

/**
 * Implements the {@link GamePhaseService} interface. Used to handle the reinforcement phase of the game.
 */
public class ArmyReinforcementServiceImpl implements GamePhaseService {

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
    public ArmyReinforcementServiceImpl() {
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
     * Method to check if any player has won
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
     */
    private void reinforcementsByContinentOwnership() {
        Iterator<Map.Entry<String, Continent>> l_Continents = d_GameSession.getContinentsInSession().entrySet().iterator();
        while (l_Continents.hasNext()) {
            Map.Entry<String, Continent> l_CurrContinent = l_Continents.next();
            Continent l_Continent = l_CurrContinent.getValue();
            String l_ContinentOwner = l_Continent.get_Owner();
            if (l_ContinentOwner != null) {
                Player l_ContinentPlayerOwner = d_GameSession.getPlayers().get(l_ContinentOwner);
                l_ContinentPlayerOwner.addArmies(l_Continent.get_ControlValue());
            }
            else {
                boolean l_IsContinentOwned = true;
                String l_ContinentOwnerPlayer = null;
                Iterator<Map.Entry<String, Country>> l_Countries = l_Continent.getCountries().entrySet().iterator();
                while (l_Countries.hasNext()) {
                    Map.Entry<String, Country> l_CurrCountry = l_Countries.next();
                    Country l_Country = l_CurrCountry.getValue();
                    String l_CountryOwner = l_Country.get_Owner();
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
                l_Continent.set_Owner(l_ContinentOwnerPlayer);
                l_FinalContinentOwner.addArmies(l_Continent.get_ControlValue());
            }
            }
        }

    }


}
