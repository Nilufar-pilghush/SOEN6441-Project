package main.java.com.warzone.Entities;

public enum GamePhase {

//different enums for services
        MAP_EDITOR,

        START_UP,

        MAIN_GAME_LOOP,

        REINFORCEMENT,

        ISSUE_ORDERS,

        EXECUTE_ORDERS,

        EXIT;

        //methods to return next phase
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
