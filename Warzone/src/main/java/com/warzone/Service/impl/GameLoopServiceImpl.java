package main.java.com.warzone.Service.impl;

import main.java.com.warzone.Entities.GamePhase;
import main.java.com.warzone.Entities.GameSession;
import main.java.com.warzone.Service.GamePhaseService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GameLoopServiceImpl implements GamePhaseService {

    private GameSession d_GameSession;

    public GameLoopServiceImpl() {
        d_GameSession = GameSession.getInstance();
    }

    // Handle user actions
    // Display command options for the user
    // Read the user input and handle the command
    // return the game phase affected by the command
    @Override
    public GamePhase handleGamePhase(GamePhase p_CurrPhase) {
        System.out.println("Game Loop Service Controller");
        Scanner l_InputScanner = new Scanner(System.in);
        GamePhase l_NextPhase = p_CurrPhase.getNextPhaseInLoop(d_GameSession.getCurrGamePhase());
        while (true) {
            System.out.println("Main Game Loop");
            System.out.println("Current phase: " + d_GameSession.getCurrGamePhase());
            System.out.println("Next phase in line: " + l_NextPhase);
            System.out.println("Enter 'showmap' to view the map.");
            System.out.println("Enter 'exit' to exit the game.");
            System.out.println("Enter 'help' to display these instructions.");
            System.out.println("Enter nothing to continue with the game.");

            String l_UserInput = l_InputScanner.nextLine();
            List<String> l_UserInputTokens = new ArrayList<>();
            if (l_UserInput.contains("-")) {
                String l_HyphenTokens[]  = l_UserInput.split("-");
                for (String l_text : l_HyphenTokens) {
                    l_UserInputTokens.add(l_text.trim());
                }
            }
            else {
                String l_SpaceTokens[] = l_UserInput.split("\\s+");
                for (String l_text : l_SpaceTokens) {
                    l_UserInputTokens.add(l_text.trim());
                }
            }

            try {
                String l_PrimaryCommand = l_UserInputTokens.get(0).toLowerCase();
                switch (l_PrimaryCommand) {
                    case "showmap" -> {
                        System.out.println("Map display");
                        // need to add show map method
                    }
                    case "help" -> {
                        System.out.println("Help display");
                    }
                    case "exit" -> {
                        System.out.println("Exit game");
                    }
                    default -> {
                        System.out.println("Continue with game");
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("No input given");
            }
        }
    }
}
