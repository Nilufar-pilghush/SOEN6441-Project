package main.java.com.warzone.Service.impl;

import main.java.com.warzone.Entities.GamePhase;
import main.java.com.warzone.Entities.GameSession;
import main.java.com.warzone.Entities.Player;
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

    // add reinforments armies to players based on the owned countries
    private void addReinforcementToPlayers() {
        Iterator<Map.Entry<String, Player>> l_Players = d_GameSession.getPlayers().entrySet().iterator();
        while (l_Players.hasNext()) {
            Map.Entry<String, Player> l_CurrPlayer = l_Players.next();
            Player l_Player = l_CurrPlayer.getValue();
            l_Player.addArmies(Math.max(Math.floorDiv(l_Player.getOwnedCountries().size(), 3), d_MinArmyReinforcements));
        }
    }


}
