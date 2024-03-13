package main.java.warzone.entities.orders.commands;

import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.orders.Order;
import main.java.warzone.entities.orders.OrderDetails;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;

public class DiplomacyOrderCommand extends Order {

    private String d_TargetPlayerName;

    private LogEntryBuffer d_LogEntryBuffer;

    public DiplomacyOrderCommand(String p_PlayerName, String p_TargetPlayerName) {
        super(p_PlayerName, "", 0);
        this.d_TargetPlayerName = p_TargetPlayerName;
        this.d_LogEntryBuffer = LogEntryBuffer.getInstance();
        this.setOrderType(WarzoneConstants.NEGOTIATE);
    }

    @Override
    public void execute(GameSession p_GameSession) {
        OrderDetails l_OrderDetails = this.getOrderDetails();
        d_LogEntryBuffer.logData("Executing diplomacy order for " + l_OrderDetails.getPlayerName() + " on " + this.d_TargetPlayerName);
        p_GameSession.getPlayers().get(l_OrderDetails.getPlayerName()).addDiplomacyPlayer(this.d_TargetPlayerName);
        p_GameSession.getPlayers().get(this.d_TargetPlayerName).addDiplomacyPlayer(l_OrderDetails.getPlayerName());
        this.runPostExecute(p_GameSession);
    }
}
