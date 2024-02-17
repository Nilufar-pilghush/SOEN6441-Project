package main.java.com.warzone.Service;

import main.java.com.warzone.Entities.GamePhase;

/**
 * Interface defining essential operations for each game phase.
 */
public interface GamePhaseService {

    GamePhase handleGamePhase(GamePhase p_CurrPhase);
}
