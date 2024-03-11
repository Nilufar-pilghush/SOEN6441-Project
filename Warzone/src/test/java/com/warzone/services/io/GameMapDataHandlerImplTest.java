package test.java.com.warzone.services.io;

        import main.java.warzone.services.io.GameMapDataHandlerImpl;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.Test;

        import java.io.ByteArrayInputStream;
        import java.io.InputStream;

/**
 * Tests for {@link GameMapDataHandlerImpl} focusing on game map creation and saving functionalities.
 * These tests verify the capability to correctly interpret and save map data, ensuring that the game world
 * can be initialized and persisted based on map file inputs.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class GameMapDataHandlerImplTest {
    private GameMapDataHandlerImpl d_gameMapDataHandler;

    /**
     * Initializes the {@link GameMapDataHandlerImpl} for testing its functionalities related to game map handling.
     */
    public void testGameMapDataHandlerImpl() {
        d_gameMapDataHandler = new GameMapDataHandlerImpl();
    }
    /**
     * Validates that a game map can be created from a map file input without throwing exceptions.
     * This test simulates the reading of a basic map file structure, asserting that the map creation process is successful.
     */
    @Test
    public void testCreateGameMap() {
        String mapContent = "[continents]\n[countries]\n[borders]";
        InputStream in = new ByteArrayInputStream(mapContent.getBytes());
        Assertions.assertDoesNotThrow(() -> d_gameMapDataHandler.createGameMap(in),
                "Creating a game map from provided input should not throw exceptions.");
    }
}
