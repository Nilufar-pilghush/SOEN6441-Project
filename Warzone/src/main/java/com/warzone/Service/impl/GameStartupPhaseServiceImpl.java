package main.java.com.warzone.Service.impl;

import main.java.com.warzone.constants.WarzoneConstants;
import main.java.com.warzone.Entities.GamePhase;
import main.java.com.warzone.Entities.GameSession;
import main.java.com.warzone.Exceptions.WarzoneBaseException;
import main.java.com.warzone.Exceptions.WarzoneRuntimeException;
import main.java.com.warzone.Exceptions.WarzoneValidationException;
import main.java.com.warzone.Service.GameMapDataHandler;
import main.java.com.warzone.Service.GamePhaseService;
import main.java.com.warzone.Service.impl.GameMapDataHandlerImpl;
import main.java.com.warzone.utils.CmdUtils;
import main.java.com.warzone.utils.FileUtils;
import main.java.com.warzone.utils.GameWorldValidator;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Realization of {@link GamePhaseService} that represents the game's startup phase.
 *
 * @author Snehil Sharma
 * @author Jatin
 * @author Kenish Rajeshbhai Halani
 * @author Udhisha
 * @author Aazam Arvind
 * @version 1.0.0
 */
public class GameStartupPhaseServiceImpl implements GamePhaseService {

    /**
     * Singleton instance of the game world.
     */
    private GameSession d_CurrGameSession;

    /**
     * Constructor to initialize GameStartupPhaseService
     */
    public GameStartupPhaseServiceImpl() {
        d_CurrGameSession = GameSession.getInstance();
    }


