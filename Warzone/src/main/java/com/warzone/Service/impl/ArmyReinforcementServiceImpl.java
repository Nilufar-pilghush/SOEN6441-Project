package main.java.com.warzone.Service.impl;

import main.java.com.warzone.Entities.*;
import main.java.com.warzone.Service.GamePhaseService;

import java.util.Iterator;
import java.util.Map;

// add reinforments armies to players based on the owned countries
// give additional armies to players who control entire continents

public class ArmyReinforcementServiceImpl implements GamePhaseService {

    private GameSession d_GameSession;

    private int d_MinArmyReinforcements;

    public ArmyReinforcementServiceImpl() {
        d_GameSession = GameSession.getInstance();
        d_MinArmyReinforcements = 3;
    }

    @Override
    public GamePhase handleGamePhase(GamePhase p_CurrPhase) {
        return null;
    }
    // add reinforcements armies to players based on the owned countries
    private void addReinforcementToPlayers() {
        Iterator<Map.Entry<String, Player>> l_Players = d_GameSession.getPlayers().entrySet().iterator();
        while (l_Players.hasNext()) {
            Map.Entry<String, Player> l_CurrPlayer = l_Players.next();
            Player l_Player = l_CurrPlayer.getValue();
            l_Player.addArmies(Math.max(Math.floorDiv(l_Player.getOwnedCountries().size(), 3), d_MinArmyReinforcements));
        }
    }

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

    // give additional armies to players who control entire continents
    private void reinforcementsByContinentOwnership() {
        // checks if a user owns a continent and add the additional armies
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
                // Loop over all the countries and check if 1 player owns all the countries
                boolean l_IsContinentOwned = true;
                String l_ContinentOwnerPlayer = null;

                Iterator<Map.Entry<String, Country>> l_Countries = l_Continent.getD_Countries().entrySet().iterator();
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
