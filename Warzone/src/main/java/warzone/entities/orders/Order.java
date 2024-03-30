package main.java.warzone.entities.orders;
import main.java.warzone.entities.GameSession;
import  main.java.warzone.utils.logging.impl.LogEntryBuffer;
import java.io.Serializable;

/**
 * Represents an order issued by a player in the Warzone main.java.game.
 *
 * This class encapsulates the properties and behavior of an order in the Warzone main.java.game,
 * including the player name, target country, source country (if applicable), and number of armies involved.
 * Orders can be either deploy orders or attack orders, each with different execution logic.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */

public abstract class Order implements Serializable{

    /**
     * Member to hold details of the order
     */
    private OrderDetails d_OrderDetails;

    /**
     * LogEntryBuffer object to log the information
     * and notifying all the observers
     */
    private LogEntryBuffer d_LogEntryBuffer;

    /**
     * Boolean to check if capture was successful
     */
    private boolean d_captureFlag = false;

    /**
     * Order type
     */
    private String d_OrderType;

    /**
     * Constructor to instantiate order
     *
     * @param p_PlayerName     Name of the player
     * @param p_TargetCountry  Name of target country
     * @param p_NumberOfArmies Number of armies to attack
     */
    public Order(String p_PlayerName, String p_TargetCountry, int p_NumberOfArmies) {
        d_OrderDetails = new OrderDetails();
        d_OrderDetails.setPlayerName(p_PlayerName);
        d_OrderDetails.setTargetCountry(p_TargetCountry);
        d_OrderDetails.setNumberOfArmies(p_NumberOfArmies);
        d_OrderDetails.setSourceCountry(null);
        this.d_LogEntryBuffer = LogEntryBuffer.getInstance();
    }


    /**
     * Constructor to create order instance for given player name, target country
     * and number of armies.
     *
     * @param p_PlayerName     Name of the player
     * @param p_TargetCountry  Name of target country
     * @param p_SourceCountry  Name of source country
     * @param p_NumberOfArmies Count of armies
     */
    public Order(String p_PlayerName, String p_SourceCountry, String p_TargetCountry, int p_NumberOfArmies) {
        d_OrderDetails = new OrderDetails();
        d_OrderDetails.setPlayerName(p_PlayerName);
        d_OrderDetails.setSourceCountry(p_SourceCountry);
        d_OrderDetails.setTargetCountry(p_TargetCountry);
        d_OrderDetails.setNumberOfArmies(p_NumberOfArmies);
        this.d_LogEntryBuffer = LogEntryBuffer.getInstance();
    }

    /**
     * Method to get order details
     * @return Details of the given order
     */
    public OrderDetails getOrderDetails() {
        return this.d_OrderDetails;
    }

    /**
     * Method to set order details for a given order
     *
     * @param p_OrderDetails Details of the order
     */
    public void setOrderDetails(OrderDetails p_OrderDetails) {
        this.d_OrderDetails = p_OrderDetails;
    }

    /**
     * Abstract Method to execute order
     *
     * @param p_GameSession Current game session instance
     */
    public abstract void execute(GameSession p_GameSession);

    /**
     * Method to check if capture was successful
     *
     * @return true if capture was successful, false otherwise
     */
    public boolean isCaptureSuccessful() {
        return d_captureFlag;
    }

    /**
     * Method to set capture flag
     *
     * @param p_CaptureFlag true if capture was successful, false otherwise
     */
    public void setCaptureFlag(boolean p_CaptureFlag) {
        this.d_captureFlag = p_CaptureFlag;
    }


    /**
     * Method to run post execute
     * Will run after execute method for logic like awarding cards
     * on successful capture
     *
     * @param p_GameSession Current game session instance
     */
    public void runPostExecute(GameSession p_GameSession) {
        if (isCaptureSuccessful()) {
            // Award player a card
            p_GameSession.getPlayers().get(this.getOrderDetails().getPlayerName()).setEarnedCardThisTurn(true);

        }
    }

    /**
     * Method to get order type
     *
     * @return Order type
     */
    public String getOrderType() {
        return d_OrderType;
    }

    /**
     * Method to set order type
     *
     * @param p_OrderType Order type
     */
    public void setOrderType(String p_OrderType) {
        this.d_OrderType = p_OrderType;
    }

    /**
     * Method to get remaining defenders
     *
     * @param p_AttackingArmies Number of attacking armies
     * @param p_DefendingArmies Number of defending armies
     * @return Remaining defenders
     */
    public int getRemainingDefenders(int p_AttackingArmies, int p_DefendingArmies) {
        // 60% of attacking armies chance to kill 1 defending army
        int l_RemainingDefenders = (int) Math.round(p_DefendingArmies - (p_AttackingArmies * 0.6));
        if (l_RemainingDefenders < 0) {
            return 0;
        }
        return l_RemainingDefenders;
    }

    /**
     * Method to get remaining attackers
     *
     * @param p_AttackingArmies Number of attacking armies
     * @param p_DefendingArmies Number of defending armies
     * @return Remaining attackers
     */
    public int getRemainingAttackers(int p_AttackingArmies, int p_DefendingArmies) {
        // 70% of defending armies chance to kill 1 attacking army
        int l_RemainingAttackers = (int) Math.round(p_AttackingArmies - (p_DefendingArmies * 0.7));
        if (l_RemainingAttackers < 0) {
            return 0;
        }
        return l_RemainingAttackers;
    }

}
