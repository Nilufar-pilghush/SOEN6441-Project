package main.java.warzone;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import main.java.warzone.entities.GamePhase;

import main.java.warzone.exceptions.WarzoneBaseException;
import main.java.warzone.utils.logging.impl.ConsoleLogger;
import main.java.warzone.utils.logging.impl.GameLogger;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;

/**
 * Concrete class to manage the main menu of the game.
 * The GamePlay class serves as the entry point for the Warzone-Risk game.
 * It enables the presentation of the main menu to the player and
 * navigates them to different game sections depending on their choice.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */
public class GamePlay {

    /**
     * LogEntryBuffer object for logging the user play data.
     */
    private LogEntryBuffer d_LogEntryBuffer;

    /**
     * Constructor to instantiate game play.
     */
    public GamePlay() {
        d_LogEntryBuffer = LogEntryBuffer.getInstance();
        d_LogEntryBuffer.registerObserver(new ConsoleLogger());
        d_LogEntryBuffer.registerObserver(new GameLogger());
    }

    /**
     * Presents the Warzone-Risk game's main menu to the player.
     * Options available for player:
     * - Start a new game: Redirects the player to the map editor phase.
     * - Exit the game.
     *
     * @return True if main menu loading was successful, exception otherwise.
     * @throws WarzoneBaseException If any validation issues arise within this game phase.
     */
        public boolean showMainMenu() throws WarzoneBaseException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            GamePhase l_GamePhase = null;
            d_LogEntryBuffer.logData("");
            d_LogEntryBuffer.logData("********************************************************");
            d_LogEntryBuffer.logData("\t\t\tSOEN-6441, Winter 2024, Concordia University");
            d_LogEntryBuffer.logData("\t\t\tWarzone-Risk Game Project, Build 03");
            d_LogEntryBuffer.logData("\t\t\t\t\t\t\t");
            d_LogEntryBuffer.logData("********************************************************");
            boolean l_IsValid = false;
            while (!l_IsValid) {
                try {
                    d_LogEntryBuffer.logData("********************************************************");
                    d_LogEntryBuffer.logData("Choose One Option: ");
                    d_LogEntryBuffer.logData("\t\t\t\t\t  1 Edit Map");
                    d_LogEntryBuffer.logData("\t\t\t\t\t  2 Start Game");
                    d_LogEntryBuffer.logData("\t\t\t\t\t  3 Load Game");
                    d_LogEntryBuffer.logData("\t\t\t\t\t  4 Exit Game");
                    d_LogEntryBuffer.logData("********************************************************");
                    int l_SelectedOption = Integer.parseInt(br.readLine());
                    switch (l_SelectedOption) {
                        case 1 -> {
                            l_IsValid = true;
                            l_GamePhase = GamePhase.MAP_EDITOR;
                        }
                        case 2 -> {
                            l_IsValid = true;
                            l_GamePhase = GamePhase.START_UP;
                        }
                        case 3 -> {
                            l_IsValid = true;
                            l_GamePhase = GamePhase.LOAD_GAME;
                        }
                        case 4 -> {
                            l_GamePhase = GamePhase.EXIT;
                            return true;
                        }
                        default -> {
                            d_LogEntryBuffer.logData("Invalid option--> " + l_SelectedOption);
                            continue;
                        }
                    }
                } catch (Exception e) {
                    d_LogEntryBuffer.logData("Invalid user command");
                }
                if(l_IsValid) {
                    GameEngine l_GameEngine = new GameEngine();
                    l_GameEngine.handleGamePhases(l_GamePhase);
                }
            }
            return true;
        }


    }

