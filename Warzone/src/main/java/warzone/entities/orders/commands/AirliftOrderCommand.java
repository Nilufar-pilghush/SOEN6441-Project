package main.java.warzone.entities.orders.commands;

import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.Country;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.orders.Order;
import main.java.warzone.entities.orders.OrderDetails;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;

/**
 * Implementation of the {@link Order} interface for executing the airlift order.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali Sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class AirliftOrderCommand extends Order {

    /**
     * LogEntryBuffer instance for recording log data.
     */
    private LogEntryBuffer d_LogEntryBuffer;

    /**
     * Constructor for Initializing an airlift order with the specified details.
     *
     * @param p_PlayerName     Name of the player
     * @param p_SourceCountry  Source country to attack
     * @param p_TargetCountry  Target country to attack
     * @param p_NumberOfArmies Number of armies in attack
     */
    public AirliftOrderCommand(String p_PlayerName, String p_SourceCountry, String p_TargetCountry, int p_NumberOfArmies) {
        super(p_PlayerName, p_SourceCountry, p_TargetCountry, p_NumberOfArmies);
        this.d_LogEntryBuffer = LogEntryBuffer.getInstance();
        this.setOrderType(WarzoneConstants.AIRLIFT);
    }

    /**
     * Executes the airlift order within the given game session.
     *
     * @param p_GameSession The current instance of the game session.
     */
    @Override
    public void execute(GameSession p_GameSession) {
        OrderDetails l_OrderDetails = this.getOrderDetails();
        d_LogEntryBuffer.logData("Executing airlift order for " + l_OrderDetails.getPlayerName() + " from " + l_OrderDetails.getSourceCountry() + " to " + l_OrderDetails.getTargetCountry() + " with " + l_OrderDetails.getNumberOfArmies() + " armies");
        Country l_SourceCountry = p_GameSession.getCountriesOfSession().get(l_OrderDetails.getSourceCountry());
        Country l_TargetCountry = p_GameSession.getCountriesOfSession().get(l_OrderDetails.getTargetCountry());
        // If not owned by the player, then subtract armies & update owner
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
        l_SourceCountry.subtractArmies(l_OrderDetails.getNumberOfArmies());
        this.runPostExecute(p_GameSession);
    }

}