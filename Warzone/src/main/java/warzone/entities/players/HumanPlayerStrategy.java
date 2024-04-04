package main.java.warzone.entities.players;

import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.Player;
import main.java.warzone.utils.CmdUtils;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;
import main.java.constants.WarzoneConstants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;


public class HumanPlayerStrategy implements PlayerStrategy, Serializable {

    private BufferedReader d_InputScanner;

    private LogEntryBuffer d_LogEntryBuffer;


    public HumanPlayerStrategy() {
        d_InputScanner = new BufferedReader(new InputStreamReader(System.in));
        d_LogEntryBuffer = LogEntryBuffer.getInstance();
    }

    @Override
    public void issuePlayerOrder(Player p_Player, GameSession p_GameSession) {
        boolean l_isPlayerDoneIssuingOrders = false;

        while (!l_isPlayerDoneIssuingOrders) {
            try {
                d_LogEntryBuffer.logData("Player " + p_Player.getName() + " is issuing orders");
                d_LogEntryBuffer.logData("Available player reinforcements: " + p_Player.getNumberOfArmies());
                d_LogEntryBuffer.logData(".....................................................");
                d_LogEntryBuffer.logData("Use any of below commands:");
                d_LogEntryBuffer.logData("deploy countryID numArmies");
                d_LogEntryBuffer.logData("advance sourceCountryID targetCountryID numArmies");
                d_LogEntryBuffer.logData("bomb targetCountryID");
                d_LogEntryBuffer.logData("blockade countryID");
                d_LogEntryBuffer.logData("airlift sourceCountryID targetCountryID numArmies");
                d_LogEntryBuffer.logData("negotiate playerID");
                d_LogEntryBuffer.logData(".....................................................");

                String l_UserInput = d_InputScanner.readLine();
                List<String> l_UserInputParts = CmdUtils.getUserInputParts(l_UserInput);
                String l_PrimaryAction = l_UserInputParts.get(0).toLowerCase();

                switch (l_PrimaryAction) {
                    case WarzoneConstants.DEPLOY -> p_Player.deployOrderHandler(l_UserInputParts, p_GameSession);
                    case WarzoneConstants.ADVANCE -> p_Player.advanceOrderHandler(l_UserInputParts, p_GameSession);
                    case WarzoneConstants.BOMB -> p_Player.bombOrderHandler(l_UserInputParts, p_GameSession);
                    case WarzoneConstants.BLOCKADE -> p_Player.blockadeOrderHandler(l_UserInputParts, p_GameSession);
                    case WarzoneConstants.AIRLIFT -> p_Player.airliftOrderHandler(l_UserInputParts, p_GameSession);
                    case WarzoneConstants.NEGOTIATE -> p_Player.negotiateOrderHandler(l_UserInputParts, p_GameSession);
                }
                d_LogEntryBuffer.logData("Do you want to issue more orders? (Y/N)");
                String l_UserInputMore = d_InputScanner.readLine();
                if (l_UserInputMore.equalsIgnoreCase("N")) {
                    l_isPlayerDoneIssuingOrders = true;
                }
            } catch (Exception e) {
                d_LogEntryBuffer.logData("Invalid command");
            }
        }
    }

    @Override
    public String getStrategyNameString() {
        return WarzoneConstants.HUMAN;
    }
}