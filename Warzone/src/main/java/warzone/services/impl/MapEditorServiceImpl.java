package main.java.warzone.services.impl;

import main.java.warzone.services.io.GameMapDataHandlerImpl;
import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.exceptions.WarzoneRuntimeException;
import main.java.warzone.exceptions.WarzoneValidationException;
import main.java.warzone.entities.Country;
import main.java.warzone.entities.GamePhase;
import main.java.warzone.entities.GameSession;
import main.java.warzone.services.MapDataHandler;
import main.java.warzone.services.GamePhaseService;
import main.java.warzone.utils.CmdUtils;
import main.java.warzone.utils.FileUtils;
import main.java.warzone.utils.GameSessionValidator;

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
 * @version 1.0.0
 */
public class MapEditorServiceImpl implements GamePhaseService {

    /**
     * Instance of the current main.java.game session
     */
    private GameSession d_GameSession;

    /**
     * Initializes the map editor with the current main.java.game map in the session.
     */
    public MapEditorServiceImpl() {
        d_GameSession = GameSession.getInstance();
    }

    /**
     * Handles user input commands for map editing and navigate through the main.java.game phases.
     * @param p_CurrPhase The current phase of the main.java.game.
     * @return The next main.java.game phase based on user actions.
     */
    @Override
    public GamePhase handleGamePhase(GamePhase p_CurrPhase) {
        Scanner l_InputScanner = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("****************************** Welcome to Warzone Map Editor ******************************");
            System.out.println("Enter 'help' at any point in the main.java.game to view available commands in this phase");
            try {
                String l_UserInput = l_InputScanner.nextLine();
                List<String> l_UserInputTokens = CmdUtils.tokenizeUserInput(l_UserInput);
                String l_UserAction = l_UserInputTokens.get(0).toLowerCase();
                switch (l_UserAction) {
                    case "editcontinent" -> {
                        handleContinentEditing(l_UserInputTokens, l_UserAction);
                    }
                    case "editcountry" -> {
                        handleCountryEditing(l_UserInputTokens, l_UserAction);
                    }
                    case "editneighbor" -> {
                        handleNeighborEditing(l_UserInputTokens, l_UserAction);
                    }
                    case "showmap" -> {
                        handleShowMap(l_UserInputTokens);
                    }
                    case "listmaps" -> {
                        handleListMaps(l_UserInputTokens);
                    }
                    case "savemap" -> {
                        handleSaveMap(l_UserInputTokens);
                    }
                    case "editmap" -> {
                        handleEditMap(l_UserInputTokens);
                    }
                    case "validateMap" -> {
                        handleMapValidation(l_UserInputTokens);
                    }
                    case "exit" -> {
                        return GamePhase.START_UP;
                    }
                    default -> {
                        System.out.println("The command '" + l_UserAction + "' is not found.");
                        displayCommandsForMapEditor();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Displays available commands for the map editor.
     */
    private void displayCommandsForMapEditor() {
        System.out.println("************************************************** Map Editor Commands **************************************************");
        System.out.println("--To edit continents--");
        System.out.println("Add a continent: 'editcontinent -add <continentId> <continentValue>' || e.g. editcontinent -add Asia 5");
        System.out.println("Remove a Continent: 'editcontinent -remove <continentId>' || e.g. editcontinent -remove 5");
        System.out.println("--To edit countries--");
        System.out.println("Add Country: 'editcountry -add <countryID> <continentID>' || e.g. editcountry -add 2 5");
        System.out.println("Remove Country: 'editcountry -remove <countryID>' || e.g. editcountry -remove 2");
        System.out.println("--To edit neighbors--");
        System.out.println("Add Neighbor: 'editneighbor -add <countryID> <neighboringCountryID>' || e.g. editneighbor -add 2 4");
        System.out.println("Remove Neighbor: 'editneighbor -remove <countryID> <neighboringCountryID>' || e.g., editneighbor -remove Japan China");
        System.out.println("To view existing maps: listmaps");
        System.out.println("To edit an existing map: editmap <filename>");
        System.out.println("To save the current map: savemap <filename>");
        System.out.println("To validate the current map: validatemap");
        System.out.println("To view the current map: showmap");
        System.out.println("To exit the current phase: exit");
        System.out.println("************************************************************************************************************************");
    }

    /**
     * Shows the current main.java.game map with continents and countries.
     */
    private void showMap() {
        System.out.println("***************************** Current Game Map *****************************");

        System.out.println("Continents on this map are:");
        String l_ContinentFormating = "|        %-27s |%n";
        System.out.println("+____________________________+");
        System.out.println("|          Continent         |");
        System.out.println("+____________________________+");
        for (String l_ContinentName : d_GameSession.getContinentsInSession().keySet()) {
            System.out.printf(l_ContinentFormating + l_ContinentName);
        }
        System.out.println("+____________________________+");
        System.out.println();

        System.out.println("Countries on this map are:");
        String l_CountryFormating = "|    %15s    |  %30s       %n";
        System.out.println("+_______________________________________________________________________________________________________________________+");
        System.out.println("|      Country Name        |                         Neighbor Countries                                                 |");
        System.out.println("+_______________________________________________________________________________________________________________________+");
        for (String l_CountryName : d_GameSession.getCountriesInSession().keySet()) {
            Country l_Country = d_GameSession.getCountriesInSession().get(l_CountryName);
            System.out.printf(l_CountryFormating, l_CountryName, l_Country.getAdjacentCountries().values());
        }
        System.out.println("+_______________________________________________________________________________________________________________________+");
    }

    /**
     * Checks if the user has entered the right 'showmap' command.
     * @param p_UserInputTokens Tokens from user input.
     * @throws WarzoneValidationException if the command syntax is incorrect or additional arguments are provided.
     */
    private void handleShowMap(List<String> p_UserInputTokens) throws WarzoneValidationException {
        if (p_UserInputTokens.size() > 1) {
            throw new WarzoneValidationException("Invalid command of show map given");
        }
        showMap();
    }

    /**
     * Validates if the sub-commands for editing continents, countries, or neighbors are correctly formatted.
     * Checks if the command and its arguments match the expected format for adding or removing entities.
     * @param p_SubCommands Array of sub-command parts extracted from the user input.
     * @param p_SubCommandToCompareTo The action to compare (e.g., "add" or "remove").
     * @param p_UserCommand The primary command (e.g., "editcontinent", "editcountry", "editneighbor") to validate against.
     * @return true if the sub-command structure is valid according to the operation, false otherwise.
     */
    private boolean isSubCommandsCallValid(String[] p_SubCommands, String p_SubCommandToCompareTo, String p_UserCommand) {
        if (p_UserCommand.equalsIgnoreCase("editcontinent") && p_SubCommands[0].equalsIgnoreCase(p_SubCommandToCompareTo)) {
            if (p_UserCommand.equalsIgnoreCase("editcontinent") && p_SubCommands.length == 3) {
                return true;
            } else if (p_SubCommandToCompareTo.equalsIgnoreCase("remove") && p_SubCommands.length == 2) {
                return true;
            }
            return false;
        } else if (p_UserCommand.equalsIgnoreCase("editcountry") && p_SubCommands[0].equalsIgnoreCase(p_SubCommandToCompareTo)) {
            if (p_SubCommandToCompareTo.equalsIgnoreCase("add") && p_SubCommands.length == 2) {
                return true;
            } else if (p_SubCommandToCompareTo.equalsIgnoreCase("remove") && p_SubCommands.length == 2) {
                return true;
            }
        } else if (p_UserCommand.equalsIgnoreCase("editneighbor") && p_SubCommands[0].equalsIgnoreCase(p_SubCommandToCompareTo)) {
            if ((p_SubCommandToCompareTo.equalsIgnoreCase("add") || p_SubCommandToCompareTo.equalsIgnoreCase("remove")) && p_SubCommands.length ==3) {
                return true;
            }
        }
        return false;
    }

    /**
     * Handles continent editing commands based on the specified action (add or remove).
     * @param p_UserInputTokens Tokens extracted from the user's input indicating continent details.
     * @param p_UserAction The specific action to perform on continents (add or remove).
     * @throws WarzoneValidationException if the command syntax is incorrect or the operation cannot be completed.
     */
    private void handleContinentEditing(List<String> p_UserInputTokens, String p_UserAction) throws WarzoneValidationException {
        String l_UserSubCommands[] = p_UserInputTokens.get(1).split("\\s+");
        String l_UserSubCommand = l_UserSubCommands[0];
        if (l_UserSubCommand.equalsIgnoreCase("add")) {
            if (isSubCommandsCallValid(l_UserSubCommands, "add", p_UserAction)) {
                d_GameSession.createContinent(l_UserSubCommands[1], l_UserSubCommands[2]);
            } else {
                throw new WarzoneValidationException();
            }
        } else if (l_UserSubCommand.equalsIgnoreCase("remove")) {
            if (isSubCommandsCallValid(l_UserSubCommands, "remove", p_UserAction)) {
                d_GameSession.deleteContinent(l_UserSubCommands[1]);
            } else {
                throw new WarzoneValidationException();
            }
        } else {
            throw new WarzoneValidationException();
        }
    }

    /**
     * Handles country editing commands based on the specified action (add or remove).
     * @param p_UserInputTokens Tokens extracted from the user's input.
     * @param p_UserAction The specific action to perform on countries (add or remove).
     * @throws WarzoneValidationException if the command syntax is incorrect or the operation cannot be completed.
     */
    private void handleCountryEditing(List<String> p_UserInputTokens, String p_UserAction) throws WarzoneValidationException {
        String l_UserSubCommands[] = p_UserInputTokens.get(1).split("\\s+");
        String l_UserSubCommand = l_UserSubCommands[0];
        if (l_UserSubCommand.equalsIgnoreCase("add")) {
            if (isSubCommandsCallValid(l_UserSubCommands, "add", p_UserAction)) {
                // Generate a unique country ID
                Long l_CountryId = 1L;
                while (d_GameSession.getCountryIds().containsKey(l_CountryId)) {
                    l_CountryId++;
                }
                d_GameSession.createCountry(l_UserSubCommands[1], l_UserSubCommands[2], l_CountryId);
            } else {
                throw new WarzoneValidationException();
            }
        } else if (l_UserSubCommand.equalsIgnoreCase("remove")) {
            if (isSubCommandsCallValid(l_UserSubCommands, "remove", p_UserAction)) {
                d_GameSession.deleteCountry(l_UserSubCommands[1]);
            } else {
                throw new WarzoneValidationException();
            }
        } else {
            throw new WarzoneValidationException();
        }
    }

    /**
     * Manages neighbor editing commands based on the specified action (add or remove).
     * @param p_UserInputTokens Tokens extracted from the user's input indicating neighbor details.
     * @param p_UserAction The specific action to perform on neighbors (add or remove).
     * @throws WarzoneValidationException if the command syntax is incorrect or the operation cannot be completed.
     */
    private void handleNeighborEditing(List<String> p_UserInputTokens, String p_UserAction) throws WarzoneValidationException {
        String l_UserSubCommands[] = p_UserInputTokens.get(1).split("\\s+");
        String l_UserSubCommand = l_UserSubCommands[0];
        if (l_UserSubCommand.equalsIgnoreCase("add")) {
            if(isSubCommandsCallValid(l_UserSubCommands, "add", p_UserAction)) {
                d_GameSession.createNeighbors(l_UserSubCommands[1], l_UserSubCommands[2]);
            }
        } else if (l_UserSubCommand.equalsIgnoreCase("remove")) {
            if (isSubCommandsCallValid(l_UserSubCommands, "remove", p_UserAction)) {
                d_GameSession.deleteNeighbor(l_UserSubCommands[1], l_UserSubCommands[2]);
            }
        } else {
            throw new WarzoneValidationException();
        }
    }

    /**
     * Lists all available maps in the main.java.game.
     * @param p_UserInputTokens Tokens from user input.
     * @throws WarzoneValidationException if the command is not 'listmaps' command is not found.
     */
    private void handleListMaps(List<String> p_UserInputTokens) throws WarzoneValidationException {
        if (p_UserInputTokens.size() > 1) {
            throw new WarzoneValidationException("Invalid command of list maps given");
        }
    }

    /**
     * Validates the current main.java.game map and ensures the map is valid before proceeding.
     * @param p_UserInputTokens Tokens from user input
     * @throws WarzoneValidationException if the command is not 'validatemap' command is not found.
     */
    private void handleMapValidation(List<String> p_UserInputTokens) throws WarzoneValidationException {
        if (p_UserInputTokens.size() > 1) {
            throw new WarzoneValidationException("Invalid command of validate map given");
        }

        if (GameSessionValidator.validateMap(d_GameSession)) {
            System.out.println("Current main.java.game map is valid");
        } else {
            System.out.println("Current main.java.game map is invalid");
        }
    }

    /**
     * Saves the current map to a file.
     * @param p_UserInputTokens Tokens extracted from the user's input, including the filename.
     */
    private void handleSaveMap(List<String> p_UserInputTokens) throws WarzoneValidationException, Exception {
        if (p_UserInputTokens.size() > 1) {
            throw new WarzoneValidationException("Invalid command of save map given");
        }
        String l_FileName = p_UserInputTokens.get(1) + WarzoneConstants.GAME_MAP_EXTENSION;
        // check if the file exists
        String l_FilePath = WarzoneConstants.GAME_SESSIONS + WarzoneConstants.FORWARD_SLASH + l_FileName;
        File l_File = new File(l_FilePath);
        if (l_File.exists()) {
            throw new WarzoneValidationException("File with name " + l_FileName + " already exists");
        }

        if (!GameSessionValidator.validateMap(d_GameSession)) {
            throw new WarzoneValidationException("The map is not valid to save");
        }
        try (OutputStream l_NewMapFile = new FileOutputStream(l_File)) {
            MapDataHandler l_MapDataHandler = new GameMapDataHandlerImpl();
            l_MapDataHandler.saveGameMap(l_NewMapFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to create new map file", e);
        }
    }

    /**
     * Edits an existing map by loading it from a file.
     * @param p_UserInputTokens Tokens extracted from the user's input, including the filename.
     */
    private void handleEditMap(List<String> p_UserInputTokens) throws Exception {
        if (p_UserInputTokens.size() > 1) {
            throw new WarzoneValidationException("Invalid command of edit map given");
        }
        String l_MapFileName = p_UserInputTokens.get(1);
        InputStream l_GameMap = null;
        try {
            l_GameMap = FileUtils.getStreamFromGameFile(l_MapFileName);
        } catch (FileNotFoundException e) {
            System.out.println("Failed to edit the map file given:" + l_MapFileName);
            throw new WarzoneRuntimeException("Unable to edit map file given");
        }
        MapDataHandler l_MapDataHandler = new GameMapDataHandlerImpl();
        l_MapDataHandler.createGameMap(l_GameMap);
    }

}

