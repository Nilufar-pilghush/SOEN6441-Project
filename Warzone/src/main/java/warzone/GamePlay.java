package main.java.warzone;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import main.java.warzone.entities.GamePhase;

import main.java.warzone.exceptions.WarzoneBaseException;

/**
 * Concrete class to manage the main menu of the game.
 * The GamePlay class serves as the entry point for the Warzone-Risk game.
 * It enables the presentation of the main menu to the player and
 * navigates them to different game sections depending on their choice.
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */
public class GamePlay {

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
            System.out.println();
            System.out.println("********************************************************");
            System.out.println("\t\t\tSOEN-6441, Winter 2024, Concordia University");
            System.out.println("\t\t\tWarzone-Risk Game Project, Build 01");
            System.out.println("\t\t\t\t\t\t\t");
            System.out.println("********************************************************");
            boolean l_IsValid = false;
            while (!l_IsValid) {
                try {
                    System.out.println("********************************************************");
                    System.out.println("Choose One Option: ");
                    System.out.println("\t\t\t\t\t  1 Edit Map");
                    System.out.println("\t\t\t\t\t  2 Start Game");
                    System.out.println("\t\t\t\t\t  3 Exit Game");
                    System.out.println("********************************************************");
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
                            l_GamePhase = GamePhase.EXIT;
                            return true;

                        }
                        default -> {
                            System.out.println("Invalid option--> " + l_SelectedOption);
                            continue;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Invalid user command");
                }
                if(l_IsValid) {
                    GameEngine l_GameEngine = new GameEngine();
                    l_GameEngine.handleGamePhases(l_GamePhase);
                }
            }
            return true;
        }


    }

