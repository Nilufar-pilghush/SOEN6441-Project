package main.java.warzone.services.impl;

import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.GamePhase;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.Player;
import main.java.warzone.entities.TournamentConfig;
import main.java.warzone.entities.players.AggressivePlayerStrategy;
import main.java.warzone.entities.players.BenevolentPlayerStrategy;
import main.java.warzone.entities.players.CheaterPlayerStrategy;
import main.java.warzone.entities.players.RandomPlayerStrategy;
import main.java.warzone.exceptions.WarzoneRuntimeException;
import main.java.warzone.exceptions.WarzoneValidationException;
import main.java.warzone.services.GameMapDataHandler;
import main.java.warzone.services.GamePhaseService;
import main.java.warzone.services.io.ConquestAdaptee;
import main.java.warzone.services.io.ConquestAdapter;
import main.java.warzone.services.io.DominationMapDataHandlerImpl;
import main.java.warzone.utils.FileUtils;
import main.java.warzone.utils.GameSessionValidator;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Realization of the {@link GamePhaseService} that deals with the phase tied to tournament.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */

public class TournamentServiceImpl implements GamePhaseService {

    /**
     * Current game session instance
     */
    private GameSession d_GameSession;

    /**
     * LogEntryBuffer object to log the information
     * and notifying all the observers
     */
    private LogEntryBuffer d_LogEntryBuffer;


    /**
     * Constructor to initialize TournamentService
     */
    public TournamentServiceImpl() {
        d_GameSession = GameSession.getInstance();
        d_LogEntryBuffer = LogEntryBuffer.getInstance();
    }

    @Override
    public GamePhase handleGamePhase(GamePhase p_CurrPhase) {
        TournamentConfig l_TournamentConfig = d_GameSession.getTournamentConfig();
        int l_NumOfGames = l_TournamentConfig.getNumberOfGames();
        int l_NumOfMaxTurns = l_TournamentConfig.getNumberOfMaxTurns();
        String[] l_MapFiles = l_TournamentConfig.getMapFiles();
        String[] l_PlayerStrategies = l_TournamentConfig.getPlayerStrategies();
        Player l_CurrentGameWinner = d_GameSession.getTournamentCurrentGameWinner();
        int l_CurrentGame = d_GameSession.getTournamentCurrentGame();
        int l_CurrentTurn = d_GameSession.getTournamentCurrentTurn();
        int l_CurrentMapFile = d_GameSession.getTournamentCurrentMap();
        d_LogEntryBuffer.logData("Tournament service handler");
        if (l_CurrentGameWinner != null) {
            l_TournamentConfig.addTournamentResult(l_MapFiles[l_CurrentMapFile], l_CurrentGame, l_CurrentGameWinner.getName());
            return startNewRound(p_CurrPhase);
        }
        else if(l_CurrentGame == -1){
            d_LogEntryBuffer.logData("Starting tournament...");
            return startNewRound(p_CurrPhase);
        }
        else if(l_CurrentTurn > l_NumOfMaxTurns){
            d_LogEntryBuffer.logData("Max turns reached. Starting new game...");
            l_TournamentConfig.addTournamentResult(l_MapFiles[l_CurrentMapFile], l_CurrentGame, "draw");
            return startNewRound(p_CurrPhase);
        } else {
            d_LogEntryBuffer.logData("Starting new turn...");
            d_GameSession.setTournamentCurrentTurn(l_CurrentTurn + 1);
            return p_CurrPhase.validateAndMoveToNextState(GamePhase.MAIN_GAME_LOOP);
        }

    }

    /**
     * Method to start a new round
     *
     * @param p_CurrPhase Current game phase
     * @return Upcoming game phase
     */
    private GamePhase startNewRound(GamePhase p_CurrPhase) {
        TournamentConfig l_TournamentConfig = d_GameSession.getTournamentConfig();
        String[] l_PlayerStrategies = l_TournamentConfig.getPlayerStrategies();
        String[] l_MapFiles = l_TournamentConfig.getMapFiles();
        int l_NumOfGames = l_TournamentConfig.getNumberOfGames();
        int l_CurrentGame = d_GameSession.getTournamentCurrentGame();
        int l_CurrentMapFile = d_GameSession.getTournamentCurrentMap();
        int l_CurrentTurn = d_GameSession.getTournamentCurrentTurn();
        if((l_CurrentGame >= l_NumOfGames-1)){
            if(d_GameSession.getTournamentCurrentMap() >= (l_MapFiles.length - 1)){
                d_LogEntryBuffer.logData("Tournament over. Printing results...");
                l_TournamentConfig.printTournamentResultsInTableFormat();
                return p_CurrPhase.validateAndMoveToNextState(GamePhase.EXIT);
            } else {
                l_CurrentMapFile++;
                l_CurrentGame = 0;
                l_CurrentTurn = 0;
                d_LogEntryBuffer.logData("Starting new map...");
            }
        } else {
            l_CurrentGame++;
            l_CurrentTurn = 0;
            d_LogEntryBuffer.logData("Starting new game...");
        }
        d_GameSession.clearPreviousSession();
        d_GameSession.setTournamentCurrentGame(l_CurrentGame);
        d_GameSession.setTournamentCurrentMap(l_CurrentMapFile);
        d_GameSession.setTournamentCurrentTurn(l_CurrentTurn);
        d_GameSession.setTournamentCurrentGameWinner(null);
        try {
            loadMapHandler(l_MapFiles[l_CurrentMapFile]);
            addGamePlayers();
            assignCountriesToPlayers();
        } catch (WarzoneValidationException e) {
            d_LogEntryBuffer.logData(e.getMessage());
            return p_CurrPhase.validateAndMoveToNextState(GamePhase.EXIT);
        } catch (WarzoneRuntimeException e) {
            d_LogEntryBuffer.logData(e.getMessage());
            return p_CurrPhase.validateAndMoveToNextState(GamePhase.EXIT);
        }

        return p_CurrPhase.validateAndMoveToNextState(GamePhase.REINFORCEMENT);
    }

