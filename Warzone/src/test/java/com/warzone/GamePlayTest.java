package test.java.com.warzone;

        import main.java.warzone.GamePlay;
        import main.java.warzone.exceptions.WarzoneBaseException;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.Test;

        import java.io.ByteArrayInputStream;
        import java.io.InputStream;

/**
 * JUnit test cases for the Game Play class.
 * Test case to verify main menu.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */
public class GamePlayTest {

    /**
     * Class to be tested
     */
    private GamePlay d_GameRunner;

    public GamePlayTest(){
        d_GameRunner = new GamePlay();
    }

    /**
     * Test case to verify main menu
     */
    @Test
    public void whenMainMenuExit_ExpectGameExitTest() throws WarzoneBaseException {
        String input1 = "3";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in);
        Assertions.assertTrue(d_GameRunner.showMainMenu());
    }
}
