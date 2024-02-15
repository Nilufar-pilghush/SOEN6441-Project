package main.java.com.warzone.Service;

import main.java.com.warzone.Entities.GamePhase;

public interface PhaseService {
    GamePhase handlePhase(GamePhase p_CurrPhase);
}
