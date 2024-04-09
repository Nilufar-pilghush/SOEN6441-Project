package test.java.warzone.services.io;

import main.java.warzone.services.io.ConquestAdaptee;
import main.java.warzone.services.io.ConquestAdapter;
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
public class ConquestAdapterTest {
    /**
     * Adaptee instance
     */
    private ConquestAdaptee mockConquestAdaptee;

    /**
     * Adapter instance
     */
    private ConquestAdapter conquestAdapter;

    /**
     * Method to setup the test
     */
    @BeforeEach
    void setUp() {
        mockConquestAdaptee = new ConquestAdaptee();
        conquestAdapter = new ConquestAdapter(mockConquestAdaptee);
    }

    /**
     Test case for saving the game world
     */
    @Test
    void testSaveGameWorld() {
        String fileName = "testSaveGameWorld.map";
        assertDoesNotThrow(() -> conquestAdapter.saveGameSession(fileName));

    }

    /**
     Test case for making the game world with valid input
     */
    @Test
    void testMakeGameWorldValidInput() {
        String mapData = "[Continents]\nAsia=7\n\n[Territories]\nIndia,Asia\n";
        InputStream inputStream = new ByteArrayInputStream(mapData.getBytes());
        assertDoesNotThrow(() -> conquestAdapter.makeGameSession(inputStream));
    }

}
