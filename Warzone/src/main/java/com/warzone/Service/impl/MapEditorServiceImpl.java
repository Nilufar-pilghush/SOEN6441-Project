package main.java.com.warzone.Service.impl;

import main.java.com.warzone.Entities.Country;
import main.java.com.warzone.Entities.GamePhase;
import main.java.com.warzone.Entities.GameSession;
import main.java.com.warzone.Service.GamePhaseService;

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
                List<String> l_UserInputTokens = new ArrayList<>();
                if (l_UserInput.contains("-")) {
                    String[] l_HyphenSplit = l_UserInput.split("-");
                    for (String l_Part : l_HyphenSplit) {
                        l_UserInputTokens.add(l_Part.trim());
                    }
                } else {
                    String[] l_SpaceSplit = l_UserInput.split("\\s+");
                    l_UserInputTokens.addAll(Arrays.asList(l_SpaceSplit));
                }
                String l_UserAction = l_UserInputTokens.get(0).toLowerCase();
                switch (l_UserAction) {
                    case "editcontinent" -> {
                        // method to edit continents
                    }
                    case "editcountry" -> {
                        // method to edit countries
                    }
                    case "editneighbor" -> {
                        // method to edit neighboring countries
                    }
                    case "showmap" -> {
                        showMap((l_UserInputTokens);
                    }
                    case "listmaps" -> {
                        // method to list all the maps in the current game directory
                    }
                    case "savemap" -> {
                        // method to save the map to a file
                    }
                    case "editmap" -> {
                        // method to handle map editing
                    }
                    case "validateMap" -> {
                        // method to handle map validation
                    }
                    case "editmap" -> {
                        displayCommandsForMapEditor();
                    }
                    case "exit" -> {
                        return GamePhase.START_UP;
                    }
                    default -> {
                        System.out.println("The command '" + l_UserAction + "' is not found.");
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
}
