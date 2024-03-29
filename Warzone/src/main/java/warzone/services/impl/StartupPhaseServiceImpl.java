package main.java.warzone.services.impl;
import main.java.warzone.constants.WarzoneConstants;
        import main.java.warzone.exceptions.WarzoneRuntimeException;
        import main.java.warzone.exceptions.WarzoneValidationException;
        import main.java.warzone.services.GameMapDataHandler;
        import main.java.warzone.services.io.GameMapDataHandlerImpl;
        import main.java.warzone.entities.GamePhase;
        import main.java.warzone.entities.GameSession;
        import main.java.warzone.services.GamePhaseService;
        import main.java.warzone.utils.CmdUtils;
        import main.java.warzone.utils.FileUtils;
        import main.java.warzone.utils.GameSessionValidator;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;

import java.io.FileNotFoundException;
        import java.io.InputStream;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Scanner;


/**
 * Implements the {@link GamePhaseService} that represents the main.java.game's startup phase.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class StartupPhaseServiceImpl implements GamePhaseService {

    /**
     *Instance of the main.java.game session.
     */
    private GameSession d_CurrGameSession;

    /**
     * LogEntryBuffer object to log the information and
     * notify all the observers
     */
    private LogEntryBuffer d_LogEntryBuffer;

    /**
     * Constructor to initialize GameStartupPhaseService
     */
    public StartupPhaseServiceImpl() {
        d_CurrGameSession = GameSession.getInstance();
        d_LogEntryBuffer = LogEntryBuffer.getInstance();
    }


    /**
     * Handles the main.java.game phase based on user input.
     *
     * @param p_CurrPhase Current main.java.game phase.
     * @return Next main.java.game phase after handling.
     */
    @Override
    public GamePhase handleGamePhase(GamePhase p_CurrPhase) {
        Scanner l_InputScanner = new Scanner(System.in);
        d_LogEntryBuffer.logData("");
        d_LogEntryBuffer.logData("************ Welcome to Game Startup Phase ************");
        d_LogEntryBuffer.logData("...Enter 'help' at any time to view available commands in this phase...");
        while (true) {
            try {
                String l_UserInput = l_InputScanner.nextLine();
                List<String> l_UserInputParts = CmdUtils.tokenizeUserInput(l_UserInput);
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
                        return p_CurrPhase.validateAndMoveToNextState(GamePhase.MAIN_GAME_LOOP);
                    }
                    default -> {
                        d_LogEntryBuffer.logData("Command not found--> " + l_PrimaryAction);
                        displayHelpCommandsForGamePhase();
                    }
                }
            } catch (Exception e) {
                d_LogEntryBuffer.logData(e.getMessage());
                displayHelpCommandsForGamePhase();
            }
        }
    }

    /**
     * This method is used to view the existing maps for efficient main.java.game play
     *
     * @param p_UserInputParts the user input command
     * @throws WarzoneValidationException if the given command is invalid
     * @throws WarzoneRuntimeException if the main.java.game sessions directory with maps is not present
     */
    private void listMapsHandler(List<String> p_UserInputParts) throws WarzoneValidationException, WarzoneRuntimeException {
        if (p_UserInputParts.size() > 1) {
            throw new WarzoneValidationException("Invalid listmaps command!");
        }
        try {
            FileUtils.listMaps();
        } catch (FileNotFoundException e) {
            throw new WarzoneRuntimeException("Unable to find game sessions directory.");
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
        InputStream l_GameMap = null;

        try {
            l_GameMap = FileUtils.getStreamFromGameFile(l_GameFileName);
        } catch (FileNotFoundException e) {
            throw new WarzoneRuntimeException("Unable to find map!");
        }

        GameMapDataHandler l_GameMapDataManager = new GameMapDataHandlerImpl();
        l_GameMapDataManager.createGameMap(l_GameMap);

        if(!GameSessionValidator.validateMap(d_CurrGameSession)){
            d_CurrGameSession.clearPreviousSession();
            throw new WarzoneValidationException("Game session is not valid to play.");
        }
        d_LogEntryBuffer.logData("...Enter 'help' at any time to view available commands in this phase...");
    }

    /**
     * This method is used to add/remove a player in the main.java.game
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
                if(GameSessionValidator.isSessionEmpty(d_CurrGameSession)){
                    d_LogEntryBuffer.logData("Please load a map first");
                    return;
                }
                d_CurrGameSession.createPlayer(l_SubActions[1]);
            }
            case WarzoneConstants.REMOVE -> {
                d_CurrGameSession.deletePlayer(l_SubActions[1]);
            }
            default -> {
                d_LogEntryBuffer.logData("Sub command not found--> " + l_SubAction);
                displayHelpCommandsForGamePhase();
            }
        }
    }

    /**
     * Method to assign main.java.game session countries to the players.
     *
     * @throws WarzoneValidationException If assignment of country to player fails.
     */
    private void assignCountriesToPlayers() throws WarzoneValidationException {
        // Only assign countries if players are added
        if(!GameSessionValidator.arePlayersAdded(d_CurrGameSession)){
            d_LogEntryBuffer.logData("Please add players first");
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
        d_LogEntryBuffer.logData("Countries assigned to players successfully");
        d_LogEntryBuffer.logData("Remaining unowned countries: " + (l_UnownedCountries.size()));

    }

    /**
     * Displays the help commands for the current main.java.game phase.
     */
    private void displayHelpCommandsForGamePhase() {
        d_LogEntryBuffer.logData(".......................................Startup Phase Commands..........................................");
        d_LogEntryBuffer.logData("To list existing maps: listmaps");
        d_LogEntryBuffer.logData("To load a map: loadmap filename");
        d_LogEntryBuffer.logData("To edit game players: gameplayer -add playername -remove playername");
        d_LogEntryBuffer.logData("To assign countries to players: assigncountries");
        d_LogEntryBuffer.logData("To exit startup phase & start the game: exit");
        d_LogEntryBuffer.logData(".......................................................................................................");
    }
}