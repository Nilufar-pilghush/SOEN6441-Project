package main.java.warzone.services.impl;

import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.exceptions.WarzoneRuntimeException;
import main.java.warzone.exceptions.WarzoneValidationException;
import main.java.warzone.entities.Country;
import main.java.warzone.entities.GamePhase;
import main.java.warzone.entities.GameSession;
import main.java.warzone.services.GameMapDataHandler;
import main.java.warzone.services.GamePhaseService;
import main.java.warzone.utils.CmdUtils;
import main.java.warzone.utils.FileUtils;
import main.java.warzone.utils.GameSessionValidator;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;

import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * Implements the {@link GamePhaseService} interface. Provides functionality
 * for user to add or remove continents, countries, and neighbors, view
 * and validate maps and save their changes to files.
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */


public class MapEditorServiceImpl implements GamePhaseService {

    /**
     * Current game session instance
     */
    private GameSession d_GameSession;

    /**
     * LogEntryBuffer object to log the information and
     * notify all the observers
     */
    private LogEntryBuffer d_LogEntryBuffer;

    /**
     * Constructor to initialize SceneEditorService
     */
    public MapEditorServiceImpl() {
        d_GameSession = GameSession.getInstance();
        d_LogEntryBuffer = LogEntryBuffer.getInstance();
    }

    /**
     * Manages the game's scene editor phase, interpreting user instructions for map modifications.
     *
     * @param p_CurrPhase The ongoing game phase.
     * @return The subsequent game phase post scene editor activities.
     */