    /**
     * This method is used to load the map from the user input file
     *
     * @param p_Filename the user input command
     * @throws WarzoneValidationException throws exception if the user input is invalid or file not found
     */
    private void loadMapHandler(String p_Filename) throws WarzoneValidationException, WarzoneRuntimeException {
        InputStream l_GameSceneMap = null;

        try {
            l_GameSceneMap = FileUtils.getStreamFromGameFile(p_Filename);
        } catch (FileNotFoundException e) {
            throw new WarzoneRuntimeException("Unable to find map!");
        }

        boolean conquestMap = false;
        // Only domination maps for tourneys

        GameMapDataHandler l_GameMapDataHandler = conquestMap ? new ConquestAdapter(new ConquestAdaptee()) : new DominationMapDataHandlerImpl();
        l_GameMapDataHandler.makeGameSession(l_GameSceneMap);

        if (!GameSessionValidator.validateMap(d_GameSession)) {
            d_GameSession.clearPreviousSession();
            throw new WarzoneValidationException("Game session is not valid to play.");
        }
        d_LogEntryBuffer.logData("...Enter 'help' at any time to view available commands in this phase...");
    }

    private void addGamePlayers() throws WarzoneValidationException {
        TournamentConfig l_TournamentConfig = d_GameSession.getTournamentConfig();
        String[] l_PlayerStrategies = l_TournamentConfig.getPlayerStrategies();
        for (String l_PlayerStrategy : l_PlayerStrategies) {
            switch (l_PlayerStrategy) {
                case WarzoneConstants.AGGRESSIVE -> {
                    d_GameSession.createPlayer(l_PlayerStrategy, new AggressivePlayerStrategy());
                }
                case WarzoneConstants.BENEVOLENT -> {
                    d_GameSession.createPlayer(l_PlayerStrategy, new BenevolentPlayerStrategy());
                }
                case WarzoneConstants.CHEATER -> {
                    d_GameSession.createPlayer(l_PlayerStrategy, new CheaterPlayerStrategy());
                }
                case WarzoneConstants.RANDOM -> {
                    d_GameSession.createPlayer(l_PlayerStrategy, new RandomPlayerStrategy());
                }
                default -> {
                    throw new WarzoneValidationException("Invalid player strategy: " + l_PlayerStrategy);
                }
            }
        }
    }

    /**
     * Method to assign game session countries to the players.
     *
     * @throws WarzoneValidationException If assignment of country to player fails.
     */
    private void assignCountriesToPlayers() throws WarzoneValidationException {
        // Only assign countries if players are added
        if (!GameSessionValidator.arePlayersAdded(d_GameSession)) {
            d_LogEntryBuffer.logData("Please add players first");
            return;
        }
        List<String> l_UnownedCountries = new ArrayList<>();
        l_UnownedCountries.addAll(d_GameSession.getCountriesInSession().keySet());

        List<String> l_PlayerNames = new ArrayList<>();
        l_PlayerNames.addAll(d_GameSession.getPlayers().keySet());

        int l_CountriesPerPlayer = Math.floorDiv(l_UnownedCountries.size(), l_PlayerNames.size());
        int l_RemainingCountries = l_UnownedCountries.size() % l_PlayerNames.size();

        for (String l_PlayerName : l_PlayerNames) {
            for (int i = 0; i < l_CountriesPerPlayer; i++) {
                int l_RandomIndex = (int) (Math.random() * l_UnownedCountries.size());
                String l_RandomCountry = l_UnownedCountries.get(l_RandomIndex);
                d_GameSession.assignCountryToPlayer(l_PlayerName, l_RandomCountry);
                l_UnownedCountries.remove(l_RandomIndex);
            }
        }
        d_LogEntryBuffer.logData("Countries assigned to players successfully");
        d_LogEntryBuffer.logData("Remaining unowned countries: " + (l_UnownedCountries.size()));

    }
}