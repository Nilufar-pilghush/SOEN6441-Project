package main.java.com.warzone.Entities;


import main.java.com.warzone.Service.impl.GameMapDataHandlerImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameSession {

    private static GameSession d_CurrGameSession;
    private GamePhase d_CurrGamePhase;
    private Map<String, Player> d_Players;
    private Map<String, Continent> d_ContinentsInSession;
    private Map<String, Country> d_CountriesInSession;
    private List<String> d_ContinentsInOrder;
    private Map<Long, String> d_CountryIdsToCountryNames;


    private GameSession() {

        d_CurrGamePhase = null;
        d_Players = new HashMap<>();
        d_ContinentsInSession = new HashMap<>();
        d_CountriesInSession = new HashMap<>();
        d_ContinentsInOrder = new ArrayList<>();
        d_CountryIdsToCountryNames = new HashMap<>();
    }

    public static GameSession getInstance() {
        if (d_CurrGameSession == null) {
            d_CurrGameSession = new GameSession();
        }
        return d_CurrGameSession;
    }

    //get methods

    public GamePhase getCurrGamePhase(){

        return this.d_CurrGamePhase;
    }

    public Map<String, Player> getPlayers() {

        return this.d_Players;
    }

    public Map<String, Continent> getContinentsInSession() {

        return this.d_ContinentsInSession;
    }

    public Map<String, Country> getCountriesInSession() {

        return this.d_CountriesInSession;
    }

    public List<String> getContinentsInOrder() {

        return this.d_ContinentsInOrder;
    }

    public Map<Long, String> getCountryIds() {

        return this.d_CountryIdsToCountryNames;
    }


    //needed set methods
    public void setCurrGamePhase(GamePhase p_CurrGamePhase){

        this.d_CurrGamePhase = p_CurrGamePhase;
    }

    public void createContinent(String p_ContinentName, String p_ControlValue) {
        System.out.println("Continent created: " + p_ContinentName + ", " + p_ControlValue);
    }

    public void createCountry(String p_CountryName, String p_ContinentName, long p_CountryId) {
        System.out.println("Country created: " + p_CountryId + ", " + p_CountryName + " in " + p_ContinentName);
    }

    public void createNeighbors(String p_countryName, String p_NeighboringCountry) {
        System.out.println("Neighbors created: " + p_countryName + " neighbors with " + p_NeighboringCountry);

    }
}

