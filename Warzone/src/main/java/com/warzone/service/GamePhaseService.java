package main.java.com.warzone.service;

import main.java.com.warzone.entities.GamePhase;

/**
 * Interface defining essential operations for each game phase.
 */
public interface GamePhaseService {

    GamePhase handleGamePhase(GamePhase p_CurrPhase);
}
