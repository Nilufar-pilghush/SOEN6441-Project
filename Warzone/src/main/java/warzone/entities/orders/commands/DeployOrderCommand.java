package main.java.warzone.entities.orders.commands;

import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.Country;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.Player;
import main.java.warzone.entities.orders.Order;
import main.java.warzone.entities.orders.OrderDetails;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;

public class DeployOrderCommand extends Order {

    private LogEntryBuffer d_LogEntryBuffer;

    public DeployOrderCommand(String p_PlayerName, String p_TargetCountry, int p_NumberOfArmies) {
        super(p_PlayerName, p_TargetCountry, p_NumberOfArmies);
        this.d_LogEntryBuffer = LogEntryBuffer.getInstance();
        this.setOrderType(WarzoneConstants.DEPLOY);
    }

    @Override
    public void execute(GameSession p_GameSession) {
        OrderDetails l_OrderDetails = this.getOrderDetails();
        d_LogEntryBuffer.logData("Executing deployment order for " + l_OrderDetails.getPlayerName() + " in " + l_OrderDetails.getTargetCountry() + " with a deployment of " + l_OrderDetails.getNumberOfArmies() + " armies.");
        Country l_TargetCountry = p_GameSession.getCountriesInSession().get(l_OrderDetails.getTargetCountry());
        int l_RemainingAttackers = this.getRemainingAttackers(l_OrderDetails.getNumberOfArmies(), l_TargetCountry.getNumberOfArmies());
        int l_RemainingDefenders = this.getRemainingDefenders(l_OrderDetails.getNumberOfArmies(), l_TargetCountry.getNumberOfArmies());
        if (l_TargetCountry.getOwner() == null || !l_TargetCountry.getOwner().equals(l_OrderDetails.getPlayerName())) {
            if (l_RemainingDefenders == 0 && l_RemainingAttackers > 0) {
                d_LogEntryBuffer.logData("Capture successful");
                this.setCaptureFlag(true);
                if (l_TargetCountry.getOwner() != null) {
                    Player l_PreviousOwner = p_GameSession.getPlayers().get(l_TargetCountry.getOwner());
                    l_PreviousOwner.removeOwnedCountry(l_OrderDetails.getTargetCountry());
                }
                l_TargetCountry.setOwner(l_OrderDetails.getPlayerName());
                Player l_NewOwner = p_GameSession.getPlayers().get(l_OrderDetails.getPlayerName());
                l_NewOwner.addOwnedCountry(l_TargetCountry.getName());
                l_TargetCountry.setNumberOfArmies(l_RemainingAttackers);
            } else {
                d_LogEntryBuffer.logData("Capture failed.");
                l_TargetCountry.setNumberOfArmies(l_RemainingDefenders);
                return;
            }
        } else {
            d_LogEntryBuffer.logData("Adding armies to owned country");
            l_TargetCountry.addArmies(l_OrderDetails.getNumberOfArmies());
        }
        this.runPostExecute(p_GameSession);
    }
}
