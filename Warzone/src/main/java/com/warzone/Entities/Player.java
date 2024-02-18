package main.java.com.warzone.Entities;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a player in the Warzone game.
 *
 * This class encapsulates the properties and behavior of a player in the Warzone game,
 * including the player's name, owned countries, number of armies, and orders.
 * Players can issue deploy orders to reinforce their territories or attack orders to conquer new territories.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */


public class Player {

    /**
     * The name of the player.
     */
    private String d_Name;

    /**
     * The set of countries owned by the player.
     */
    private Set<String> d_OwnedCountries;

    /**
     * The number of armies controlled by the player.
     */
    private int d_NumberOfArmies;

    /**
     * The list of orders issued by the player.
     */
    private List<Order> d_OrderList;

    /**
     * Default constructor
     */
    Player(String p_Name) {
        this.d_Name = p_Name;
        d_OwnedCountries = new HashSet<>();
        d_OrderList = new java.util.ArrayList<>();
    }

    /**
     * Retrieves the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {

        return d_Name;
    }

    /**
     * Sets the name of the player.
     *
     * @param p_Name The name to set for the player.
     */
    public void setName(String p_Name) {

        this.d_Name = p_Name;
    }

    /**
     * Retrieves the set of countries owned by the player.
     *
     * @return The set of owned countries.
     */
    public Set<String> getOwnedCountries() {

        return d_OwnedCountries;
    }

    /**
     * Adds a country to the set of owned countries.
     *
     * @param p_OwnedCountry The name of the country to add.
     */
    public void addOwnedCountry(String p_OwnedCountry) {

        this.d_OwnedCountries.add(p_OwnedCountry);
    }

    /**
     * Removes a country from the set of owned countries.
     *
     * @param p_OwnedCountry The name of the country to remove.
     */
    public void removeOwnedCountry(String p_OwnedCountry) {

        this.d_OwnedCountries.remove(p_OwnedCountry);
    }

    /**
     * Retrieves the list of orders issued by the player.
     *
     * @return The list of orders.
     */
    public List<Order> getOrderList() {

        return d_OrderList;
    }

    /**
     * Adds a deploy order issued by the player.
     *
     * @param p_TargetCountry The target country of the deploy order.
     * @param p_NumberOfArmies The number of armies to deploy.
     */
    public void addDeployOrder(String p_TargetCountry, int p_NumberOfArmies) {
        Order l_Order = new Order(this.d_Name, p_TargetCountry, p_NumberOfArmies);
        this.d_OrderList.add(l_Order);
    }

    /**
     * Adds an attack order issued by the player.
     *
     * @param p_SourceCountry The source country of the attack order.
     * @param p_TargetCountry The target country of the attack order.
     * @param p_NumberOfArmies The number of armies for the attack.
     */
    public void addAttackOrder(String p_SourceCountry, String p_TargetCountry, int p_NumberOfArmies) {
        Order l_Order = new Order(this.d_Name, p_SourceCountry, p_TargetCountry, p_NumberOfArmies);
        this.d_OrderList.add(l_Order);
    }

    /**
     * Removes an order from the list of orders.
     *
     * @param p_Order The order to remove.
     */
    public void removeOrder(Order p_Order) {

        this.d_OrderList.remove(p_Order);
    }

    /**
     * Retrieves the next order from the list of orders.
     *
     * @return The next order, or null if the list is empty.
     */
    public Order nextOrder() {
        if (this.d_OrderList.isEmpty()) {
            return null;
        }
        return this.d_OrderList.remove(0);
    }

    /**
     * Retrieves the number of armies controlled by the player.
     *
     * @return The number of armies.
     */
    public int getNumberOfArmies() {

        return d_NumberOfArmies;
    }

    /**
     * Sets the number of armies controlled by the player.
     *
     * @param p_NumberOfArmies The number of armies to set.
     */
    public void setNumberOfArmies(int p_NumberOfArmies) {

        this.d_NumberOfArmies = p_NumberOfArmies;
    }

    /**
     * Adds a specified number of armies to the player's army count.
     *
     * @param p_NumberOfArmies The number of armies to add.
     */
    public void addArmies(int p_NumberOfArmies) {

        this.d_NumberOfArmies += p_NumberOfArmies;
    }

    /**
     * Checks if the player owns a specific country.
     *
     * @param p_CountryName The name of the country to check ownership of.
     * @return True if the player owns the country, otherwise false.
     */
    public boolean ownsCountry(String p_CountryName) {

        return this.d_OwnedCountries.contains(p_CountryName);
    }
}
