package main.java.warzone.entities;

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
 * @version 1.0.0
 */

public class Order {

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
     * Default constructor
     */
    public Order(String p_PlayerName, String p_TargetCountry, int p_NumberOfArmies) {
        this.d_PlayerName = p_PlayerName;
        this.d_TargetCountry = p_TargetCountry;
        this.d_NumberOfArmies = p_NumberOfArmies;
        this.d_SourceCountry = null;
    }

    /**
     *
     * @exclude
     */
    public Order(String p_PlayerName, String p_SourceCountry, String p_TargetCountry, int p_NumberOfArmies) {
        this.d_PlayerName = p_PlayerName;
        this.d_SourceCountry = p_SourceCountry;
        this.d_TargetCountry = p_TargetCountry;
        this.d_NumberOfArmies = p_NumberOfArmies;
    }


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

    /**
     * Executes the order, either as a deploy order or an attack order, based on the source country.
     *
     * @param gameSession The main.java.game session in which the order is executed.
     */
    public void execute(GameSession gameSession) {
        if (this.d_SourceCountry != null) {
            System.out.println("Executing attack order for " + this.d_PlayerName + " on " + this.d_TargetCountry + " from " + this.d_SourceCountry + " with " + this.d_NumberOfArmies + " armies");
            this.executeAttackOrder(gameSession);
        } else {
            System.out.println("Executing deploy order for " + this.d_PlayerName + " on " + this.d_TargetCountry + " with " + this.d_NumberOfArmies + " armies");
            this.executeDeployOrder(gameSession);
        }
    }

    /**
     * Executes the logic for a deploy order, including updating territory ownership and army counts.
     *
     * @param gameSession The main.java.game session in which the order is executed.
     */
    private void executeDeployOrder(GameSession gameSession) {
        Country targetCountry = gameSession.getCountriesInSession().get(this.d_TargetCountry);
        // If not owned by player then subtract armies & update owner
        if (targetCountry.getOwner() == null || !targetCountry.getOwner().equals(this.d_PlayerName)) {
            if (this.d_NumberOfArmies > targetCountry.getNumberOfArmies()) {
                System.out.println("Successfully captured territory.");
                if (targetCountry.getOwner() != null) {
                    Player oldOwner = gameSession.getPlayers().get(targetCountry.getOwner());
                    oldOwner.removeOwnedCountry(this.d_TargetCountry);
                }
                targetCountry.setOwner(this.d_PlayerName);
                Player newOwner = gameSession.getPlayers().get(this.d_PlayerName);
                newOwner.addOwnedCountry(targetCountry.getName());
                targetCountry.setNumberOfArmies(this.d_NumberOfArmies - targetCountry.getNumberOfArmies());
            } else {
                System.out.println("Failed to capture territory.");
                targetCountry.setNumberOfArmies(targetCountry.getNumberOfArmies() - this.d_NumberOfArmies);
            }

        } else {
            System.out.println("Adding armies to owned territory.");
            targetCountry.addArmies(this.d_NumberOfArmies);

        }
    }

    /**
     * Executes the logic for an attack order, including updating territory ownership and army counts.
     *
     * @param gameSession The main.java.game session in which the order is executed.
     */
    private void executeAttackOrder(GameSession gameSession) {
        Country sourceCountry = gameSession.getCountriesInSession().get(this.d_SourceCountry);
        Country targetCountry = gameSession.getCountriesInSession().get(this.d_TargetCountry);
        // If not owned by player then subtract armies & update owner
        if (targetCountry.getOwner() == null || !targetCountry.getOwner().equals(this.d_PlayerName)) {
            if (this.d_NumberOfArmies > targetCountry.getNumberOfArmies()) {
                System.out.println("Successfully captured territory.");
                if (targetCountry.getOwner() != null) {
                    Player oldOwner = gameSession.getPlayers().get(targetCountry.getOwner());
                    oldOwner.removeOwnedCountry(this.d_TargetCountry);
                }
                targetCountry.setOwner(this.d_PlayerName);
                Player newOwner = gameSession.getPlayers().get(this.d_PlayerName);
                newOwner.addOwnedCountry(targetCountry.getName());
                targetCountry.setNumberOfArmies(this.d_NumberOfArmies - targetCountry.getNumberOfArmies());
            } else {
                System.out.println("Failed to capture territory.");
                targetCountry.setNumberOfArmies(targetCountry.getNumberOfArmies() - this.d_NumberOfArmies);
            }

        } else {
            System.out.println("Adding armies to owned territory.");
            targetCountry.addArmies(this.d_NumberOfArmies);
        }

        // Reduce source country armies
        sourceCountry.addArmies(-this.d_NumberOfArmies);
    }


}
