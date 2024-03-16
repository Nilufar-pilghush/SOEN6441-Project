package main.java.warzone.services.impl;

import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.Country;
import main.java.warzone.entities.GamePhase;
import main.java.warzone.entities.GameSession;
import main.java.warzone.services.GamePhaseService;
import main.java.warzone.utils.CmdUtils;

import java.util.*;

/**
 * Implements the {@link GamePhaseService} interface. Used to handle the different phases in the main.java.game loop.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */

public class GameLoopServiceImpl implements GamePhaseService {

    /**
     * Current game session instamce
     */
    private GameSession d_GameSession;

    /**
     * Constructor to initialize MainGameLoopService
     */
    public GameLoopServiceImpl(){
        d_GameSession = GameSession.getInstance();
    }

    @Override
    public GamePhase handleGamePhase(GamePhase p_CurrPhase) {
        System.out.println("Main game loop service handler");
        Scanner l_InputScanner = new Scanner(System.in);
        GamePhase l_NextGamePhase = p_CurrPhase.getNextPhaseInMainGameLoop(d_GameSession.getCurrGamePhase());
        while (true) {
            displayHelpCommandsForGamePhase(l_NextGamePhase);
            String l_UserInput = l_InputScanner.nextLine();
            List<String> l_UserInputParts = CmdUtils.tokenizeUserInput(l_UserInput);
            try{
                String l_PrimaryAction = l_UserInputParts.get(0).toLowerCase();
                switch (l_PrimaryAction) {
                    case WarzoneConstants.SHOW_MAP -> {
                        showMap();
                    }
                    case WarzoneConstants.HELP -> {
                        // Do nothing
                    }
                    case WarzoneConstants.EXIT -> {
                        return GamePhase.EXIT;
                    }
                    default -> {
                        return proceedToGamePhase(l_NextGamePhase);
                    }
                }
            }
            // In case empty input is given
            catch(IndexOutOfBoundsException e){
                proceedToGamePhase(l_NextGamePhase);
            }

        }
    }

    private void displayHelpCommandsForGamePhase(GamePhase p_nextPhase) {
        System.out.println(".......................................MainGameLoop..........................................");
        System.out.println("Current phase: " + d_GameSession.getCurrGamePhase());
        System.out.println("Next phase in queue: " + p_nextPhase);
        System.out.println("...Enter 'showmap' at any time to view the current state of the map...");
        System.out.println("...Enter 'exit' at any time to exit the game...");
        System.out.println("...Enter 'help' show this message...");
        System.out.println("...Enter nothing to continue to the next phase...");
        System.out.println("....................................................................................................");

    }

    private void showMap() {
        System.out.println("Showing map");
        // Print all players
        System.out.println();
        System.out.println("--Players:");
        for (String l_PlayerName : d_GameSession.getPlayers().keySet()) {
            System.out.println();
            System.out.println("--"+l_PlayerName);
            System.out.println("----Armies: "+ d_GameSession.getPlayers().get(l_PlayerName).getNumberOfArmies());
            System.out.println("----Owned countries:");
            for (String l_CountryName : d_GameSession.getPlayers().get(l_PlayerName).getOwnedCountries()) {
                System.out.println("------" + l_CountryName);
            }
        }

        System.out.println();
        System.out.println();
        // Print all continents
        System.out.println("---Continents:");
        for (String l_ContinentName : d_GameSession.getContinentsInSession().keySet()) {
            System.out.println();
            System.out.println("--"+l_ContinentName);
            System.out.println("----Countries:");

            Iterator<Map.Entry<String, Country>> l_CountryIterator = d_GameSession.getContinentsInSession().get(l_ContinentName).getCountries().entrySet().iterator();
            while (l_CountryIterator.hasNext()) {
                Map.Entry<String, Country> l_CountryEntry = l_CountryIterator.next();
                System.out.println("----"+l_CountryEntry.getKey());
                System.out.println("------Armies: "+l_CountryEntry.getValue().getNumberOfArmies());
                System.out.println("------Owned by:");
                System.out.println("--------"+l_CountryEntry.getValue().getOwner());
                System.out.println("------Adjacent countries:");
                for (String l_AdjacentCountryName : l_CountryEntry.getValue().getAdjacentCountries().values()) {
                    System.out.println("--------"+l_AdjacentCountryName);
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
    private GamePhase proceedToGamePhase(GamePhase p_NextPhase){
        d_GameSession.setCurrGamePhase(p_NextPhase);
        return p_NextPhase;
    }


}


