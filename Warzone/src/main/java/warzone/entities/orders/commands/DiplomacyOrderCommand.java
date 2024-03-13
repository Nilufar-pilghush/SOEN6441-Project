package main.java.warzone.entities.orders.commands;

import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.orders.Order;
import main.java.warzone.entities.orders.OrderDetails;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;

/**
 * Deals with the diplomacy order execution in {@link Order}.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali Sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class DiplomacyOrderCommand extends Order {

    /**
     * Name of target the player.
     */
    private String d_TargetPlayerName;

    /**
     * LogEntry buffer object for logging.
     */
    private LogEntryBuffer d_LogEntryBuffer;

    /**
     * Constructor to create a diplomacy order command.
     *
     * @param p_PlayerName       Name of the player initiating the diplomacy.
     * @param p_TargetPlayerName Name of the target player for diplomacy.
     */
    public DiplomacyOrderCommand(String p_PlayerName, String p_TargetPlayerName) {
        super(p_PlayerName, "", 0);
        this.d_TargetPlayerName = p_TargetPlayerName;
        this.d_LogEntryBuffer = LogEntryBuffer.getInstance();
        this.setOrderType(WarzoneConstants.NEGOTIATE);
    }

    /**
     * Executes the diplomacy order command.
     * It adds the target player to the diplomacy list of the player initiating the order
     * and vice versa, indicating diplomatic relations between the two players.
     *
     * @param p_GameSession The current game session which contains information
     *      *                       about all countries and their armies.
     */
    @Override
    public void execute(GameSession p_GameSession) {
        OrderDetails l_OrderDetails = this.getOrderDetails();
        d_LogEntryBuffer.logData("Executing diplomacy order for " + l_OrderDetails.getPlayerName() + " on " + this.d_TargetPlayerName);
        p_GameSession.getPlayers().get(l_OrderDetails.getPlayerName()).addDiplomacyPlayer(this.d_TargetPlayerName);
        p_GameSession.getPlayers().get(this.d_TargetPlayerName).addDiplomacyPlayer(l_OrderDetails.getPlayerName());
        this.runPostExecute(p_GameSession);
    }
}
