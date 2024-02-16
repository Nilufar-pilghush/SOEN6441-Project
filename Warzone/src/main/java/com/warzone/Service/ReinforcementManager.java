package main.java.com.warzone.Service;


import main.java.com.warzone.Entities.*;
import main.java.com.warzone.Service.GamePhaseService;

import java.util.Iterator;
import java.util.Map;

public class ReinforcementManager implements GamePhaseService {
    private GameSession gameSession;
    private int minimumReinforcementArmies;

    public ReinforcementManager() {
        gameSession = GameSession.getInstance();
        minimumReinforcementArmies = 3;
    }

    @Override
    public GamePhase handleGamePhase(GamePhase currentSegment) {
        System.out.println("Handling reinforcement phase");
        checkWinningCondition();
        reinforcePlayers();
        updateContinentOwnershipAndReinforcements();
        return GamePhase.MAIN_GAME_LOOP;
    }

    private void checkWinningCondition() {
        for (Player player : gameSession.getPlayers().values()) {
            if (player.getOwnedCountries().size() == gameSession.getCountriesInSession().size()) {
                System.out.println(player.getName() + " has won the game!");
                System.exit(0);
            }
        }
    }

    private void reinforcePlayers() {
        for (Player player : gameSession.getPlayers().values()) {
            player.addArmies(Math.max(Math.floorDiv(player.getOwnedCountries().size(), 3), minimumReinforcementArmies));
        }
    }

    private void updateContinentOwnershipAndReinforcements() {
        for (Continent continent : gameSession.getContinentsInSession().values()) {
            String continentOwner = continent.get_Owner();
            if (continentOwner != null) {
                Player continentOwnerPlayer = gameSession.getPlayers().get(continentOwner);
                continentOwnerPlayer.addArmies(continent.get_ControlValue());
            } else {
                boolean isContinentOwned = true;
                String continentOwnerPlayerName = null;
                for (Country country : continent.getD_Countries().values()) {
                    String countryOwner = country.get_Owner();
                    if (continentOwnerPlayerName == null && countryOwner != null) {
                        continentOwnerPlayerName = countryOwner;
                    } else if (countryOwner == null || !countryOwner.equals(continentOwnerPlayerName)) {
                        isContinentOwned = false;
                        break;
                    }
                }
                if (isContinentOwned) {
                    Player continentOwnerPlayer = gameSession.getPlayers().get(continentOwnerPlayerName);
                    continent.set_Owner(continentOwnerPlayerName);
                    continentOwnerPlayer.addArmies(continent.get_ControlValue());
                }
            }
        }
    }
}

