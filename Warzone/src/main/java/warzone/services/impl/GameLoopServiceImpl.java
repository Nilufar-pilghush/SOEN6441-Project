package main.java.warzone.services.impl;

import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.Country;
import main.java.warzone.entities.GamePhase;
import main.java.warzone.entities.GameSession;
import main.java.warzone.services.GamePhaseService;
import main.java.warzone.utils.CmdUtils;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;

import java.util.*;

/**
 * Implements the {@link GamePhaseService} interface. Used to handle the different phases in the main.java.game loop.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */

public class GameLoopServiceImpl implements GamePhaseService {

    /**
     * Current game session instamce
     */
    private GameSession d_GameSession;

    /**
     * LogEntryBuffer object to log the information and
     * notify all the observers
     */
    private LogEntryBuffer d_LogEntryBuffer;

    /**
     * Constructor to initialize MainGameLoopService
     */
    public GameLoopServiceImpl() {
        d_GameSession = GameSession.getInstance();
        d_LogEntryBuffer = LogEntryBuffer.getInstance();
    }

    @Override
    public GamePhase handleGamePhase(GamePhase p_CurrPhase) {
        d_LogEntryBuffer.logData("Main game loop service handler");
        Scanner l_InputScanner = new Scanner(System.in);
        GamePhase l_NextPhase = p_CurrPhase.getNextPhaseInMainGameLoop(d_GameSession.getCurrGamePhase());
        while (true) {
            displayHelpCommandsForGamePhase(l_NextPhase);
            String l_UserInput = l_InputScanner.nextLine();
            List<String> l_UserInputParts = CmdUtils.tokenizeUserInput(l_UserInput);
            try {
                String l_PrimaryAction = l_UserInputParts.get(0).toLowerCase();
                switch (l_PrimaryAction) {
                    case WarzoneConstants.SHOW_MAP -> {
                        showMap();
                    }
                    case WarzoneConstants.HELP -> {
                        // Do nothing
                    }
                    case WarzoneConstants.EXIT -> {
                        return p_CurrPhase.validateAndMoveToNextState(GamePhase.EXIT);
                    }
                    default -> {
                        // validate change of game state, if the movement is allowed
                        return p_CurrPhase.validateAndMoveToNextState(proceedToGamePhase(l_NextPhase));
                    }
                }
            } catch (IndexOutOfBoundsException e) { // In case empty input is given
                proceedToGamePhase(l_NextPhase);
            }

        }
    }

    /**
     * Method to display help commands for game phase
     *
     * @param p_nextPhase Game phase
     */
    private void displayHelpCommandsForGamePhase(GamePhase p_nextPhase) {
        d_LogEntryBuffer.logData(".......................................MainGameLoop..........................................");
        d_LogEntryBuffer.logData("Current phase: " + d_GameSession.getCurrGamePhase());
        d_LogEntryBuffer.logData("Next phase in queue: " + p_nextPhase);
        d_LogEntryBuffer.logData("...Enter 'showmap' at any time to view the current state of the map...");
        d_LogEntryBuffer.logData("...Enter 'exit' at any time to exit the game...");
        d_LogEntryBuffer.logData("...Enter 'help' show this message...");
        d_LogEntryBuffer.logData("...Enter nothing to continue to the next phase...");
        d_LogEntryBuffer.logData("....................................................................................................");

    }

    /**
     * Method to show map
     */
    private void showMap() {
        d_LogEntryBuffer.logData("Showing map");
        // Print all players
        d_LogEntryBuffer.logData(WarzoneConstants.EMPTY);
        d_LogEntryBuffer.logData("--Players:");
        for (String l_PlayerName : d_GameSession.getPlayers().keySet()) {
            d_LogEntryBuffer.logData(WarzoneConstants.EMPTY);
            d_LogEntryBuffer.logData("--" + l_PlayerName);
            d_LogEntryBuffer.logData("----Armies: " + d_GameSession.getPlayers().get(l_PlayerName).getNumberOfArmies());
            d_LogEntryBuffer.logData("----Owned countries:");
            for (String l_CountryName : d_GameSession.getPlayers().get(l_PlayerName).getOwnedCountries()) {
                d_LogEntryBuffer.logData("------" + l_CountryName);
            }
        }

        d_LogEntryBuffer.logData(WarzoneConstants.EMPTY);
        d_LogEntryBuffer.logData(WarzoneConstants.EMPTY);
        // Print all continents
        d_LogEntryBuffer.logData("---Continents:");
        for (String l_ContinentName : d_GameSession.getContinentsInSession().keySet()) {
            d_LogEntryBuffer.logData(WarzoneConstants.EMPTY);
            d_LogEntryBuffer.logData("--" + l_ContinentName);
            d_LogEntryBuffer.logData("----Countries:");

            Iterator<Map.Entry<String, Country>> l_CountryIterator = d_GameSession.getContinentsInSession().get(l_ContinentName).getCountries().entrySet().iterator();
            while (l_CountryIterator.hasNext()) {
                Map.Entry<String, Country> l_CountryEntry = l_CountryIterator.next();
                d_LogEntryBuffer.logData("----" + l_CountryEntry.getKey());
                d_LogEntryBuffer.logData("------Armies: " + l_CountryEntry.getValue().getNumberOfArmies());
                d_LogEntryBuffer.logData("------Owned by:");
                d_LogEntryBuffer.logData("--------" + l_CountryEntry.getValue().getOwner());
                d_LogEntryBuffer.logData("------Adjacent countries:");
                for (String l_AdjacentCountryName : l_CountryEntry.getValue().getAdjacentCountries().values()) {
                    d_LogEntryBuffer.logData("--------" + l_AdjacentCountryName);
                }

            }
        }

    }

    /**
     * Method to proceed to next game phase
     *
     * @param p_NextPhase Next game phase.
     * @return Updated game phase.
     */
    private GamePhase proceedToGamePhase(GamePhase p_NextPhase) {
        d_GameSession.setCurrGamePhase(p_NextPhase);
        return p_NextPhase;
    }

}


