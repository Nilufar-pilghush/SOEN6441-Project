package test.java.com.warzone.services.io;

        import main.java.warzone.services.io.GameMapDataHandlerImpl;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.Test;

        import java.io.ByteArrayInputStream;
        import java.io.InputStream;

/**
 * JUnit test cases for the game map data manager class.
 * Test case to verify the game world creation from map.
 * Test case to verify the correct game world file saved on save file command.
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */
public class GameMapDataHandlerImplTest {

    /**
     * Class to be tested
     */
    private GameMapDataHandlerImpl gameMapDataHandler;

    /**
     * Constructor for initialization
     */
    public GameMapDataHandlerImplTest(){
        gameMapDataHandler = new GameMapDataHandlerImpl();
    }

    /**
     * Test case to create game world from file
     */
    @Test
    public void whenMakeGameSession_ExpectGameSessionTest(){
        String input1 = "[continents]\n[countries]\n[borders]";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        Assertions.assertDoesNotThrow(()-> gameMapDataHandler.createGameMap(in));
    }
}
