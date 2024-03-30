package main.java.warzone.entities;

import  main.java.warzone.entities.orders.commands.AdvanceOrderCommand;
import  main.java.warzone.entities.orders.commands.BombOrderCommand;
import  main.java.warzone.entities.orders.commands.DeployOrderCommand;
import  main.java.warzone.entities.orders.commands.BlockadeOrderCommand;
import  main.java.warzone.entities.orders.commands.DiplomacyOrderCommand;
import  main.java.warzone.entities.players.HumanPlayerStrategy;
import  main.java.warzone.entities.players.PlayerStrategy;
import  main.java.warzone.utils.logging.impl.LogEntryBuffer;
import  main.java.warzone.entities.orders.commands.AirliftOrderCommand;
import  main.java.warzone.constants.WarzoneConstants;
import  main.java.warzone.entities.orders.Order;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Concrete class to access and modify the properties of Player.
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */
public class Player implements Serializable {

    /**
     * LogEntryBuffer object to log the information
     * and notifying all the observers
     */
    private LogEntryBuffer d_LogEntryBuffer;


    /**
     * The name of the player.
     */
    private String d_Name;

    /**
     * Countries owned by the player
     */
    private Set<String> d_OwnedCountries;

    /**
     * List of orders issued by player
     */
    private Queue<Order> d_OrderList;

    /**
     * Number of armies player have
     */
    private int d_NumberOfArmies;

    /**
     * Cards owned by player
     */
    private Set<String> d_OwnedCards;

    /**
     * Boolean to check if player has earned card this turn
     */
    private boolean d_EarnedCardThisTurn;

    /**
     * Set of players with whom player has diplomacy
     */
    private Set<String> d_DiplomacyPlayers;

    /**
     * Strategy of player
     */
    private PlayerStrategy d_PlayerStrategy;

    /**
     * Constructs a new Player with the given name and initializes the set of owned country IDs.
     *
     * @param p_name The name of the player.
     * @param p_PlayerStrategy Player strategy
     */
    Player(String p_name, PlayerStrategy p_PlayerStrategy) {
        this.d_Name = p_name;
        d_OwnedCountries = new HashSet<>();
        d_OrderList = new ArrayDeque<>();
        d_OwnedCards = new HashSet<>();
        d_EarnedCardThisTurn = false;
        d_DiplomacyPlayers = new HashSet<>();
        d_PlayerStrategy = p_PlayerStrategy;
        d_LogEntryBuffer = LogEntryBuffer.getInstance();
    }


