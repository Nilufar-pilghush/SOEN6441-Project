package main.java.warzone.entities.orders;

/**
 * Stores the details of a given order
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */

public class OrderDetails {

    /**
     * Represents the name of the player issuing the order.
     */
    private String d_PlayerName;

    /**
     * Represents the name of the target country of the order.
     */
    private String d_TargetCountry;

    /**
     * Represents the name of the source country of the order, if applicable.
     */
    private String d_SourceCountry;

    /**
     * Represents the number of armies involved in the order.
     */
    private int d_NumberOfArmies;


    /**
     * Retrieves the name of the player issuing the order.
     *
     * @return The name of the player.
     */
    public String getPlayerName() {
        return d_PlayerName;
    }

    /**
     * Retrieves the name of the target country of the order.
     *
     * @return The name of the target country.
     */
    public String getTargetCountry() {
        return d_TargetCountry;
    }

    /**
     * Retrieves the name of the source country of the order, if applicable.
     *
     * @return The name of the source country, or null if not applicable.
     */
    public String getSourceCountry() {
        return d_SourceCountry;
    }

    /**
     * Retrieves the number of armies involved in the order.
     *
     * @return The number of armies.
     */
    public int getNumberOfArmies() {
        return d_NumberOfArmies;
    }

    /**
     * Sets the name of the player issuing the order.
     *
     * @param p_PlayerName The name of the player.
     */
    public void setPlayerName(String p_PlayerName) {
        this.d_PlayerName = p_PlayerName;
    }


    /**
     * Sets the name of the target country of the order.
     *
     * @param p_TargetCountry The name of the target country.
     */
    public void setTargetCountry(String p_TargetCountry) {
        this.d_TargetCountry = p_TargetCountry;
    }

    /**
     * Sets the name of the source country of the order, if applicable.
     *
     * @param p_SourceCountry The name of the source country.
     */
    public void setSourceCountry(String p_SourceCountry) {
        this.d_SourceCountry = p_SourceCountry;
    }

    /**
     * Sets the number of armies involved in the order.
     *
     * @param p_NumberOfArmies The number of armies.
     */
    public void setNumberOfArmies(int p_NumberOfArmies) {
        this.d_NumberOfArmies = p_NumberOfArmies;
    }

}
