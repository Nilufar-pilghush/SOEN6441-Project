package test.java.warzone.services.io;

import main.java.warzone.services.io.ConquestAdaptee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Concrete class implementing the behaviors necessary to manage
 * game session creation/saving from/to user specified files.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */
public class ConquestAdapteeTest {
    /**
     * ConquestAdaptee instance for testing.
     */
    private ConquestAdaptee conquestAdaptee;

    /**
     * Setup method to initialize the ConquestAdaptee instance before each test.
     */
    @BeforeEach
    void setUp() {
        conquestAdaptee = new ConquestAdaptee();
    }

    /**
     * Test case to ensure that saving the game world does not throw an exception.
     */
    @Test
    void testSaveGameWorld() {
        String fileName = "testSaveGameWorld.map";
        assertDoesNotThrow(() -> conquestAdaptee.saveGameSession(fileName));
    }

    /**
     * Test case to ensure that making the game world from valid input does not throw an exception.
     */
    @Test
    void testMakeGameWorldValidInput() {
        String mapData = "[Continents]\nAsia=7\n\n[Territories]\nIndia,Asia\n";
        InputStream inputStream = new ByteArrayInputStream(mapData.getBytes());
        assertDoesNotThrow(() -> conquestAdaptee.makeGameSession(inputStream));
    }

    /**
     * Test case to ensure that making the game world from valid input with multiple continents does not throw an exception.
     */
    @Test
    void testMakeGameWorldValidInputMultipleContinents() {
        String mapData = "[Continents]\nAsia=7\nEurope=5\n\n[Territories]\nIndia,Asia\nGermany,Europe\n";
        InputStream inputStream = new ByteArrayInputStream(mapData.getBytes());
        assertDoesNotThrow(() -> conquestAdaptee.makeGameSession(inputStream));
    }

    /**
     * Test case to ensure that making the game world from input with invalid neighbor format does not throw an exception.
     */
    @Test
    void testMakeGameWorldInvalidNeighborFormat() {
        String mapData = "[Territories]\nIndia,Asia,InvalidNeighbor";
        InputStream inputStream = new ByteArrayInputStream(mapData.getBytes());
        assertDoesNotThrow(() -> conquestAdaptee.makeGameSession(inputStream));
    }
}