    /**
     * Constructs a new Human Player with the given name and initializes the set of owned country IDs.
     *
     * @param p_Name The name of the player.
     */
    Player(String p_Name) {
        this(p_Name, new HumanPlayerStrategy());
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
     * Method to add diplomacy player
     *
     * @param p_DiplomacyPlayer Name of diplomacy player
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

    /**
     * Method to set all owned countries
     *
     * @param p_OwnedCountries Countries owned by player
     */
    public void setOwnedCountries(Set<String> p_OwnedCountries) {
        this.d_OwnedCountries = p_OwnedCountries;
    }

    /**
     * Method to set all orders list
     *
     * @param p_OrderList List of orders
     */
    public void setOrdersList(Queue<Order> p_OrderList) {
        this.d_OrderList = p_OrderList;
    }

    /**
     * Method to set all owned cards by player
     *
     * @param p_OwnedCards Set of cards owned
     */
    public void setOwedCards(HashSet<String> p_OwnedCards) {
        this.d_OwnedCards = p_OwnedCards;
    }

    /**
     * Method to set all diplomacy players
     *
     * @param p_DiplomacyPlayers Set of diplomacy players
     */
    public void setDiplomacyPlayers(HashSet<String> p_DiplomacyPlayers) {
        this.d_DiplomacyPlayers = p_DiplomacyPlayers;
    }

    /**
     * Method to get player strategy
     *
     * @return Player strategy
     */
    public PlayerStrategy getPlayerStrategy() {
        return d_PlayerStrategy;
    }

    /**
     * Method to set player strategy
     *
     * @param p_PlayerStrategy Player strategy
     */
    public void setPlayerStrategy(PlayerStrategy p_PlayerStrategy) {
        this.d_PlayerStrategy = p_PlayerStrategy;
    }

    /**
     * Method to get player strategy string
     *
     * @return Player strategy string
     */
    public String getPlayerStrategyString() {
        return this.d_PlayerStrategy.getStrategyNameString();
    }

    /**
     * Method to issue order.
     *
     * @param p_GameSession GameSession object.
     */
    public void issueOrder(GameSession p_GameSession) {
        d_PlayerStrategy.issuePlayerOrder(this, p_GameSession);
    }

    /**
     * Method to handle deploy orders.
     *
     * @param p_UserInputParts List of user input parts.
     * @param p_GameSession     GameSession object.
     */
    public void deployOrderHandler(List<String> p_UserInputParts, GameSession p_GameSession) {
        String l_CountryName = p_UserInputParts.get(1);
        int l_NumArmies = Integer.parseInt(p_UserInputParts.get(2));

        // Check if player owns the country
        if (!this.ownsCountry(l_CountryName)) {
            // If does not own country then check if country is neighbour to one of player's countries
            if (Collections.disjoint(this.getOwnedCountries(), p_GameSession.getCountriesInSession().get(l_CountryName).getAdjacentCountries().values())) {
                d_LogEntryBuffer.logData("You do not own any countries adjacent to " + l_CountryName);
                return;
            } else if (p_GameSession.getCountriesInSession().get(l_CountryName).getOwner() != null) { // If owns adjacent country then check if target country is not owned by anyone
                d_LogEntryBuffer.logData("Country is owned by another player, please use the attack command to capture this country");
                return;
            } else { // If all above validations passed then allow deploy command
                d_LogEntryBuffer.logData("Can deploy armies to unowned country " + l_CountryName);
            }

        }
        // Do not allow if player has diplomacy with target country owner
        String l_TargetCountryOwner = p_GameSession.getCountriesInSession().get(p_UserInputParts.get(1)).getOwner();
        if (l_TargetCountryOwner != null && this.hasDiplomacyWith(l_TargetCountryOwner)) {
            d_LogEntryBuffer.logData("Cannot deploy. You are negotiating with the owner" + l_TargetCountryOwner);
            return;
        }
        if (l_NumArmies > this.getNumberOfArmies()) {
            d_LogEntryBuffer.logData("You do not have enough armies to deploy");
            return;
        }
        this.addArmies(-l_NumArmies);
        this.addDeployOrder(
                l_CountryName,
                l_NumArmies
        );
    }

    /**
     * Method to handle attack orders.
     *
     * @param p_UserInputParts List of user input parts.
     * @param p_GameSession      GameSession object.
     */
    public void advanceOrderHandler(List<String> p_UserInputParts, GameSession p_GameSession) {
        String l_SourceCountryName = p_UserInputParts.get(1);
        String l_TargetCountryName = p_UserInputParts.get(2);
        int l_NumArmies = Integer.parseInt(p_UserInputParts.get(3));
        // Check if player owns the country
        if (!this.ownsCountry(l_SourceCountryName)) {
            d_LogEntryBuffer.logData("You do not own the source country " + l_SourceCountryName);
        }
        if (!this.ownsCountry(l_TargetCountryName)) {
            // If does not own country then check if country is neighbour to one of player's countries
            if (Collections.disjoint(this.getOwnedCountries(), p_GameSession.getCountriesInSession().get(l_TargetCountryName).getAdjacentCountries().values())) {
                d_LogEntryBuffer.logData("You do not own any countries adjacent to " + l_TargetCountryName);
                return;
            } else { // If all above validations passed then allow deploy command
                d_LogEntryBuffer.logData("Can deploy armies to country " + l_TargetCountryName);
            }
        }
        // Do not allow if source country has less than 1 armies remaining
        if (l_NumArmies >= p_GameSession.getCountriesInSession().get(l_SourceCountryName).getNumberOfArmies()) {
            d_LogEntryBuffer.logData("Cannot attack. You need to have minimum 1 army remaining in each country");
            return;
        }
        // Do not allow if player has diplomacy with target country owner
        String l_TargetCountryOwner = p_GameSession.getCountriesInSession().get(p_UserInputParts.get(2)).getOwner();
        if (l_TargetCountryOwner != null && this.hasDiplomacyWith(l_TargetCountryOwner)) {
            d_LogEntryBuffer.logData("Cannot attack. You are negotiating with the owner" + l_TargetCountryOwner);
            return;
        }
        this.addAttackOrder(
                l_SourceCountryName,
                l_TargetCountryName,
                l_NumArmies
        );

    }

    /**
     * Method to handle bomb orders.
     *
     * @param p_UserInputParts List of user input parts.
     * @param p_GameSession      GameSession object.
     */
    public void bombOrderHandler(List<String> p_UserInputParts, GameSession p_GameSession) {
        // Validations
        if (!p_GameSession.isCountryExists(p_UserInputParts.get(1))) {
            d_LogEntryBuffer.logData("Country does not exist");
            return;
        }
        // Do not allow if player has diplomacy with target country owner
        String l_TargetCountryOwner = p_GameSession.getCountriesInSession().get(p_UserInputParts.get(1)).getOwner();
        if (l_TargetCountryOwner != null && this.hasDiplomacyWith(l_TargetCountryOwner)) {
            d_LogEntryBuffer.logData("Cannot bomb. You are negotiating with the owner" + l_TargetCountryOwner);
            return;
        }

        // If player owns card then allow the order
        if (this.useCard(WarzoneConstants.BOMB)) {
            this.addBombOrder(
                    p_UserInputParts.get(1)
            );
        } else {
            d_LogEntryBuffer.logData("You do not own the bomb card");
            return;
        }
    }

    /**
     * Method to handle blockade orders.
     *
     * @param p_UserInputParts List of user input parts.
     * @param p_GameSession      GameSession object.
     */
    public void blockadeOrderHandler(List<String> p_UserInputParts, GameSession p_GameSession) {
        // Validations
        if (!p_GameSession.isCountryExists(p_UserInputParts.get(1))) {
            d_LogEntryBuffer.logData("Country does not exist");
            return;
        }

        if (this.useCard(WarzoneConstants.BLOCKADE)) {
            this.addBlockadeOrder(
                    p_UserInputParts.get(1)
            );
        } else {
            d_LogEntryBuffer.logData("You do not own the blockade card");
            return;
        }
    }

    /**
     * Method to handle airlift orders.
     *
     * @param p_UserInputParts List of user input parts.
     * @param p_GameSession      p_GameSession object.
     */
    public void airliftOrderHandler(List<String> p_UserInputParts, GameSession p_GameSession) {
        String l_SourceCountryName = p_UserInputParts.get(1);
        int l_NumArmies = Integer.parseInt(p_UserInputParts.get(3));
        // Check if player owns the country
        if (!this.ownsCountry(l_SourceCountryName)) {
            d_LogEntryBuffer.logData("You do not own the source country " + l_SourceCountryName);
        }
        // Do not allow if source country has less than 1 armies remaining
        if (l_NumArmies >= p_GameSession.getCountriesInSession().get(l_SourceCountryName).getNumberOfArmies()) {
            d_LogEntryBuffer.logData("Cannot airlift. You need to have minimum 1 army remaining in each country");
            return;
        }
        // Do not allow if player has diplomacy with target country owner
        String l_TargetCountryOwner = p_GameSession.getCountriesInSession().get(p_UserInputParts.get(2)).getOwner();
        if (l_TargetCountryOwner != null && this.hasDiplomacyWith(l_TargetCountryOwner)) {
            d_LogEntryBuffer.logData("Cannot airlift. You are negotiating with the owner" + l_TargetCountryOwner);
            return;
        }

        if (this.useCard(WarzoneConstants.AIRLIFT)) {
            this.addAirliftOrder(
                    p_UserInputParts.get(1),
                    p_UserInputParts.get(2),
                    Integer.parseInt(p_UserInputParts.get(3))
            );
        } else {
            d_LogEntryBuffer.logData("You do not own the airlift card");
            return;
        }
    }

    /**
     * Method to handle negotiate orders.
     *
     * @param p_UserInputParts List of user input parts.
     * @param p_GameSession      GameSession object.
     */
    public void negotiateOrderHandler(List<String> p_UserInputParts, GameSession p_GameSession) {
        // Validations
        // Do not allow to diplomatically negotiate with self
        if (p_UserInputParts.get(1).equals(this.getName())) {
            d_LogEntryBuffer.logData("Cannot negotiate with self");
            return;
        }

        // Do not allow to negotiate if attack order is already issued
        for (Order l_Order : this.getOrderList()) {
            if (l_Order.getOrderType().equals(WarzoneConstants.ADVANCE)) {
                String l_TargetCountry = l_Order.getOrderDetails().getTargetCountry();
                String l_TargetCountryOwner = p_GameSession.getCountriesInSession().get(l_TargetCountry).getOwner();
                if (l_TargetCountryOwner != null && l_TargetCountryOwner.equals(p_UserInputParts.get(1))) {
                    d_LogEntryBuffer.logData("Cannot negotiate with " + p_UserInputParts.get(1) + " as you have already issued attack order on " + l_TargetCountry);
                    return;
                }
            }
        }

        if (this.useCard(WarzoneConstants.NEGOTIATE)) {
            this.addDiplomacyOrder(
                    p_UserInputParts.get(1)
            );
        } else {
            d_LogEntryBuffer.logData("You do not own the negotiate card");
            return;
        }
    }
}
