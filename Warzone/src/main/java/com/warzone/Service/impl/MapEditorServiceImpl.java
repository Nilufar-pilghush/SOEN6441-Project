package main.java.com.warzone.Service.impl;

import main.java.com.warzone.Entities.Country;
import main.java.com.warzone.Entities.GamePhase;
import main.java.com.warzone.Entities.GameSession;
import main.java.com.warzone.Exceptions.WarzoneBaseException;
import main.java.com.warzone.Service.MapDataHandler;
import main.java.com.warzone.Service.GamePhaseService;
import main.java.com.warzone.constants.WarzoneConstants;
import main.java.com.warzone.utils.CmdUtils;
import main.java.com.warzone.utils.FileUtils;
import main.java.com.warzone.utils.GameWorldValidator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// edit current game map
// display edit map options
// handle user input commands and validate
// display map before and after editing


public class MapEditorServiceImpl implements GamePhaseService {

    private GameSession d_GameSession;

    public MapEditorServiceImpl() {
        d_GameSession = GameSession.getInstance();
    }

    @Override
    public GamePhase handleGamePhase(GamePhase p_CurrPhase) {
        Scanner l_InputScanner = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("****************************** Welcome to Warzone Scene Editor ******************************");
            System.out.println("Enter 'help' at any point in the game to view available commands in this phase");
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
            System.out.printf(l_CountryFormating, l_CountryName, l_Country.getD_AdjacentCountries().values());
        }
        System.out.println("+_______________________________________________________________________________________________________________________+");
    }

    private void handleShowMap(List<String> p_UserInputTokens) throws Exception {
        if (p_UserInputTokens.size() > 1) {
            throw new Exception("Invalid command of show map given");
        }
        showMap();
    }


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

    private void handleContinentEditing(List<String> p_UserInputTokens, String p_UserAction) throws Exception {
        String l_UserSubCommands[] = p_UserInputTokens.get(1).split("\\s+");
        String l_UserSubCommand = l_UserSubCommands[0];
        if (l_UserSubCommand.equalsIgnoreCase("add")) {
            if (isSubCommandsCallValid(l_UserSubCommands, "add", p_UserAction)) {
                d_GameSession.createContinent(l_UserSubCommands[1], l_UserSubCommands[2]);
            } else {
                throw new Exception();
            }
        } else if (l_UserSubCommand.equalsIgnoreCase("remove")) {
            if (isSubCommandsCallValid(l_UserSubCommands, "remove", p_UserAction)) {
                d_GameSession.deleteContinent(l_UserSubCommands[1]);
            } else {
                throw new Exception();
            }
        } else {
            throw new Exception();
        }
    }

    private void handleCountryEditing(List<String> p_UserInputTokens, String p_UserAction) throws Exception {
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
                throw new Exception();
            }
        } else if (l_UserSubCommand.equalsIgnoreCase("remove")) {
            if (isSubCommandsCallValid(l_UserSubCommands, "remove", p_UserAction)) {
                d_GameSession.deleteCountry(l_UserSubCommands[1]);
            } else {
                throw new Exception();
            }
        } else {
            throw new Exception();
        }
    }


    private void handleNeighborEditing(List<String> p_UserInputTokens, String p_UserAction) throws Exception {
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
            throw new Exception();
        }
    }

    private void handleListMaps(List<String> p_UserInputTokens) throws WarzoneBaseException {
        if (p_UserInputTokens.size() > 1) {
            throw new WarzoneBaseException("Invalid command of list maps given");
        }
    }

    private void handleMapValidation(List<String> p_UserInputTokens) throws Exception {
        if (p_UserInputTokens.size() > 1) {
            throw new Exception("Invalid command of validate map given");
        }

        if (GameWorldValidator.validateMap(d_GameSession)) {
            System.out.println("Current game map is valid");
        } else {
            System.out.println("Current game map is invalid");
        }
    }

    private void handleSaveMap(List<String> p_UserInputTokens) throws Exception {
        if (p_UserInputTokens.size() > 1) {
            throw new Exception("Invalid command of save map given");
        }
        String l_FileName = p_UserInputTokens.get(1) + WarzoneConstants.GAME_MAP_EXTENSION;
        // check if the file exists
        String l_FilePath = WarzoneConstants.GAME_WORLDS + WarzoneConstants.FORWARD_SLASH + l_FileName;
        File l_File = new File(l_FilePath);
        if (l_File.exists()) {
            throw new Exception("File with name " + l_FileName + " already exists");
        }

        if (!GameWorldValidator.validateMap(d_GameSession)) {
            throw new Exception("The map is not valid to save");
        }
        try (OutputStream l_NewMapFile = new FileOutputStream(l_File)) {
            MapDataHandler l_MapDataHandler = new MapDataHandlerImpl();
            l_MapDataHandler.saveGameMap(l_NewMapFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to create new map file", e);
        }
    }

    private void handleEditMap(List<String> p_UserInputTokens) throws Exception, WarzoneBaseException {
        if (p_UserInputTokens.size() > 1) {
            throw new Exception("Invalid command of edit map given");
        }
        String l_MapFileName = p_UserInputTokens.get(1);
        InputStream l_GameMap = null;
        try {
            l_GameMap = FileUtils.getStreamFromGameFile(l_MapFileName);
        } catch (FileNotFoundException e) {
            System.out.println("Failed to edit the map file given:" + l_MapFileName);
            throw new WarzoneBaseException("Unable to edit map file given");
        }
        MapDataHandler l_MapDataHandler = new MapDataHandlerImpl();
        l_MapDataHandler.createGameMap(l_GameMap);
    }

}

