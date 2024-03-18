package test.java.warzone.services.impl;

import main.java.warzone.entities.GamePhase;
import main.java.warzone.services.impl.StartupPhaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Tests the functionality of {@link StartupPhaseServiceImpl} during the game's start-up phase.
 * It verifies that various commands such as loading maps, adding players, listing maps, displaying help,
 * and assigning countries can be executed without errors, ensuring the game can transition through the
 * START_UP phase as expected.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class StartupPhaseServiceImplTest {
    private StartupPhaseServiceImpl d_startupPhaseService;

    /**
     * Sets up the {@link StartupPhaseServiceImpl} before each test to ensure a clean environment.
     */
    @BeforeEach
    public void setUp() {
        d_startupPhaseService = new StartupPhaseServiceImpl();
    }

    /**
     * Tests the addition of a game player using the startup phase service.
     * Verifies that adding a player does not throw any exceptions and is handled correctly.
     */
    @Test
    public void testAddGamePlayer() {
        String input = "loadmap artic\ngameplayer -add Snehil\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> d_startupPhaseService.handleGamePhase(GamePhase.START_UP));
    }

    /**
     * Tests listing maps using the startup phase service.
     * Verifies that listing available maps does not throw any exceptions and is processed correctly.
     */
    @Test
    public void testListMaps() {
        String input = "listmaps\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> d_startupPhaseService.handleGamePhase(GamePhase.START_UP));
    }

    /**
     * Tests the help command using the startup phase service.
     * Ensures that requesting help information does not throw any exceptions and provides the necessary guidance.
     */
    @Test
    public void testHelpCommand() {
        String input = "help\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> d_startupPhaseService.handleGamePhase(GamePhase.START_UP));
    }

    /**
     * Tests assigning countries to players using the startup phase service.
     * Checks that the operation of assigning countries after adding players does not throw any exceptions.
     */
    @Test
    public void testAssignCountries() {
        String input = "loadmap artic\ngameplayer -add Snehil\nassigncountries\nexit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Assertions.assertDoesNotThrow(() -> d_startupPhaseService.handleGamePhase(GamePhase.START_UP));
    }

    /**
     * Cleans up after each test by resetting the {@link System#in} to its original state.
     * This ensures no test side-effects due to changes in {@link System#in}.
     */
    @AfterEach
    public void cleanUp() {
        System.setIn(System.in);
    }
}

