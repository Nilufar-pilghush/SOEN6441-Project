package test.java.warzone.services.io;

import main.java.warzone.services.io.GameMapDataHandlerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
     * Sets up the testing environment before each test. Initializes the {@link GameMapDataHandlerImpl} instance.
     */
    @BeforeEach
    public void setUp() {
        d_gameMapDataHandler = new GameMapDataHandlerImpl();
    }

    /**
     * Tests the map creation functionality from a map file input. Ensures that no exceptions are thrown
     * during the creation process when given a basic map file structure.
     */
    @Test
    public void testCreateGameMap() {
        String mapContent = "[continents]\n[countries]\n[borders]";
        InputStream in = new ByteArrayInputStream(mapContent.getBytes());
        Assertions.assertDoesNotThrow(() -> d_gameMapDataHandler.createGameMap(in),
                "Creating a game map from provided input should not throw exceptions.");
    }
}