    @Override
    public GamePhase handleGamePhase(GamePhase p_CurrPhase) {
        Scanner l_InputScanner = new Scanner(System.in);
        while (true) {
            d_LogEntryBuffer.logData(WarzoneConstants.EMPTY);
            d_LogEntryBuffer.logData("************ Welcome to War zone Map Editor ************");
            d_LogEntryBuffer.logData("...Enter 'help' at any time to view available commands in this phase...");
            try {
                String l_UserInput = l_InputScanner.nextLine();
                List<String> l_UserInputParts = CmdUtils.tokenizeUserInput(l_UserInput);
                String l_PrimaryAction = l_UserInputParts.get(0).toLowerCase();
                switch (l_PrimaryAction) {
                    case WarzoneConstants.EDIT_CONTINENT -> {
                        editContinentHandler(l_UserInputParts, l_PrimaryAction);
                    }
                    case WarzoneConstants.EDIT_COUNTRY -> {
                        editCountryHandler(l_UserInputParts, l_PrimaryAction);
                    }
                    case WarzoneConstants.EDIT_NEIGHBOR -> {
                        editNeighborHandler(l_UserInputParts, l_PrimaryAction);
                    }
                    case WarzoneConstants.SHOW_MAP -> {
                        showMapHandler(l_UserInputParts);
                    }
                    case WarzoneConstants.LIST_MAPS -> {
                        listMapsHandler(l_UserInputParts);
                    }
                    case WarzoneConstants.SAVE_MAP -> {
                        saveMapHandler(l_UserInputParts);
                    }
                    case WarzoneConstants.EDIT_MAP -> {
                        editMapHandler(l_UserInputParts);
                    }
                    case WarzoneConstants.VALIDATE_MAP->{
                        validateMapHandler(l_UserInputParts);
                    }
                    case WarzoneConstants.HELP -> {
                        displayHelpCommandsForGamePhase();
                    }
                    case WarzoneConstants.EXIT -> {
                        return p_CurrPhase.validateAndMoveToNextState(GamePhase.START_UP);
                    }
                    default -> {
                        System.out.println("Command not found--> " + l_PrimaryAction);
                        displayHelpCommandsForGamePhase();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Method to handle validate map command
     *
     * @param p_UserInputParts User input split into its constituent parts.
     * @throws WarzoneValidationException If command is invalid
     */
    private void validateMapHandler(List<String> p_UserInputParts) throws WarzoneValidationException {
        if (p_UserInputParts.size() > 1) {
            throw new WarzoneValidationException("Invalid validate map command!");
        }

        if (GameSessionValidator.validateMap(d_GameSession)) {
            d_LogEntryBuffer.logData("Current game map is valid.");
        } else {
            d_LogEntryBuffer.logData("Current game map is not valid.");
        }
    }

    /**
     * Saves the current game map to a specified file.
     *
     * @param p_UserInputParts User input split into its constituent parts.
     * @throws WarzoneValidationException When there's an issue with the user's savemap command.
     */
    private void saveMapHandler(List<String> p_UserInputParts) throws WarzoneValidationException {
        if (p_UserInputParts.size() <= 1) {
            throw new WarzoneValidationException("Invalid savemap command!");
        }
        String l_FileName = p_UserInputParts.get(1) + WarzoneConstants.GAME_MAP_EXTENSION;
        boolean l_doesFileExist = doesFileExist(l_FileName);
        if (l_doesFileExist) {
            throw new WarzoneValidationException("File with name: " + l_FileName + " already exists");
        }
        if (!GameSessionValidator.validateMap(d_GameSession)) {
            throw new WarzoneValidationException("Game session is not valid to save.");
        }
        GameMapDataHandler l_GameMapDataManager = new GameMapDataHandlerImpl();
        l_GameMapDataManager.saveGameMap(l_FileName);
    }

    /**
     * Determines whether a given game file is already present.
     *
     * @param p_FileName The name of the file to verify.
     * @return true if the file is found, else false.
     */
    private boolean doesFileExist(String p_FileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream l_GameScene = classLoader.getResourceAsStream(WarzoneConstants.GAME_SESSIONS + WarzoneConstants.FORWARD_SLASH + p_FileName);
        return l_GameScene != null;
    }

    /**
     * Manages the command to edit a map, either loading a pre-existing map or establishing a new one.
     *
     * @param p_UserInputParts The split components of the user's input.
     * @throws WarzoneValidationException In cases where there's a problem with the editmap instruction.
     * @throws FileNotFoundException      If the designated game map file can't be located.
     */
    private void editMapHandler(List<String> p_UserInputParts) throws WarzoneValidationException, WarzoneRuntimeException {
        if (p_UserInputParts.size() <= 1) {
            throw new WarzoneValidationException("Invalid editmap command!");
        }
        String l_GameFileName = p_UserInputParts.get(1);
        InputStream l_GameSceneMap = null;
        try {
            l_GameSceneMap = FileUtils.getStreamFromGameFile(l_GameFileName);
        } catch (FileNotFoundException e) {
            d_LogEntryBuffer.logData("Failed to edit map file---> " + l_GameFileName);
            throw new WarzoneRuntimeException("Unable to edit map file!");
        }
        GameMapDataHandler l_GameMapDataManager = new GameMapDataHandlerImpl();
        l_GameMapDataManager.createGameMap(l_GameSceneMap);
    }

    /**
     * Handles the continent editing commands.
     *
     * @param p_UserInputParts User input split into its constituent parts.
     * @param p_PrimaryAction  The main action (editcontinent) from the user input.
     * @throws WarzoneValidationException When there's an issue with the editcontinent command.
     */
    private void editContinentHandler(List<String> p_UserInputParts, String p_PrimaryAction) throws WarzoneValidationException {
        String[] l_SubActions = p_UserInputParts.get(1).split(WarzoneConstants.SPACE_REGEX);
        String l_SubAction = l_SubActions[0];
        if (l_SubAction.equalsIgnoreCase(WarzoneConstants.ADD)) {
            if (isValidSubActionsSequence(l_SubActions, WarzoneConstants.ADD, p_PrimaryAction)) {
                d_GameSession.createContinent(l_SubActions[1], l_SubActions[2]);
            } else {
                throw new WarzoneValidationException();
            }
        } else if (l_SubAction.equalsIgnoreCase(WarzoneConstants.REMOVE)) {
            if (isValidSubActionsSequence(l_SubActions, WarzoneConstants.REMOVE, p_PrimaryAction)) {
                d_GameSession.deleteContinent(l_SubActions[1]);
            } else {
                throw new WarzoneValidationException();
            }
        } else {
            throw new WarzoneValidationException();
        }
    }

    /**
     * Handles the country editing commands.
     *
     * @param p_UserInputParts User input split into its constituent parts.
     * @param p_PrimaryAction  The main action (editcountry) from the user input.
     * @throws WarzoneValidationException When there's an issue with the editcountry command.
     */
    private void editCountryHandler(List<String> p_UserInputParts, String p_PrimaryAction) throws WarzoneValidationException {
        String[] l_SubActions = p_UserInputParts.get(1).split(WarzoneConstants.SPACE_REGEX);
        String l_SubAction = l_SubActions[0];
        if (l_SubAction.equalsIgnoreCase(WarzoneConstants.ADD)) {
            if (isValidSubActionsSequence(l_SubActions, WarzoneConstants.ADD, p_PrimaryAction)) {
                d_GameSession.createCountry(l_SubActions[1], l_SubActions[2]);
            } else {
                throw new WarzoneValidationException();
            }
        } else if (l_SubAction.equalsIgnoreCase(WarzoneConstants.REMOVE)) {
            if (isValidSubActionsSequence(l_SubActions, WarzoneConstants.REMOVE, p_PrimaryAction)) {
                d_GameSession.deleteCountry(l_SubActions[1]);
            }
        } else {
            throw new WarzoneValidationException();
        }
    }

    /**
     * Manages commands for editing neighbors.
     *
     * @param p_UserInputParts phase components of the user's input.
     * @param p_PrimaryAction  The principal operation (editneighbor) extracted from user input.
     * @throws WarzoneValidationException If there's a problem with the editneighbor instruction.
     */

    private void editNeighborHandler(List<String> p_UserInputParts, String p_PrimaryAction) throws WarzoneValidationException {
        String[] l_SubActions = p_UserInputParts.get(1).split(WarzoneConstants.SPACE_REGEX);
        String l_SubAction = l_SubActions[0];
        if (l_SubAction.equalsIgnoreCase(WarzoneConstants.ADD)) {
            if (isValidSubActionsSequence(l_SubActions, WarzoneConstants.ADD, p_PrimaryAction)) {
                d_GameSession.makeNeighbors(l_SubActions[1], l_SubActions[2]);
            }
        } else if (l_SubAction.equalsIgnoreCase(WarzoneConstants.REMOVE)) {
            if (isValidSubActionsSequence(l_SubActions, WarzoneConstants.REMOVE, p_PrimaryAction)) {
                d_GameSession.removeNeighbors(l_SubActions[1], l_SubActions[2]);
            }
        } else {
            throw new WarzoneValidationException();
        }
    }

    /**
     * This method is used to view the existing maps for efficient game play
     *
     * @param p_UserInputParts the user input command
     * @throws WarzoneValidationException if the given command is invalid
     * @throws WarzoneRuntimeException    if the game sessions directory with maps is not present
     */
    private void listMapsHandler(List<String> p_UserInputParts) throws WarzoneValidationException, WarzoneRuntimeException {
        if (p_UserInputParts.size() > 1) {
            throw new WarzoneValidationException("Invalid listmaps command!");
        }
        try {
            FileUtils.listMaps();
        } catch (FileNotFoundException e) {
            throw new WarzoneRuntimeException("Unable to find gamesessions directory for listing maps");
        }
    }

    /**
     * Displays help commands specific to the map editor phase.
     */
    private void displayHelpCommandsForGamePhase() {
        d_LogEntryBuffer.logData(".......................................Map Editor Commands..........................................");
        d_LogEntryBuffer.logData("To edit a continent: editcontinent -add continentId continentValue -remove continentId");
        d_LogEntryBuffer.logData("To edit a country: editcountry -add countryID continentID -remove countryID");
        d_LogEntryBuffer.logData("To edit a neighbor: editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
        d_LogEntryBuffer.logData("To view existing maps: listmaps");
        d_LogEntryBuffer.logData("To edit an existing map: editmap filename");
        d_LogEntryBuffer.logData("To save current map: savemap filename");
        d_LogEntryBuffer.logData("To validate current map: validatemap");
        d_LogEntryBuffer.logData("To view current map: showmap");
        d_LogEntryBuffer.logData("To exit current phase: exit");
        d_LogEntryBuffer.logData("....................................................................................................");
    }

    /**
     * Method to display map in readable format.
     *
     * @param p_UserInputParts Parts of user input.
     * @throws WarzoneValidationException If command is invalid.
     */
    private void showMapHandler(List<String> p_UserInputParts) throws WarzoneValidationException {
        if (p_UserInputParts.size() > 1) {
            throw new WarzoneValidationException("Invalid showmap command");
        }
        showMap();
    }

    /**
     * Method to display map.
     */
    private void showMap() {
        d_LogEntryBuffer.logData("**************** Current Game session *********************");
        showContinents();
        showCountryNeighbors();
    }

    /**
     * Method to display continents.
     */
    private void showContinents() {
        d_LogEntryBuffer.logData("The continents of this game session are:");
        String l_ContinentNamePlaceHolder = "|        %-18s |%n";
        d_LogEntryBuffer.logData("+...........................+");
        d_LogEntryBuffer.logData("|      Continent Name       |");
        d_LogEntryBuffer.logData("+...........................+");
        for (String l_ContinentName : d_GameSession.getContinentsInSession().keySet()) {
            d_LogEntryBuffer.logData(String.format(l_ContinentNamePlaceHolder, l_ContinentName));
        }
        d_LogEntryBuffer.logData("+...........................+");
        d_LogEntryBuffer.logData("");
    }

    /**
     * Show country with their neighbors.
     */
    private void showCountryNeighbors() {
        d_LogEntryBuffer.logData("Countries with their neighbors are:");
        String l_CountryNeighborPlaceholder = "|    %20s    |  %50s                  %n";
        d_LogEntryBuffer.logData("+........................................................................................................................................+");
        d_LogEntryBuffer.logData("|        Country Name        |                         Neighbor Countries                                                                |");
        d_LogEntryBuffer.logData("+........................................................................................................................................+");
        for (String l_CountryName : d_GameSession.getCountriesInSession().keySet()) {
            Country l_Country = d_GameSession.getCountriesInSession().get(l_CountryName);
            d_LogEntryBuffer.logData(String.format(l_CountryNeighborPlaceholder, l_CountryName, l_Country.getAdjacentCountries().values()));
        }
        d_LogEntryBuffer.logData("+........................................................................................................................................+");
    }

    /**
     * Validates if the secondary actions in the user input match the expected sequence based on the primary action.
     *
     * @param p_SubActionsSequence         Array of secondary actions.
     * @param p_SubActionToValidateAgainst The specific sub-action to validate against.
     * @param p_PrimaryAction              The main action from the user input.
     * @return true if valid, otherwise false.
     */
    private boolean isValidSubActionsSequence(String[] p_SubActionsSequence, String p_SubActionToValidateAgainst, String p_PrimaryAction) {
        if (p_PrimaryAction.equalsIgnoreCase(WarzoneConstants.EDIT_CONTINENT) && p_SubActionsSequence[0].equalsIgnoreCase(p_SubActionToValidateAgainst)) {
            if ((p_SubActionToValidateAgainst.equalsIgnoreCase(WarzoneConstants.ADD) && p_SubActionsSequence.length == 3)) {
                return true;
            } else if (p_SubActionToValidateAgainst.equalsIgnoreCase(WarzoneConstants.REMOVE) && p_SubActionsSequence.length == 2) {
                return true;
            }
            return false;
        } else if (p_PrimaryAction.equalsIgnoreCase(WarzoneConstants.EDIT_COUNTRY) && p_SubActionsSequence[0].equalsIgnoreCase(p_SubActionToValidateAgainst)) {
            if (p_SubActionToValidateAgainst.equalsIgnoreCase(WarzoneConstants.ADD) && p_SubActionsSequence.length == 3) {
                return true;
            } else if (p_SubActionToValidateAgainst.equalsIgnoreCase(WarzoneConstants.REMOVE) && p_SubActionsSequence.length == 2) {
                return true;
            }
        } else if (p_PrimaryAction.equalsIgnoreCase(WarzoneConstants.EDIT_NEIGHBOR) && p_SubActionsSequence[0].equalsIgnoreCase(p_SubActionToValidateAgainst)) {
            if ((p_SubActionToValidateAgainst.equalsIgnoreCase(WarzoneConstants.ADD) || p_SubActionToValidateAgainst.equalsIgnoreCase(WarzoneConstants.REMOVE)) && p_SubActionsSequence.length == 3) {
                return true;
            }
        }
        return false;
    }
}


