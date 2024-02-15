package main.java.com.warzone.Entities;

import java.util.HashMap;
import java.util.Map;

public class Country {

    private Long d_Id;
    private String d_Name;

    private String d_Owner;

    private int d_NumberOfArmies;

    private String d_IsInContinent;

    private Map<Long, String> d_AdjacentCountries;

    /**
     * The name of the continent in which the country is present.
     */
    private String d_PresentInContinent;


    /**
     * Constructs a new Country with the specified name, continent, and ID.
     *
     * @param p_Name Name of the country.
     * @param p_PresentInContinent Continent in which the country is present.
     * @param p_Id Unique identifier for the country.
     */
    public Country(String p_Name, String p_PresentInContinent, Long p_Id){
        this.d_Name = p_Name;
        this.d_PresentInContinent = p_PresentInContinent;
        this.d_Id = p_Id;
        this.d_Owner = null;
        d_AdjacentCountries = new HashMap<>();
        // Default number of armies in a country is 1
        this.d_NumberOfArmies = 1;
    }

    //get methods

    public Long get_Id() {
        return d_Id;
    }

    /**
     * Method to get the name of the country.
     *
     * @return Name of the country.
     */
    public String getName() {
        return this.d_Name;
    }

    public String get_Owner() {
        return d_Owner;
    }

    public int get_NumberOfArmies() {
        return d_NumberOfArmies;
    }

    public String get_IsInContinent() {
        return d_IsInContinent;
    }

    /**
     * Method to get a map of adjacent countries to this country with their IDs and names.
     *
     * @return Map of adjacent countries.
     */
    public Map<Long, String> getAdjacentCountries() {
        return this.d_AdjacentCountries;
    }

    public void AddAdjacentCountry(Long p_AdjacentCountryId, String p_AdjacentCountry){

           this.d_AdjacentCountries.put(p_AdjacentCountryId,p_AdjacentCountry);
    }

    public boolean isAdjacentTo(String p_CountryName) {

        return this.d_AdjacentCountries.containsValue(p_CountryName);
    }

    //needed set methods

    public void set_Name(String p_Name) {
        this.d_Name = p_Name;
    }

    public void set_Owner(String p_Owner) {
        this.d_Owner = p_Owner;
    }

    public void set_IsInContinent(String p_IsInContinent) {
        this.d_IsInContinent = p_IsInContinent;
    }

    //method to add armies to each country
    public int addArmies(int p_NumberOfArmies) {
        return  this.d_NumberOfArmies += p_NumberOfArmies;
    }


    /**
     * Method to get the name of the continent in which the country is present.
     *
     * @return Name of the continent.
     */
    public String getPresentInContinent() {
        return d_PresentInContinent;
    }

}
