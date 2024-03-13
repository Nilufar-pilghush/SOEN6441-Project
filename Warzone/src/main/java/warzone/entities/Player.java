package main.java.warzone.entities;
import main.java.warzone.entities.orders.Order;

import java.util.*;

import main.java.warzone.entities.commands.AdvanceOrderCommand;
import main.java.warzone.entities.commands.BombOrderCommand;
import main.java.warzone.entities.commands.DeployOrderCommand;
import main.java.warzone.entities.commands.BlockadeOrderCommand;
import main.java.warzone.entities.commands.DiplomacyOrderCommand;
import main.java.warzone.entities.commands.AirliftOrderCommand;

/**
 * Represents a player in the Warzone main.java.game.
 *
 * This class encapsulates the properties and behavior of a player in the Warzone main.java.game,
 * including the player's name, owned countries, number of armies, and orders.
 * Players can issue deploy orders to reinforce their territories or attack orders to conquer new territories.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
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
     * List of orders issued by player
     */
    private Queue<Order> d_OrderList;

    /**
     * Boolean to check if player has earned card this turn
     */
    private boolean d_EarnedCardThisTurn;

    /**
     * Set of players with whom player has diplomacy with.
     */
    private Set<String> d_DiplomacyPlayers;

    /**
     * Cards owned by player
     */
    private Set<String> d_OwnedCards;
  
    /**
     * Constructs a new Player with the given name and initializes the set of owned country IDs.
     *
     * @param p_Name The name of the player.
     */
    Player(String p_Name) {
        this.d_Name = p_Name;
        d_OwnedCountries = new HashSet<>();
        d_OrderList = new ArrayDeque<>();
        d_OwnedCards = new HashSet<>();
        d_EarnedCardThisTurn = false;
        d_DiplomacyPlayers = new HashSet<>();
    }

    /**
     * Method to get the name of player
     *
     * @return Name of the player.
     */
    public String getName() {
        return d_Name;
    }

    /**
     * Method to set the name of the player.
     *
     * @param p_Name New name for the player.
     */
    public void setName(String p_Name) {
        this.d_Name = p_Name;
    }

    /**
     * Method to get set of countries owned by player.
     *
     * @return Set of countries owned by player.
     */
    public Set<String> getOwnedCountries() {
        return d_OwnedCountries;
    }

    /**
     * Method to add a country in owned counties list.
     *
     * @param p_OwnedCountry Name of country owned by player.
     */
    public void addOwnedCountry(String p_OwnedCountry) {
        this.d_OwnedCountries.add(p_OwnedCountry);
    }

    /**
     * Method to remove country from owned countries by player.
     *
     * @param p_OwnedCountry Country name to be removed.
     */
    public void removeOwnedCountry(String p_OwnedCountry) {
        this.d_OwnedCountries.remove(p_OwnedCountry);
    }

    /**
     * Method to get list of orders issued by player.
     *
     * @return List of order issued by player.
     */
    public Queue<Order> getOrderList() {
        return d_OrderList;
    }

    /**
     * Method to add deploy order on a target country with
     * given number of armies
     *
     * @param p_TargetCountry  Name of target country
     * @param p_NumberOfArmies Number of armies
     */
    public void addDeployOrder(String p_TargetCountry, int p_NumberOfArmies) {
        Order l_Order = new DeployOrderCommand(this.d_Name, p_TargetCountry, p_NumberOfArmies);
        this.d_OrderList.add(l_Order);
    }

    /**
     * Method to add attack order on a source country to a target country with
     * given number of armies
     *
     * @param p_SourceCountry  Name of source country
     * @param p_TargetCountry  Name of target country
     * @param p_NumberOfArmies Number of armies
     */
    public void addAttackOrder(String p_SourceCountry, String p_TargetCountry, int p_NumberOfArmies) {
        Order l_Order = new AdvanceOrderCommand(this.d_Name, p_SourceCountry, p_TargetCountry, p_NumberOfArmies);
        this.d_OrderList.add(l_Order);
    }

    /**
     * Method to add bomb order on a target country
     *
     * @param p_TargetCountry Name of target country
     */
    public void addBombOrder(String p_TargetCountry) {
        Order l_Order = new BombOrderCommand(this.d_Name, p_TargetCountry);
        this.d_OrderList.add(l_Order);
    }

    /**
     * Method to add blockade order on a target country
     *
     * @param p_TargetCountry Name of target country
     */
    public void addBlockadeOrder(String p_TargetCountry) {
        Order l_Order = new BlockadeOrderCommand(this.d_Name, p_TargetCountry);
        this.d_OrderList.add(l_Order);
    }

    /**
     * Method to add diplomacy order on a target player
     *
     * @param p_TargetPlayer Name of target player
     */
    public void addDiplomacyOrder(String p_TargetPlayer) {
        Order l_Order = new DiplomacyOrderCommand(this.d_Name, p_TargetPlayer);
        this.d_OrderList.add(l_Order);
    }

    /**
     * Method to add airlift order on a source country to a target country with
     * given number of armies
     *
     * @param p_SourceCountry  Name of source country
     * @param p_TargetCountry  Name of target country
     * @param p_NumberOfArmies Number of armies
     */
    public void addAirliftOrder(String p_SourceCountry, String p_TargetCountry, int p_NumberOfArmies) {
        Order l_Order = new AirliftOrderCommand(this.d_Name, p_SourceCountry, p_TargetCountry, p_NumberOfArmies);
        this.d_OrderList.add(l_Order);
    }

    /**
     * Method to remove given order from orders list
     *
     * @param p_Order Order to be removed
     */
    public void removeOrder(Order p_Order) {
        this.d_OrderList.remove(p_Order);
    }

    /**
     * Method to get next order in list.
     *
     * @return Next order if available, null otherwise.
     */
    public Order nextOrder() {
        if (this.d_OrderList.isEmpty()) {
            return null;
        }
        return this.d_OrderList.poll();
    }

    /**
     * Method to get number of armies of player.
     *
     * @return Count of armies.
     */
    public int getNumberOfArmies() {
        return d_NumberOfArmies;
    }

    /**
     * Method to set number of armies of player.
     *
     * @param p_NumberOfArmies Count of armies to set.
     */
    public void setNumberOfArmies(int p_NumberOfArmies) {
        this.d_NumberOfArmies = p_NumberOfArmies;
    }

    /**
     * Method to add given number of armies in total armies.
     *
     * @param p_NumberOfArmies Count of armies to add in total.
     */
    public void addArmies(int p_NumberOfArmies) {
        this.d_NumberOfArmies += p_NumberOfArmies;
    }

    /**
     * Method to check if given country owned by player.
     *
     * @param p_CountryName Name of the country.
     * @return True if given country is owned by a player, false otherwise.
     */
    public boolean ownsCountry(String p_CountryName) {
        return this.d_OwnedCountries.contains(p_CountryName);
    }

    /**
     * Method to get cards owned by player
     *
     * @return Set of cards owned by player
     */
    public Set<String> getOwnedCards() {
        return d_OwnedCards;
    }

    /**
     * Method to add card in owned cards list
     *
     * @param p_CardName Name of card to be added
     */
    public void addCard(String p_CardName) {
        this.d_OwnedCards.add(p_CardName);
    }

    /**
     * Method to check if player owns given card
     *
     * @param p_CardName Name of card to be removed
     * @return True if player has given card, false otherwise
     */
    public boolean ownsCard(String p_CardName) {
        return this.d_OwnedCards.contains(p_CardName);
    }


    /**
     * Method to use card
     *
     * @param p_CardName Name of card to be used
     * @return True if card is successfully used, false otherwise
     */
    public boolean useCard(String p_CardName) {
        if (this.d_OwnedCards.contains(p_CardName)) {
            this.d_OwnedCards.remove(p_CardName);
            return true;
        }
        return false;
    }

    /**
     * Method to check if card is earn in current turn
     *
     * @return True if card is earned in this turn, false otherwise
     */
    public boolean isEarnedCardThisTurn() {
        return d_EarnedCardThisTurn;
    }

    /**
     * Method to set owned cards
     *
     * @param p_EarnedCardThisTurn Set of owned cards
     */
    public void setEarnedCardThisTurn(boolean p_EarnedCardThisTurn) {
        this.d_EarnedCardThisTurn = p_EarnedCardThisTurn;
    }

    /**
     * Method to get diplomacy players
     *
     * @return Set of diplomacy players
     */
    public Set<String> getDiplomacyPlayers() {
        return d_DiplomacyPlayers;
    }

    /**
     * Method to add a diplomacy player
     *
     * @param p_DiplomacyPlayer Name of player to be added as a diplomatic
     */
    public void addDiplomacyPlayer(String p_DiplomacyPlayer) {
        this.d_DiplomacyPlayers.add(p_DiplomacyPlayer);
    }

    /**
     * Method to remove diplomacy player
     *
     * @param p_DiplomacyPlayer Name of diplomacy player
     */
    public void removeDiplomacyPlayer(String p_DiplomacyPlayer) {
        this.d_DiplomacyPlayers.remove(p_DiplomacyPlayer);
    }

    /**
     * Method to check if player has diplomacy with given player
     *
     * @param p_DiplomacyPlayer Name of diplomacy player
     * @return True if player has diplomacy with given player, false otherwise
     */
    public boolean hasDiplomacyWith(String p_DiplomacyPlayer) {
        return this.d_DiplomacyPlayers.contains(p_DiplomacyPlayer);
    }

    /**
     * Method to reset diplomacy players
     */
    public void resetDiplomacyPlayers() {
        this.d_DiplomacyPlayers = new HashSet<>();
    }
}
