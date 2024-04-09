package test.java.warzone.entities.players;

import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.Player;
import main.java.warzone.entities.players.HumanPlayerStrategy;
import main.java.warzone.exceptions.WarzoneValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Conducts tests on the {@code HumanPlayerStrategy} class to ensure that human player strategies within the Warzone game
 * function as intended. This class verifies the interactions of a human player within the game, focusing on the ability
 * of the human player strategy to process user input and execute game orders correctly.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */
public class HumanPlayerStrategyTest {
    /**
     * Current gamer session
     */
    private GameSession d_GameSession;

    /**
     * Output stream
     */
    private ByteArrayOutputStream d_OutputStream;

    /**
     * Input stream
     */
    private InputStream d_OriginalSystemIn;

    /**
     * Output stream
     */
    private PrintStream d_OriginalSystemOut;

    /**
     * Method to setup test
     *
     * @throws WarzoneValidationException if session creation fails
     */
    @BeforeEach
    public void setUp() throws WarzoneValidationException {
        d_GameSession = GameSession.getInstance();
        d_GameSession.clearPreviousSession();
        d_GameSession.createContinent("Asia", String.valueOf(5));
        d_GameSession.createCountry("Iran", "Asia");
        d_GameSession.createCountry("Turkey", "Asia");
        d_GameSession.makeNeighbors("Iran", "Turkey");
        d_GameSession.createPlayer("Player3");
        d_GameSession.getPlayers().get("Player3").setPlayerStrategy(new HumanPlayerStrategy());

        // Save the original system in and out streams
        d_OriginalSystemIn = System.in;
        d_OriginalSystemOut = System.out;

        // Set up custom input and output streams
        String userInput = "deploy Iran 3\nadvance Iran Turkey 2\nN\n";
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));
        d_OutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(d_OutputStream));
    }

    /**
     * Test to test issue player order
     */
    @Test
    void testIssuePlayerOrderVerifiesStrategy() {
        HumanPlayerStrategy l_HumanStrategy = new HumanPlayerStrategy();
        Player l_Player = d_GameSession.getPlayers().get("Player3");
        assertEquals(WarzoneConstants.HUMAN, l_Player.getPlayerStrategy().getStrategyNameString());
        System.setIn(d_OriginalSystemIn);
        System.setOut(d_OriginalSystemOut);
    }
}
