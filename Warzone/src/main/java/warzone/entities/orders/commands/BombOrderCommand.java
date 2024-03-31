package main.java.warzone.entities.orders.commands;

import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.Country;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.orders.Order;
import main.java.warzone.entities.orders.OrderDetails;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;

import java.io.Serializable;

/**
 * Deals with the bomb order execution in {@link Order}.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali Sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */

public class BombOrderCommand extends Order implements Serializable {

    /**
     * LogEntryBuffer object for logging the user play data.
     */
    private LogEntryBuffer d_LogEntryBuffer;

    /**
     * Constructs a new Bomb order command with specified player and target country.
     *
     * @param p_PlayerName     The name of the player issuing the bomb order.
     * @param p_TargetCountry  The name of the target country to be bombed.
     */
    public BombOrderCommand(String p_PlayerName, String p_TargetCountry) {
        super(p_PlayerName, p_TargetCountry, 0);
        this.d_LogEntryBuffer = LogEntryBuffer.getInstance();
        this.setOrderType(WarzoneConstants.BOMB);
    }

    /**
     * Executes the bomb order on the target country. The method halves
     * the number of armies in the target country and the execution details
     * are logged for reference.
     *
     * @param p_GameSession The current game session which contains information
     *                       about all countries and their armies.
     */
    @Override
    public void execute(GameSession p_GameSession) {
        OrderDetails l_OrderDetails = this.getOrderDetails();
        displayCommand(l_OrderDetails);

        Country l_BombTargetCountry = p_GameSession.getCountriesInSession().get(l_OrderDetails.getTargetCountry());
        int l_CurrentArmies = l_BombTargetCountry.getNumberOfArmies();
        // Reduce the target country's army count by half
        l_BombTargetCountry.setNumberOfArmies(Math.floorDiv(l_CurrentArmies, 2));
        this.runPostExecute(p_GameSession);
    }

    @Override
    public void displayCommand(OrderDetails p_OrderDetails) {
        d_LogEntryBuffer.logData("Executing bomb order for " + p_OrderDetails.getPlayerName() + " on " + p_OrderDetails.getTargetCountry());
    }
}
