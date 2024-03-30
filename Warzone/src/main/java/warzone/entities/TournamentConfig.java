package main.java.warzone.entities;

import main.java.warzone.utils.logging.impl.LogEntryBuffer;

import java.io.Serializable;
import java.util.*;

/**
 * Concrete class to access and modify the properties of a tournament.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */
public class TournamentConfig implements Serializable {

    /**
     * LogEntryBuffer object to log the information
     * and notifying all the observers
     */
    private LogEntryBuffer d_LogEntryBuffer;

    /**
     * Number of games to be played
     */
    private int d_NumberOfGames;

    /**
     * Number of max turns per game
     */
    private int d_NumberOfMaxTurns;

    /**
     * List of map files
     */
    private String[] d_MapFiles;

    /**
     * List of player strategies
     */
    private String[] d_PlayerStrategies;


    /**
     * Tournament results
     */
    private Map<String, String[]> d_TournamentResults;

    /**
     * Constructor to instantiate tournament config
     */
    public TournamentConfig() {
        this.d_LogEntryBuffer = LogEntryBuffer.getInstance();
        this.d_TournamentResults = new HashMap<>();

    }


    /**
     * Constructor to instantiate tournament config
     *
     * @param p_NumberOfGames    Number of games to be played
     * @param p_NumberOfMaxTurns Number of max turns per game
     * @param p_MapFiles         List of map files
     * @param p_PlayerStrategies List of player strategies
     */
    public TournamentConfig(int p_NumberOfGames, int p_NumberOfMaxTurns, String[] p_MapFiles, String[] p_PlayerStrategies) {
        this();
        this.d_NumberOfGames = p_NumberOfGames;
        this.d_NumberOfMaxTurns = p_NumberOfMaxTurns;
        this.d_MapFiles = p_MapFiles;
        this.d_PlayerStrategies = p_PlayerStrategies;
        for (String mapFile : p_MapFiles) {
            this.d_TournamentResults.put(mapFile, new String[p_NumberOfGames]);
        }
    }

    /**
     * Method to get number of games
     *
     * @return the d_NumberOfGames
     */
    public int getNumberOfGames() {
        return d_NumberOfGames;
    }

    /**
     * @param p_NumberOfGames the d_NumberOfGames to set
     */
    public void setNumberOfGames(int p_NumberOfGames) {
        this.d_NumberOfGames = p_NumberOfGames;
    }

    /**
     * @return the d_NumberOfMaxTurns
     */
    public int getNumberOfMaxTurns() {
        return d_NumberOfMaxTurns;
    }

    /**
     * @param p_NumberOfMaxTurns the d_NumberOfMaxTurns to set
     */
    public void setNumberOfMaxTurns(int p_NumberOfMaxTurns) {
        this.d_NumberOfMaxTurns = p_NumberOfMaxTurns;
    }

    /**
     * @return the d_MapFiles
     */
    public String[] getMapFiles() {
        return d_MapFiles;
    }

    /**
     * @param p_MapFiles the d_MapFiles to set
     */
    public void setMapFiles(String[] p_MapFiles) {
        this.d_MapFiles = p_MapFiles;
    }

    /**
     * @return the d_PlayerStrategies
     */
    public String[] getPlayerStrategies() {
        return d_PlayerStrategies;
    }

    /**
     * @param p_PlayerStrategies the d_PlayerStrategies to set
     */
    public void setPlayerStrategies(String[] p_PlayerStrategies) {
        this.d_PlayerStrategies = p_PlayerStrategies;
    }


    /**
     * @return the d_TournamentResults
     */
    public Map<String, String[]> getTournamentResults() {
        return d_TournamentResults;
    }

    /**
     * Method to add tournament result
     *
     * @param p_MapFile    Map file
     * @param p_GameNumber Number of the game
     * @param p_WinnerName Name of the winner
     */
    public void addTournamentResult(String p_MapFile, int p_GameNumber, String p_WinnerName) {
        if(this.d_TournamentResults.get(p_MapFile) == null){
            this.d_TournamentResults.put(p_MapFile, new String[this.d_NumberOfGames]);
        }
        this.d_TournamentResults.get(p_MapFile)[p_GameNumber] = p_WinnerName;
    }

    /**
     * Method to print the tournament results
     */
    public void printTournamentResultsInTableFormat() {
        d_LogEntryBuffer.logData("Tournament Results:");
        d_LogEntryBuffer.logData("Map Files: " + Arrays.toString(this.d_MapFiles));
        d_LogEntryBuffer.logData("Player Strategies: " + Arrays.toString(this.d_PlayerStrategies));
        d_LogEntryBuffer.logData("Number of Games: " + this.d_NumberOfGames);
        d_LogEntryBuffer.logData("Number of Max Turns: " + this.d_NumberOfMaxTurns);
        d_LogEntryBuffer.logData("....................");
        List<List<String>> rows = new ArrayList<>();
        
        List<String> header = new ArrayList<>();
        header.add("Games");
        for(int i = 0; i < this.d_NumberOfGames; i++){
            header.add("Game " + (i + 1));
        }
        rows.add(header);
        for(String mapFile : this.d_MapFiles){
            List<String> row = new ArrayList<>();
            row.add(mapFile);
            for(String winner : this.d_TournamentResults.get(mapFile)){
                row.add(winner);
            }
            rows.add(row);
        }
        d_LogEntryBuffer.logData(formatAsTable(rows));
    }

    public static String formatAsTable(List<List<String>> rows)
    {
        int[] maxLengths = new int[rows.get(0).size()];
        for (List<String> row : rows)
        {
            for (int i = 0; i < row.size(); i++)
            {
                maxLengths[i] = Math.max(maxLengths[i], row.get(i).length());
            }
        }

        StringBuilder formatBuilder = new StringBuilder();
        for (int maxLength : maxLengths)
        {
            formatBuilder.append("%-").append(maxLength + 2).append("s");
        }
        String format = formatBuilder.toString();

        StringBuilder result = new StringBuilder();
        for (List<String> row : rows)
        {
            result.append(String.format(format, row.toArray(new String[0]))).append("\n");
        }
        return result.toString();
    }

}
