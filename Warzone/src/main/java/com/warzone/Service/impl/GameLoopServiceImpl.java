package main.java.com.warzone.Service.impl;

import main.java.com.warzone.Entities.Country;
import main.java.com.warzone.Entities.GamePhase;
import main.java.com.warzone.Entities.GameSession;
import main.java.com.warzone.Service.GamePhaseService;
import main.java.com.warzone.utils.CmdUtils;

import java.util.*;

/**
 * Implements the {@link GamePhaseService} interface. Used to handle the different phases in the game loop.
 */

public class GameLoopServiceImpl implements GamePhaseService {

    /**
     * Instance of the current game session
     */
    private GameSession d_GameSession;

    /**
     * Initialization constructor for the GameLoopService
     */
    public GameLoopServiceImpl() {
        d_GameSession = GameSession.getInstance();
    }

    /**
     * Method to process user commands within the current game phase
     *
     * @param p_CurrPhase Current game phase.
     * @return Next or current game phase based on user input.
     */
    @Override
    public GamePhase handleGamePhase(GamePhase p_CurrPhase) {
        System.out.println("Game Loop Service Controller");
        Scanner l_InputScanner = new Scanner(System.in);
        GamePhase l_NextPhase = p_CurrPhase.getNextPhaseInLoop(d_GameSession.getCurrGamePhase());
        while (true) {
            displayHelpCommandsMenu(l_NextPhase);
            String l_UserInput = l_InputScanner.nextLine();
            List<String> l_UserInputTokens = CmdUtils.tokenizeUserInput(l_UserInput);
            try {
                String l_PrimaryCommand = l_UserInputTokens.get(0).toLowerCase();
                switch (l_PrimaryCommand) {
                    case "showmap" -> {
                        showMap();
                    }
                    case "help" -> {
                        // will display help text
                    }
                    case "exit" -> {
                        System.out.println("Exit game");
                        return GamePhase.EXIT;
                    }
                    default -> {
                        d_GameSession.setCurrGamePhase(l_NextPhase);
                    }
                }
            }
            // Proceed with game if no input is given
            catch (IndexOutOfBoundsException e) {
                d_GameSession.setCurrGamePhase(l_NextPhase);
            }
        }
    }

    private void displayHelpCommandsMenu(GamePhase p_NextPhase) {
        System.out.println("*************************Main Game Loop*************************");
        System.out.println("  Current phase: " + d_GameSession.getCurrGamePhase());
        System.out.println("  Next phase: " + p_NextPhase);
        System.out.println("  Enter 'showmap' to view the map.");
        System.out.println("  Enter 'exit' to exit the game.");
        System.out.println("  Enter 'help' to display these instructions.");
        System.out.println("  Enter nothing to continue with the game.");
        System.out.println("*******************************************************************");
    }

    private void showMap() {
        System.out.println("Showing map");
        System.out.println();

        //Display players
        System.out.println("Players");
        for (String l_Player : d_GameSession.getPlayers().keySet()) {
            System.out.println();
            System.out.println("-" + l_Player);
            System.out.println("--Armies " + d_GameSession.getPlayers().get(l_Player).getNumberOfArmies());
            System.out.println("--Owned countries:");
            for (String l_Country : d_GameSession.getPlayers().get(l_Player).getOwnedCountries()) {
                System.out.println("----" + l_Country);
            }
        }
        System.out.println();
        System.out.println();

        // Display Continents
        System.out.println("Continents:");
        for (String l_ContinentName : d_GameSession.getContinentsInSession().keySet()) {
            System.out.println("-" + l_ContinentName);
            System.out.println("--Countries:");

            Iterator<Map.Entry<String, Country>> l_CountriesInContinent = d_GameSession.getContinentsInSession().get(l_ContinentName).getCountries().entrySet().iterator();
            while (l_CountriesInContinent.hasNext()) {
                Map.Entry<String, Country> l_Country = l_CountriesInContinent.next();
                System.out.println("----" + l_Country.getKey());
                System.out.println("------Armies: " + l_Country.getValue().getNumberOfArmies());
                System.out.println("------Player owner: " + l_Country.getValue().getOwner());
                System.out.println("------Adjacent countries:");
                for (String l_AdjacentCountry : l_Country.getValue().getAdjacentCountries().values()) {
                    System.out.println("------" + l_AdjacentCountry);
                }
            }
        }
    }
}
