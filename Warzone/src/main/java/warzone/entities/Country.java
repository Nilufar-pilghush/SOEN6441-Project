package main.java.warzone.entities;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a country in the Warzone main.java.game.
 *
 * This class encapsulates the properties and behavior of a country in the Warzone main.java.game,
 * including its unique ID, name, owner, number of armies, continent it belongs to, and adjacent countries.
 * Countries can have armies deployed on them and can be owned by players.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */

public class Country {

    /**
     * The unique ID of the country.
     */
    private Long d_Id;

    /**
     * The name of the country.
     */
    private String d_Name;

    /**
     * The owner of the country.
     */
    private String d_Owner;

    /**
     * The number of armies currently deployed on the country.
     */
    private int d_NumberOfArmies;

    /**
     * The continent where the country is located.
     */
    private String d_IsInContinent;

    /**
     * The map of adjacent countries to the country.
     */
    private Map<Long, String> d_AdjacentCountries;

    //constructor
    public Country(long p_Id, String p_Name, String p_IsInContinent) {

        this.d_Id = p_Id;
        this.d_Name = p_Name;
        this.d_Owner = null;
        //default number of armies in each country
        this.d_NumberOfArmies = 1;
        this.d_IsInContinent = p_IsInContinent;
        this.d_AdjacentCountries = new HashMap<>();

    }

    /**
     * Retrieves the unique ID of the country.
     *
     * @return The ID of the country.
     */
    public Long getId() {
        return d_Id;
    }

    /**
     * Retrieves the name of the country.
     *
     * @return The name of the country.
     */
    public String getName() {
        return d_Name;
    }

    /**
     * Retrieves the owner of the country.
     *
     * @return The owner of the country.
     */
    public String getOwner() {
        return d_Owner;
    }


    /**
     * Retrieves the number of armies currently deployed on the country.
     *
     * @return The number of armies on the country.
     */
    public int getNumberOfArmies() {
        return d_NumberOfArmies;
    }

    /**
     * Retrieves the map of adjacent countries to the country.
     *
     * @return The map of adjacent countries.
     */
    public Map<Long, String> getAdjacentCountries() {
        return this.d_AdjacentCountries;
    }

    /**
     * Retrieves the continent where the country is located.
     *
     * @return The continent of the country.
     */
    public String getIsInContinent() {
        return d_IsInContinent;
    }

    /**
     * Adds an adjacent country to the country.
     *
     * @param p_AdjacentCountryId The ID of the adjacent country.
     * @param p_AdjacentCountry   The name of the adjacent country.
     */
    public void addAdjacentCountry(Long p_AdjacentCountryId, String p_AdjacentCountry){

           this.d_AdjacentCountries.put(p_AdjacentCountryId,p_AdjacentCountry);
    }

    /**
     * Checks if the country is adjacent to a specified country.
     *
     * @param p_CountryName The name of the country to check adjacency with.
     * @return True if the country is adjacent to the specified country, otherwise false.
     */
    public boolean isAdjacentTo(String p_CountryName) {

        return this.d_AdjacentCountries.containsValue(p_CountryName);
    }

    /**
     * Sets the name of the country.
     *
     * @param p_Name The name to set for the country.
     */
    public void setName(String p_Name) {
        this.d_Name = p_Name;
    }

    /**
     * Sets the owner of the country.
     *
     * @param p_Owner The owner to set for the country.
     */
    public void setOwner(String p_Owner) {
        this.d_Owner = p_Owner;
    }

    /**
     * Sets the continent where the country is located.
     *
     * @param p_IsInContinent The continent to set for the country.
     */
    public void setIsInContinent(String p_IsInContinent) {
        this.d_IsInContinent = p_IsInContinent;
    }

    /**
     * Sets the number of armies deployed on the country.
     *
     * @param p_NumberOfArmies The number of armies to set for the country.
     */
    public void setNumberOfArmies(int p_NumberOfArmies) {
        this.d_NumberOfArmies = p_NumberOfArmies;
    }


    /**
     * Adds a specified number of armies to the country.
     *
     * @param p_NumberOfArmies The number of armies to add.
     * @return The total number of armies after addition.
     */
    public int addArmies(int p_NumberOfArmies) {
        return this.d_NumberOfArmies += p_NumberOfArmies;
    }

}