    /**
     * Handles the game segment based on user input.
     *
     * @param p_CurrSegment Current game segment.
     * @return Next game segment after handling.
     */
    @Override
    public GamePhase handleGamePhase(GamePhase p_CurrSegment) {
        Scanner l_InputScanner = new Scanner(System.in);
        System.out.println();
        System.out.println("************ Welcome to Game Startup Phase ************");
        System.out.println("...Enter 'help' at any time to view available commands in this phase...");
        while (true) {
            try {
                String l_UserInput = l_InputScanner.nextLine();
                List<String> l_UserInputParts = CmdUtils.getUserInputParts(l_UserInput);
                String l_PrimaryAction = l_UserInputParts.get(0).toLowerCase();
                switch (l_PrimaryAction) {
                    case WarzoneConstants.LIST_MAPS -> {
                        listMapsHandler(l_UserInputParts);
                    }
                    case WarzoneConstants.LOAD_MAP -> {
                        loadMapHandler(l_UserInputParts);
                    }
                    case WarzoneConstants.GAME_PLAYER -> {
                        gamePlayerHandler(l_UserInputParts);
                    }
                    case WarzoneConstants.ASSIGN_COUNTRIES -> {
                        assignCountriesToPlayers();
                    }
                    case WarzoneConstants.HELP -> {
                        displayHelpCommandsForGamePhase();
                    }
                    case WarzoneConstants.EXIT -> {
                        return GamePhase.MAIN_GAME_LOOP;
                    }
                    default -> {
                        System.out.println("Command not found--> " + l_PrimaryAction);
                        displayHelpCommandsForGamePhase();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                displayHelpCommandsForGamePhase();
            }
        }
    }

    /**
     * This method is used to view the existing maps for efficient game play
     *
     * @param p_UserInputParts the user input command
     * @throws WarzoneValidationException if the given command is invalid
     * @throws WarzoneRuntimeException if the game worlds directory with maps is not present
     */
    private void listMapsHandler(List<String> p_UserInputParts) throws WarzoneValidationException, WarzoneRuntimeException {
        if (p_UserInputParts.size() > 1) {
            throw new WarzoneValidationException("Invalid listmaps command!");
        }
        try {
            FileUtils.listMaps();
        } catch (FileNotFoundException e) {
            throw new WarzoneRuntimeException("Unable to find gameworlds directory.");
        }
    }

    /**
     * This method is used to load the map from the user input file
     *
     * @param p_UserInputParts the user input command
     * @throws WarzoneValidationException throws exception if the user input is invalid or file not found
     */
    private void loadMapHandler(List<String> p_UserInputParts) throws Exception {
        if (p_UserInputParts.size() <= 1) {
            throw new WarzoneValidationException("Invalid loadmap command!");
        }
        String l_GameFileName = p_UserInputParts.get(1);
        InputStream l_GameSceneMap = null;

        try {
            l_GameSceneMap = FileUtils.getStreamFromGameFile(l_GameFileName);
        } catch (FileNotFoundException e) {
            throw new WarzoneRuntimeException("Unable to find map!");
        }

        GameMapDataHandler l_GameMapDataManager = new GameMapDataHandlerImpl();
        l_GameMapDataManager.makeGameSession(l_GameSceneMap);

        if(!GameWorldValidator.validateMap(d_CurrGameSession)){
            d_CurrGameSession.clearPreviousSession();
            throw new WarzoneValidationException("Game world is not valid to play.");
        }
        System.out.println("...Enter 'help' at any time to view available commands in this phase...");
    }

    /**
     * This method is used to add/remove a player in the game
     *
     * @param p_UserInputParts the user input command
     * @throws WarzoneValidationException throws exception if the user input is invalid
     */
    private void gamePlayerHandler(List<String> p_UserInputParts) throws WarzoneValidationException {
        if (p_UserInputParts.size() <= 1) {
            throw new WarzoneValidationException("Invalid gameplayer command!");
        }
        String[] l_SubActions = p_UserInputParts.get(1).split(WarzoneConstants.SPACE_REGEX);
        String l_SubAction = l_SubActions[0];

        // add or remove players based on input command
        switch (l_SubAction) {
            case WarzoneConstants.ADD -> {
                // Only add player if map is loaded
                if(GameWorldValidator.isWorldEmpty(d_CurrGameSession)){
                    System.out.println("Please load a map first");
                    return;
                }
                d_CurrGameSession.createPlayer(l_SubActions[1]);
            }
            case WarzoneConstants.REMOVE -> {
                d_CurrGameSession.deletePlayer(l_SubActions[1]);
            }
            default -> {
                System.out.println("Sub command not found--> " + l_SubAction);
                displayHelpCommandsForGamePhase();
            }
        }
    }

    /**
     * Method to assign game world countries to the players.
     *
     * @throws WarzoneValidationException If assignment of country to player fails.
     */
    private void assignCountriesToPlayers() throws WarzoneValidationException {
        // Only assign countries if players are added
        if(!GameWorldValidator.arePlayersAdded(d_CurrGameSession)){
            System.out.println("Please add players first");
            return;
        }
        List<String> l_UnownedCountries = new ArrayList<>();
        l_UnownedCountries.addAll(d_CurrGameSession.getCountriesInSession().keySet());

        List<String> l_PlayerNames = new ArrayList<>();
        l_PlayerNames.addAll(d_CurrGameSession.getPlayers().keySet());

        int l_CountriesPerPlayer = Math.floorDiv(l_UnownedCountries.size(), l_PlayerNames.size());
        int l_RemainingCountries = l_UnownedCountries.size() % l_PlayerNames.size();

        for (String l_PlayerName : l_PlayerNames) {
            for (int i = 0; i < l_CountriesPerPlayer; i++) {
                int l_RandomIndex = (int) (Math.random() * l_UnownedCountries.size());
                String l_RandomCountry = l_UnownedCountries.get(l_RandomIndex);
                d_CurrGameSession.assignCountryToPlayer(l_PlayerName, l_RandomCountry);
                l_UnownedCountries.remove(l_RandomIndex);
            }
        }
        System.out.println("Countries assigned to players successfully");
        System.out.println("Remaining unowned countries: " + (l_UnownedCountries.size()));

    }

    /**
     * Displays the help commands for the current game segment.
     */
    private void displayHelpCommandsForGamePhase() {
        System.out.println(".......................................Startup Phase Commands..........................................");
        System.out.println("To list existing maps: listmaps");
        System.out.println("To load a map: loadmap filename");
        System.out.println("To edit game players: gameplayer -add playername -remove playername");
        System.out.println("To assign countries to players: assigncountries");
        System.out.println("To exit startup phase & start the game: exit");
        System.out.println(".......................................................................................................");
    }
}
