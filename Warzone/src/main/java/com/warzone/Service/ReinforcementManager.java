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
}
