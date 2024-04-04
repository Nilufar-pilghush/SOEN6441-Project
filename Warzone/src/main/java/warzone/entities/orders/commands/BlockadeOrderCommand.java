package main.java.warzone.entities.orders.commands;

import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.Country;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.orders.Order;
import main.java.warzone.entities.orders.OrderDetails;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;

import java.io.Serializable;

/**
 * Implementation of the {@link Order} interface for executing the blockade order.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali Sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class BlockadeOrderCommand extends Order implements Serializable {

    /**
     * LogEntryBuffer instance for recording log data.
     */
    private LogEntryBuffer d_LogEntryBuffer;

    /**
     * Constructor for Initializing a blockade order with the specified details.
     *
     * @param p_PlayerName     Name of the player
     * @param p_TargetCountry  Target country to attack
     */
    public BlockadeOrderCommand(String p_PlayerName, String p_TargetCountry) {
        super(p_PlayerName, p_TargetCountry, 0);
        this.d_LogEntryBuffer = LogEntryBuffer.getInstance();
        this.setOrderType(WarzoneConstants.BLOCKADE);
    }


    /**
     * Executes the blockade order within the given game session.
     *
     * @param p_GameSession The current instance of the game session.
     */
    @Override
    public void execute(GameSession p_GameSession) {
        OrderDetails l_OrderDetails = this.getOrderDetails();
        displayCommand(l_OrderDetails);

        // Triple the number of armies in the target country
        Country l_TargetCountry = p_GameSession.getCountriesInSession().get(l_OrderDetails.getTargetCountry());
        int l_Armies = l_TargetCountry.getNumberOfArmies();
        l_TargetCountry.setNumberOfArmies(Math.multiplyExact(l_Armies, 3));

        // Remove ownership of the target country
        l_TargetCountry.setOwner(null);
    }

    @Override
    public void displayCommand(OrderDetails p_OrderDetails) {
        d_LogEntryBuffer.logData("Executing blockade order for " + p_OrderDetails.getPlayerName() + " on " + p_OrderDetails.getTargetCountry());
    }

}