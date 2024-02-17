package main.java.com.warzone.Entities;

import main.java.com.warzone.Service.GamePhaseService;
import main.java.com.warzone.Service.OrderExecutorServiceImpl;
import main.java.com.warzone.Service.OrderIssuanceServiceImpl;
import main.java.com.warzone.Service.impl.ReinforcementServiceImpl;
import main.java.com.warzone.Service.impl.GameLoopServiceImpl;
import main.java.com.warzone.Service.impl.MapEditorServiceImpl;

/**
 * This enum is designed for different phases of the game.
 * Each phase represents a distinct stage in the game lifecycle.
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */

/* different phases of the game */
public enum GamePhase {

        MAP_EDITOR,

        START_UP,

        MAIN_GAME_LOOP,

        REINFORCEMENT,

        ISSUE_ORDERS,

        EXECUTE_ORDERS,

        EXIT;


        /**
         * method to return corresponding service implementation for a given game phase.
         *
         * @param p_CurrGamePhase Current game phase.
         * @return Instance of the PhaseService corresponding to the provided game phase.
         */
        public GamePhaseService getWarzonePhase(GamePhase p_CurrGamePhase) {
                switch (p_CurrGamePhase) {
                        case MAP_EDITOR -> {
                                return new MapEditorServiceImpl();
                        }
//                        case START_UP -> {
//                                return new StartupPhaseServiceImpl();
//                        }
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
         * method to return next phase of the game based on the current phase
         * @param  p_CurrGamePhase: GamePhase
         * @return next phase of the game
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
