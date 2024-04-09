package test.java.warzone.services.impl;

import main.java.warzone.entities.GamePhase;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.TournamentConfig;
import main.java.warzone.services.impl.TournamentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of the {@code TournamentServiceImpl} within the Warzone game, focusing on its ability to correctly
 * handle tournament segments and manage tournament configurations. This class ensures that the service layer responsible
 * for managing tournaments operates as expected, particularly in starting new rounds under varying configurations.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */
public class TournamentServiceImplTest {
    /**
     * Test case to start new round
     */
    @Test
    void testNewRoundInHandlePhases() {
        TournamentServiceImpl l_TournamentService = new TournamentServiceImpl();
        GamePhase l_GameSegment = GamePhase.TOURNAMENT;
        TournamentConfig l_TournamentConfig = new TournamentConfig();
        l_TournamentConfig.setNumberOfGames(3);
        l_TournamentConfig.setNumberOfMaxTurns(10);
        l_TournamentConfig.setMapFiles(new String[]{"map1", "map2"});
        l_TournamentConfig.setPlayerStrategies(new String[]{"Aggressive", "Benevolent"});
        GameSession l_GameWorld = GameSession.getInstance();
        l_GameWorld.setTournamentConfig(l_TournamentConfig);
        Assertions.assertDoesNotThrow(()->l_TournamentService.handleGamePhase(l_GameSegment)); // Starting the tournament
    }
}
