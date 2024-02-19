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




public enum GamePhase {
        /**
         * Scene Editor phase of gameplay
         */
        MAP_EDITOR,

        /**
         * Start up phase of gameplay
         */
        START_UP,

        /**
         * Main game loop phase of gameplay
         */
        MAIN_GAME_LOOP,

        /**
         * Reinforcement phase of gameplay
         */
        REINFORCEMENT,

        /**
         * Issue orders phase of gameplay
         */
        ISSUE_ORDERS,

        /**
         * Execute orders phase of gameplay
         */
        EXECUTE_ORDERS,

        /**
         * Exit phase of gameplay
         */
        EXIT;


        /**
         * Retrieves the corresponding service implementation for a given game segment.
         *
         * @param p_CurrGamePhase Current game segment.
         * @return Instance of the PhaseService corresponding to the provided game segment.
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
         * Method to get next game segment from current game segment.
         *
         * @param p_CurrGamePhase Current game segment.
         * @return Next game segment from current game segment
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
