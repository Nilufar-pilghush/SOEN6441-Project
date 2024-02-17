package main.java.com.warzone.Entities;

/**
 * This enum is designed for different phases of the game.
 * Each phase represents a distinct stage in the game lifecycle.
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome
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
