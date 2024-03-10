package main.java.warzone.entities;

import main.java.warzone.services.GamePhaseService;
import main.java.warzone.services.impl.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This enum is designed for different phases of the main.java.game.
 * Each phase represents a distinct stage in the main.java.game lifecycle.
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */

public enum GamePhase{
        /**
         * Map Editor phase of gameplay
         */
        MAP_EDITOR {
                @Override
                public List<GamePhase> getPossibleGameStates() {
                        return Collections.singletonList(START_UP);}

                @Override
                public GamePhaseService getWarzonePhase() {
                        return new MapEditorServiceImpl();
                }
        },

        /**
         * Start up phase of gameplay
         */
        START_UP {
                @Override
                public List<GamePhase> getPossibleGameStates() {
                        return Collections.singletonList(MAIN_GAME_LOOP);
                }

                @Override
                public GamePhaseService getWarzonePhase() {
                        return new StartupPhaseServiceImpl();
                }
        },

        /**
         * Main game loop phase of gameplay
         */
        MAIN_GAME_LOOP {
                /**
                 * Main Game Loop phase is maintaining the loop between different phases of
                 * game loop namely reinforcement, issue order and execute orders.
                 *
                 * @return List of possible game states from MainGameLoop
                 */
                @Override
                public List<GamePhase> getPossibleGameStates() {
                        return Arrays.asList(REINFORCEMENT, ISSUE_ORDERS, EXECUTE_ORDERS, EXIT);
                }

                @Override
                public GamePhaseService getWarzonePhase() {
                        return new GameLoopServiceImpl();
                }
        },

        /**
         * Reinforcement phase of gameplay
         */
        REINFORCEMENT {
                @Override
                public List<GamePhase> getPossibleGameStates() {
                        return Collections.singletonList(MAIN_GAME_LOOP);
                }

                @Override
                public GamePhaseService getWarzonePhase() {
                        return new ReinforcementServiceImpl();
                }
        },

        /**
         * Issue orders phase of gameplay
         */
        ISSUE_ORDERS {
                @Override
                public List<GamePhase> getPossibleGameStates() {
                        return Collections.singletonList(MAIN_GAME_LOOP);
                }

                @Override
                public GamePhaseService getWarzonePhase() {
                        return new OrderIssuanceServiceImpl();
                }
        },

        /**
         * Execute orders phase of gameplay
         */
        EXECUTE_ORDERS {
                @Override
                public List<GamePhase> getPossibleGameStates() {
                        return Collections.singletonList(MAIN_GAME_LOOP);
                }

                @Override
                public GamePhaseService getWarzonePhase() {
                        return new OrderExecutorServiceImpl();
                }
        },

        /**
         * Exit phase of gameplay
         */
        EXIT {
                @Override
                public List<GamePhase> getPossibleGameStates() {
                        return Collections.singletonList(EXIT);
                }

                @Override
                public GamePhaseService getWarzonePhase() {
                        return null;
                }
        };

        /**
         * Method to get next game phase from current game phase.
         *
         * @param p_CurrGamePhase Current game phase.
         * @return Next game phase from current game phase.
         */
        public GamePhase getNextPhaseInMainGameLoop(GamePhase p_CurrGamePhase) {
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

        /**
         * Method to validate if allowed transition to game phase
         * is allowed or not from the invoking phase
         *
         * @param p_GamePhase phase to move on
         * @return Validated phase
         */
        public GamePhase validateAndMoveToNextState(GamePhase p_GamePhase) {
                if (this.getPossibleGameStates().contains(p_GamePhase)) {
                        return p_GamePhase;
                } else {
                        return this;
                }
        }

        /**
         * Retrieves the corresponding service implementation for a given game phase.
         *
         * @return Instance of the PhaseService corresponding to the provided game phase.
         */
        public abstract GamePhaseService getWarzonePhase();

        /**
         * Method to get possible states from a given warzone state
         *
         * @return List of possible states
         */

        public abstract List<GamePhase> getPossibleGameStates();

}
