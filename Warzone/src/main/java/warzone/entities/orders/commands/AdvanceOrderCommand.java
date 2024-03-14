package main.java.warzone.entities.orders.commands;

import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.Country;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.orders.Order;
import main.java.warzone.entities.orders.OrderDetails;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;

public class AdvanceOrderCommand extends Order {

    private LogEntryBuffer d_LogEntryBuffer;

    public AdvanceOrderCommand(String p_PlayerName, String p_SourceCountry, String p_TargetCountry, int p_NumberOfArmies) {
        super(p_PlayerName, p_SourceCountry, p_TargetCountry, p_NumberOfArmies);
        this.d_LogEntryBuffer = LogEntryBuffer.getInstance();
        this.setOrderType(WarzoneConstants.ADVANCE);
    }

    @Override
    public void execute(GameSession p_GameSession) {
        OrderDetails l_OrderDetails = this.getOrderDetails();
        d_LogEntryBuffer.logData("Executing attack order for " + this.getOrderDetails().getPlayerName() + " on " + this.getOrderDetails().getTargetCountry() + " from " + this.getOrderDetails().getSourceCountry() + " with " + this.getOrderDetails().getNumberOfArmies() + " armies");
        Country l_SourceCountry = p_GameSession.getCountriesOfSession().get(this.getOrderDetails().getSourceCountry());
        Country l_TargetCountry = p_GameSession.getCountriesOfSession().get(l_OrderDetails.getTargetCountry());
        // If not owned by player then subtract armies & update owner
        int l_RemainingAttackers = this.getRemainingAttackers(l_OrderDetails.getNumberOfArmies(), l_TargetCountry.getNumberOfArmies());
        int l_RemainingDefenders = this.getRemainingDefenders(l_OrderDetails.getNumberOfArmies(), l_TargetCountry.getNumberOfArmies());
        if (l_TargetCountry.getOwner() == null || !l_TargetCountry.getOwner().equals(l_OrderDetails.getPlayerName())) {
            if (l_RemainingDefenders == 0 && l_RemainingAttackers > 0) {
                d_LogEntryBuffer.logData("Capture successful");
                this.setCaptureFlag(true);
                if (l_TargetCountry.getOwner() != null) {
                    Player l_OldOwner = p_GameSession.getPlayers().get(l_TargetCountry.getOwner());
                    l_OldOwner.removeOwnedCountry(l_OrderDetails.getTargetCountry());
                }
                l_TargetCountry.setOwner(l_OrderDetails.getPlayerName());
                Player l_NewOwner = p_GameSession.getPlayers().get(l_OrderDetails.getPlayerName());
                l_NewOwner.addOwnedCountry(l_TargetCountry.getName());
                l_TargetCountry.setNumberOfArmies(l_RemainingAttackers);
            } else {
                d_LogEntryBuffer.logData("Capture failed");
                l_TargetCountry.setNumberOfArmies(l_RemainingDefenders);
            }

        } else {
            d_LogEntryBuffer.logData("Adding armies to owned country");
            l_TargetCountry.addArmies(l_OrderDetails.getNumberOfArmies());

        }
        // Reduce source country armies
        l_SourceCountry.addArmies(-l_OrderDetails.getNumberOfArmies());
        this.runPostExecute(p_GameSession);
    }
}