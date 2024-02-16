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

    //get methods

    public Long get_Id() {
        return d_Id;
    }

    public String get_Name() {
        return d_Name;
    }

    public String get_Owner() {
        return d_Owner;
    }

    public int get_NumberOfArmies() {
        return d_NumberOfArmies;
    }

    public int getNumberOfArmies() { return d_NumberOfArmies;}

    public Map<Long, String> getAdjacentCountries() {
        return this.d_AdjacentCountries;
    }

    public String getOwner() { return d_Owner;}

    public String get_IsInContinent() {
        return d_IsInContinent;
    }

    public Map<Long, String> getD_AdjacentCountries() {
        return d_AdjacentCountries;
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

    public void setNumberOfArmies(int p_NumberOfArmies) {
        this.d_NumberOfArmies = p_NumberOfArmies;
    }

    public void setOwner(String p_owner) {this.d_Owner = p_owner;}

    //method to add armies to each country
    public int addArmies(int p_NumberOfArmies) {
        return this.d_NumberOfArmies += p_NumberOfArmies;
    }


}
