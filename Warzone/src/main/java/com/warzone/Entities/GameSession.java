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

    public void createContinent(String p_ContinentName, String p_ControlValue) throws Exception {
        if (!p_ControlValue.matches("\\d+")) {
            throw new Exception("Invalid format for continent control value");
        }
        if (d_CurrGameSession.getContinentsInSession().containsKey(p_ContinentName)) {
            System.out.println("The continent " + p_ContinentName + " already exists.");
            throw new Exception("Continent with id: " + p_ContinentName + " already exists");
        }

        Continent l_NewContinent = new Continent();
        l_NewContinent.set_Name(p_ContinentName);
        l_NewContinent.set_ControlValue(Integer.parseInt(p_ControlValue));
        d_CurrGameSession.getContinentsInSession().put(p_ContinentName, l_NewContinent);
        d_CurrGameSession.getContinentsInOrder().add(p_ContinentName);
        System.out.println("Continent created: " + p_ContinentName + ", " + p_ControlValue);
    }

    public void createCountry(String p_CountryName, String p_ContinentName, long p_CountryId) throws Exception {
        if (!d_CurrGameSession.getContinentsInSession().containsKey(p_ContinentName)) {
            System.out.println("The country " + p_ContinentName + " doesn't exist");
            throw new Exception("The country " + p_ContinentName + " doesn't exist");
        }
        if (!d_CurrGameSession.getCountriesInSession().containsKey(p_CountryName)) {
            System.out.println("The country " + p_ContinentName + " already exists");
            throw new Exception("The country " + p_ContinentName + " already exists");
        }
        Country l_NewCountry = new Country(p_CountryId, p_CountryName, p_ContinentName);
        d_CurrGameSession.getCountriesInSession().put(p_CountryName, l_NewCountry);
        d_CurrGameSession.getContinentsInSession().get(p_ContinentName).getCountries().put(p_CountryName, l_NewCountry);
        d_CurrGameSession.getCountryIds().put(p_CountryId, p_CountryName);
        System.out.println("Country created: " + p_CountryId + ", " + p_CountryName + " in " + p_ContinentName);
    }

    public void createNeighbors(String p_CountryName, String p_NeighboringCountry) throws Exception {
        Map<String, Country> l_CountriesInSession = d_CurrGameSession.getCountriesInSession();
        if (!l_CountriesInSession.containsKey(p_CountryName)) {
            System.out.println("The country " + p_CountryName + " doesn't exist");
            throw new Exception("The country " + p_CountryName + " doesn't exist");
        }
        if (!l_CountriesInSession.containsKey(p_NeighboringCountry)) {
            throw new Exception("The country " + p_NeighboringCountry + " doesn't exist");
        }

        Country l_Country = l_CountriesInSession.get(p_CountryName);
        Country l_NeighboringCountry = l_CountriesInSession.get(p_NeighboringCountry);
        l_Country.addAdjacentCountry(l_NeighboringCountry.get_Id(), p_NeighboringCountry);
        l_NeighboringCountry.addAdjacentCountry(l_Country.get_Id(), p_CountryName);
        System.out.println("Neighbors created, The " + p_CountryName + " neighbors with " + p_NeighboringCountry);
    }

    public void clearExistingMap() {
        d_CurrGameSession.getPlayers().clear();
        d_CurrGameSession.getContinentsInSession().clear();
        d_CurrGameSession.getContinentsInOrder().clear();
        d_CurrGameSession.getCountriesInSession().clear();
        d_CurrGameSession.getCountryIds();
    }

    public void deleteContinent(String p_ContinentName) throws Exception {
        if (!d_CurrGameSession.getContinentsInSession().containsKey(p_ContinentName)) {
            System.out.println("The Continent " + p_ContinentName + "doesn't exist");
            throw new Exception("The Continent " + p_ContinentName + "doesn't exist");
        }
        d_CurrGameSession.getContinentsInSession().remove(p_ContinentName);
        d_CurrGameSession.getContinentsInOrder().remove(p_ContinentName);
        System.out.println("The Continent " + p_ContinentName + " has been successfully deleted");
    }


    public void deleteCountry(String p_CountryName) throws Exception {
        if (!d_CurrGameSession.getCountriesInSession().containsKey(p_CountryName)) {
            System.out.println("The Country " + p_CountryName + " doesn't exist");
            throw new Exception("The Country " + p_CountryName + " doesn't exist");
        }
        String l_ContinentOfCountry = d_CurrGameSession.d_CountriesInSession.get(p_CountryName).get_IsInContinent();
        Long l_CountryId = d_CurrGameSession.getCountriesInSession().get(p_CountryName).get_Id();
        d_CurrGameSession.getCountriesInSession().remove(p_CountryName);
        d_CurrGameSession.getContinentsInSession().get(l_ContinentOfCountry).getCountries().remove(p_CountryName);
        d_CurrGameSession.getCountryIds().remove(l_CountryId);
        System.out.println("Country " + p_CountryName + " successfully deleted");
        }

    public void deleteNeighbor(String p_CountryName, String p_NeighboringCountry) throws Exception {
        Map<String, Country> l_CountriesInSession = d_CurrGameSession.getCountriesInSession();
        if (!l_CountriesInSession.containsKey(p_CountryName)) {
            System.out.println("The country " + p_CountryName + " doesn't exist");
            throw new Exception("The country " + p_CountryName + " doesn't exist");
        }
        if (!l_CountriesInSession.containsKey(p_NeighboringCountry)) {
            System.out.println("The country " + p_NeighboringCountry + " doesn't exist");
            throw new Exception("The country " + p_NeighboringCountry + " doesn't exist");
        }
        if (!l_CountriesInSession.get(p_CountryName).getD_AdjacentCountries().containsValue(p_NeighboringCountry)) {
            System.out.println("The country " + p_CountryName + "and " + p_NeighboringCountry + "are not neighbors");
            throw new Exception("The country " + p_CountryName + "and " + p_NeighboringCountry + "are not neighbors");
        }

        Country l_Country = l_CountriesInSession.get(p_CountryName);
        Country l_NeighboringCountry = l_CountriesInSession.get(p_NeighboringCountry);
        l_Country.getD_AdjacentCountries().remove(l_NeighboringCountry.get_Id());
        l_NeighboringCountry.getD_AdjacentCountries().remove((l_Country.get_Id()));
        System.out.println("Neighbor removed, between " + p_CountryName + " and " + p_NeighboringCountry);
    }
}

