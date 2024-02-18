package main.java.warzone.entities;

import main.java.warzone.services.GamePhaseService;
import main.java.warzone.services.impl.*;

/**
 * This enum is designed for different phases of the main.java.game.
 * Each phase represents a distinct stage in the main.java.game lifecycle.
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */

/* different phases of the main.java.game */
public enum GamePhase {

        MAP_EDITOR,

        START_UP,

        MAIN_GAME_LOOP,

        REINFORCEMENT,

        ISSUE_ORDERS,

        EXECUTE_ORDERS,

        EXIT;


        /**
         * method to return corresponding service implementation for a given main.java.game phase.
         *
         * @param p_CurrGamePhase Current main.java.game phase.
         * @return Instance of the PhaseService corresponding to the provided main.java.game phase.
         */
        public GamePhaseService getWarzonePhase(GamePhase p_CurrGamePhase) {
                switch (p_CurrGamePhase) {
                        case MAP_EDITOR -> {
                                return new MapEditorServiceImpl();
                        }
                        case START_UP -> {
                                return new StartupPhaseServiceImpl();
                        }
                        case MAIN_GAME_LOOP -> {
                                return new GameLoopServiceImpl();
                        }
                        case REINFORCEMENT -> {
                                return new ReinforcementServiceImpl();
                        }
                        case ISSUE_ORDERS -> {
                                return new OrderIssuanceServiceImpl();
                        }
                        case EXECUTE_ORDERS -> {
                               return new OrderExecutorServiceImpl();
                        }
                }
                return null;
        }

        /**
         * method to return next phase of the main.java.game based on the current phase
         * @param  p_CurrGamePhase: GamePhase
         * @return next phase of the main.java.game
         */
        public GamePhase getNextPhaseInLoop(GamePhase p_CurrGamePhase) {
                if (p_CurrGamePhase == null) {
                        return GamePhase.REINFORCEMENT;
                }
                switch (p_CurrGamePhase) {
                        case REINFORCEMENT -> {
                                return GamePhase.ISSUE_ORDERS;
                        }
                        case ISSUE_ORDERS -> {
                                return GamePhase.EXECUTE_ORDERS;
                        }
                        case EXECUTE_ORDERS -> {
                                return GamePhase.REINFORCEMENT;
                        }
                        default -> {
                                return GamePhase.REINFORCEMENT;
                        }
                }
        }





    }
