package com.warzone;

import com.warzone.Entities.GamePhase;
import com.warzone.Exceptions.WarzoneBaseException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GamePlay {


    public boolean showMainMenu() throws WarzoneBaseException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        GamePhase l_GamePhase = null;
        System.out.println();
        System.out.println("********************************************************");
        System.out.println("\t\t\t\t\tWelcome to Warzone-Risk Game");
        System.out.println("\t\t\tSOEN-6441, Winter 2024, Concordia University");
        System.out.println("\t\t\t\t\t\t\t");
        System.out.println("********************************************************");
        boolean l_IsValid = false;
        while (!l_IsValid) {
            try {
                System.out.println("\t\t\t********************************************************");
                System.out.println("\t\t\t\t\t  1 Edit Map");
                System.out.println("\t\t\t\t\t  2 Start Game");
                System.out.println("\t\t\t\t\t  3 Exit Game");
                System.out.println("\t\t\t********************************************************");
                System.out.println("\t\t\t\t   Choose your option");
                System.out.println("********************************************************");
                int l_SelectedOption = Integer.parseInt(br.readLine());
                switch (l_SelectedOption) {
                    case 1 -> {
                        l_IsValid = true;

                    }
                    case 2 -> {
                        l_IsValid = true;

                    }
                    case 3 -> {
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

        }
        return true;
    }


}
