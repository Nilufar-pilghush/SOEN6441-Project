package main.java.com.warzone.Service;


import main.java.com.warzone.Entities.*;
import main.java.com.warzone.Service.GamePhaseService;

import java.util.Iterator;
import java.util.Map;

public class ReinforcementManager implements GamePhaseService {
    private GameSession d_gameSession;
    private int d_minimumReinforcementArmies;

    public ReinforcementManager() {
        d_gameSession = GameSession.getInstance();
        d_minimumReinforcementArmies = 3;
    }

    @Override
    public GamePhase handleGamePhase(GamePhase p_currentSegment) {
        System.out.println("Handling reinforcement phase");
        checkWinningCondition();
        reinforcePlayers();
        updateContinentOwnershipAndReinforcements();
        return GamePhase.MAIN_GAME_LOOP;
    }

    private void checkWinningCondition() {
        for (Player l_player : d_gameSession.getPlayers().values()) {
            if (l_player.getOwnedCountries().size() == d_gameSession.getCountriesInSession().size()) {
                System.out.println(l_player.getName() + " has won the game!");
                System.exit(0);
            }
        }
    }

    private void reinforcePlayers() {
        for (Player l_player : d_gameSession.getPlayers().values()) {
            l_player.addArmies(Math.max(Math.floorDiv(l_player.getOwnedCountries().size(), 3), d_minimumReinforcementArmies));
        }
    }

    private void updateContinentOwnershipAndReinforcements() {
        for (Continent l_continent : d_gameSession.getContinentsInSession().values()) {
            String l_continentOwner = l_continent.get_Owner();
            if (l_continentOwner != null) {
                Player l_continentOwnerPlayer = d_gameSession.getPlayers().get(l_continentOwner);
                l_continentOwnerPlayer.addArmies(l_continent.get_ControlValue());
            } else {
                boolean l_isContinentOwned = true;
                String l_continentOwnerPlayerName = null;
                for (Country l_country : l_continent.getD_Countries().values()) {
                    String l_countryOwner = l_country.get_Owner();
                    if (l_continentOwnerPlayerName == null && l_countryOwner != null) {
                        l_continentOwnerPlayerName = l_countryOwner;
                    } else if (l_countryOwner == null || !l_countryOwner.equals(l_continentOwnerPlayerName)) {
                        l_isContinentOwned = false;
                        break;
                    }
                }
                if (l_isContinentOwned) {
                    Player l_continentOwnerPlayer = d_gameSession.getPlayers().get(l_continentOwnerPlayerName);
                    l_continent.set_Owner(l_continentOwnerPlayerName);
                    l_continentOwnerPlayer.addArmies(l_continent.get_ControlValue());
                }
            }
        }
    }
}
