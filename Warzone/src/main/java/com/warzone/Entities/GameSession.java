package main.java.com.warzone.Entities;


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

    //get methods

    public GamePhase getCurrGamePhase(){

        return this.d_CurrGamePhase;
    }

    public static GameSession getInstance() {
        if (d_CurrGameSession == null) {
            d_CurrGameSession = new GameSession();
        }
        return d_CurrGameSession;
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

}

