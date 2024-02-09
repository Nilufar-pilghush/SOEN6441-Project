package com.warzone.Entities;

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
        d_AdjacentCountries = new HashMap<>();

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

    public String get_IsInContinent() {
        return d_IsInContinent;
    }

    public Map<Long, String> getD_AdjacentCountries() {
        return d_AdjacentCountries;
    }

    public void AddAdjuncentCountru(Long p_AdjucentCountryId, String p_AdjucentCountry){

           this.d_AdjacentCountries.put(p_AdjucentCountryId,p_AdjucentCountry);
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




}
