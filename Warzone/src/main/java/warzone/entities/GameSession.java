package main.java.warzone.entities;

import main.java.warzone.exceptions.WarzoneValidationException;

import java.util.*;

/**
 * This class is designed for managing the session of the main.java.game
 * Each session represents a distinct part the main.java.game including phase, players, map of continents and countries
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */


public class GameSession {

    /**
     * Current session of the main.java.game
     */
    private static GameSession d_CurrGameSession;

    /**
     * Current phase of the main.java.game
     */
    private GamePhase d_CurrGamePhase;

    /**
     * Map containing name of players with their corresponding
     * player objects
     */
    private Map<String, Player> d_Players;

    /**
     * Map of continents in session with their
     * corresponding continent objects
     */
    private Map<String, Continent> d_ContinentsInSession;

    /**
     * Map of countries in session with their
     * corresponding country objects
     */
    private Map<String, Country> d_CountriesInSession;

    /**
     * List maintaining the order of continents in session
     */
    private List<String> d_ContinentsInOrder;

    /**
     * Map maintaining ids of countries to their names
     */
    private Map<Long, String> d_CountryIdsToCountryNames;

    /**
     * Default constructor
     */
    private GameSession() {

        d_CurrGamePhase = null;
        d_Players = new HashMap<>();
        d_ContinentsInSession = new HashMap<>();
        d_CountriesInSession = new HashMap<>();
        d_ContinentsInOrder = new ArrayList<>();
        d_CountryIdsToCountryNames = new HashMap<>();
    }

    /**
     * Makes the instance of the GameSession.
     *
     * @return The single instance of GameSession.
     */
    public static GameSession getInstance() {
        if (d_CurrGameSession == null) {
            d_CurrGameSession = new GameSession();
        }
        return d_CurrGameSession;
    }

    /**
     * Retrieves the current main.java.game phase.
     *
     * @return The current main.java.game phase.
     */
    public GamePhase getCurrGamePhase(){

        return this.d_CurrGamePhase;
    }

    /**
     * Retrieves the map of players in the main.java.game session.
     *
     * @return The map of players.
     */
    public Map<String, Player> getPlayers() {

        return this.d_Players;
    }

    /**
     * Retrieves the map of continents in the main.java.game session.
     *
     * @return The map of continents.
     */
    public Map<String, Continent> getContinentsInSession() {

        return this.d_ContinentsInSession;
    }

    public Map<String, Country> getCountriesInSession() {

        return this.d_CountriesInSession;
    }

    /**
     * Retrieves the map of countries in the main.java.game session.
     *
     * @return The map of countries.
     */
    public List<String> getContinentsInOrder() {

        return this.d_ContinentsInOrder;
    }

    /**
     * Retrieves the list maintaining the order of continents in the main.java.game session.
     *
     * @return The list of continents in order.
     */
    public Map<Long, String> getCountryIds() {

        return this.d_CountryIdsToCountryNames;
    }

    /**
     * Sets the current main.java.game phase.
     *
     * @param p_CurrGamePhase The main.java.game phase to set.
     */
    public void setCurrGamePhase(GamePhase p_CurrGamePhase){

        this.d_CurrGamePhase = p_CurrGamePhase;
    }

    /**
     * Creates a new continent with the specified name and control value.
     *
     * @param p_ContinentName   The name of the continent to create.
     * @param p_ControlValue    The control value of the continent.
     * @throws WarzoneValidationException if the continent already exists or if the control value has an invalid format.
     */
    public void createContinent(String p_ContinentName, String p_ControlValue) throws WarzoneValidationException {
        if (!p_ControlValue.matches("\\d+")) {
            throw new WarzoneValidationException("Invalid format for continent control value");
        }
        if (d_CurrGameSession.getContinentsInSession().containsKey(p_ContinentName)) {
            System.out.println("The continent " + p_ContinentName + " already exists.");
            throw new WarzoneValidationException("Continent with id: " + p_ContinentName + " already exists");
        }

        Continent l_NewContinent = new Continent();
        l_NewContinent.setName(p_ContinentName);
        l_NewContinent.setControlValue(Integer.parseInt(p_ControlValue));
        d_CurrGameSession.getContinentsInSession().put(p_ContinentName, l_NewContinent);
        d_CurrGameSession.getContinentsInOrder().add(p_ContinentName);
        System.out.println("Continent created: " + p_ContinentName + ", " + p_ControlValue);
    }

    /**
     * Creates a new country with the specified name, continent, and ID.
     *
     * @param p_CountryName    The name of the country to create.
     * @param p_ContinentName  The name of the continent where the country belongs.
     * @param p_CountryId      The ID of the country to create.
     * @throws Exception if the specified continent doesn't exist or if a country with the same name already exists.
     */
    public void createCountry(String p_CountryName, String p_ContinentName, long p_CountryId) throws WarzoneValidationException {
        if (!d_CurrGameSession.getContinentsInSession().containsKey(p_ContinentName)) {
            System.out.println("The country " + p_ContinentName + " doesn't exist");
            throw new WarzoneValidationException("The country " + p_ContinentName + " doesn't exist");
        }
        if (!d_CurrGameSession.getCountriesInSession().containsKey(p_CountryName)) {
            System.out.println("The country " + p_ContinentName + " already exists");
            throw new WarzoneValidationException("The country " + p_ContinentName + " already exists");
        }
        Country l_NewCountry = new Country(p_CountryId, p_CountryName, p_ContinentName);
        d_CurrGameSession.getCountriesInSession().put(p_CountryName, l_NewCountry);
        d_CurrGameSession.getContinentsInSession().get(p_ContinentName).getCountries().put(p_CountryName, l_NewCountry);
        d_CurrGameSession.getCountryIds().put(p_CountryId, p_CountryName);
        System.out.println("Country created: " + p_CountryId + ", " + p_CountryName + " in " + p_ContinentName);
    }

    /**
     * Creates a neighboring relationship between two countries.
     *
     * @param p_CountryName         The name of the country to create the neighboring relationship for.
     * @param p_NeighboringCountry  The name of the neighboring country.
     * @throws Exception if either the specified country or neighboring country doesn't exist.
     */
    public void createNeighbors(String p_CountryName, String p_NeighboringCountry) throws WarzoneValidationException {
        Map<String, Country> l_CountriesInSession = d_CurrGameSession.getCountriesInSession();
        if (!l_CountriesInSession.containsKey(p_CountryName)) {
            System.out.println("The country " + p_CountryName + " doesn't exist");
            throw new WarzoneValidationException("The country " + p_CountryName + " doesn't exist");
        }
        if (!l_CountriesInSession.containsKey(p_NeighboringCountry)) {
            throw new WarzoneValidationException("The country " + p_NeighboringCountry + " doesn't exist");
        }

        Country l_Country = l_CountriesInSession.get(p_CountryName);
        Country l_NeighboringCountry = l_CountriesInSession.get(p_NeighboringCountry);
        l_Country.AddAdjacentCountry(l_NeighboringCountry.getId(), p_NeighboringCountry);
        l_NeighboringCountry.AddAdjacentCountry(l_Country.getId(), p_CountryName);
        System.out.println("Neighbors created, The " + p_CountryName + " neighbors with " + p_NeighboringCountry);
    }

    /**
     * Clears the existing map of players, continents, countries, and country IDs.
     */
    public void clearExistingMap() {
        d_CurrGameSession.getPlayers().clear();
        d_CurrGameSession.getContinentsInSession().clear();
        d_CurrGameSession.getContinentsInOrder().clear();
        d_CurrGameSession.getCountriesInSession().clear();
        d_CurrGameSession.getCountryIds();
    }

    /**
     * Deletes the continent with the specified name.
     *
     * @param p_ContinentName   The name of the continent to delete.
     * @throws WarzoneValidationException if the continent doesn't exist.
     */
    public void deleteContinent(String p_ContinentName) throws WarzoneValidationException {
        if (!d_CurrGameSession.getContinentsInSession().containsKey(p_ContinentName)) {
            System.out.println("The Continent " + p_ContinentName + "doesn't exist");
            throw new WarzoneValidationException("The Continent " + p_ContinentName + "doesn't exist");
        }
        d_CurrGameSession.getContinentsInSession().remove(p_ContinentName);
        d_CurrGameSession.getContinentsInOrder().remove(p_ContinentName);
        System.out.println("The Continent " + p_ContinentName + " has been successfully deleted");
    }
    /**
     * Deletes the country with the specified name.
     *
     * @param p_CountryName     The name of the country to delete.
     * @throws WarzoneValidationException if the country doesn't exist.
     */
    public void deleteCountry(String p_CountryName) throws WarzoneValidationException {
        if (!d_CurrGameSession.getCountriesInSession().containsKey(p_CountryName)) {
            System.out.println("The Country " + p_CountryName + " doesn't exist");
            throw new WarzoneValidationException("The Country " + p_CountryName + " doesn't exist");
        }
        String l_ContinentOfCountry = d_CurrGameSession.d_CountriesInSession.get(p_CountryName).getIsInContinent();
        Long l_CountryId = d_CurrGameSession.getCountriesInSession().get(p_CountryName).getId();
        d_CurrGameSession.getCountriesInSession().remove(p_CountryName);
        d_CurrGameSession.getContinentsInSession().get(l_ContinentOfCountry).getCountries().remove(p_CountryName);
        d_CurrGameSession.getCountryIds().remove(l_CountryId);
        System.out.println("Country " + p_CountryName + " successfully deleted");
        }

    /**
     * Deletes the neighboring relationship between two countries.
     *
     * @param p_CountryName         The name of the country to delete the neighboring relationship for.
     * @param p_NeighboringCountry  The name of the neighboring country.
     * @throws WarzoneValidationException if either the specified country or neighboring country doesn't exist.
     */
    public void deleteNeighbor(String p_CountryName, String p_NeighboringCountry) throws WarzoneValidationException {
        Map<String, Country> l_CountriesInSession = d_CurrGameSession.getCountriesInSession();
        if (!l_CountriesInSession.containsKey(p_CountryName)) {
            System.out.println("The country " + p_CountryName + " doesn't exist");
            throw new WarzoneValidationException("The country " + p_CountryName + " doesn't exist");
        }
        if (!l_CountriesInSession.containsKey(p_NeighboringCountry)) {
            System.out.println("The country " + p_NeighboringCountry + " doesn't exist");
            throw new WarzoneValidationException("The country " + p_NeighboringCountry + " doesn't exist");
        }
        if (!l_CountriesInSession.get(p_CountryName).getAdjacentCountries().containsValue(p_NeighboringCountry)) {
            System.out.println("The country " + p_CountryName + "and " + p_NeighboringCountry + "are not neighbors");
            throw new WarzoneValidationException("The country " + p_CountryName + "and " + p_NeighboringCountry + "are not neighbors");
        }

        Country l_Country = l_CountriesInSession.get(p_CountryName);
        Country l_NeighboringCountry = l_CountriesInSession.get(p_NeighboringCountry);
        l_Country.getAdjacentCountries().remove(l_NeighboringCountry.getId());
        l_NeighboringCountry.getAdjacentCountries().remove((l_Country.getId()));
        System.out.println("Neighbor removed, between " + p_CountryName + " and " + p_NeighboringCountry);
    }

    /**
     * Creates a new player with the specified name.
     *
     * @param p_PlayerName  The name of the player to create.
     * @throws WarzoneValidationException if a player with the same name already exists.
     */
    public void createPlayer(String p_PlayerName) throws WarzoneValidationException {
        if (d_CurrGameSession.getPlayers().containsKey(p_PlayerName)) {
            System.out.println("Player with name: "+ p_PlayerName + " already exists");
            throw new WarzoneValidationException("Player with given name " + p_PlayerName + "already exists");
        }
        Player l_CreatedPlayer = new Player(p_PlayerName);
        d_Players.put(p_PlayerName, l_CreatedPlayer);
        System.out.println("Player creation accomplished: "+ p_PlayerName);
    }

    /**
     * Deletes the player with the specified name.
     *
     * @param p_PlayerName  The name of the player to delete.
     * @throws WarzoneValidationException if the player doesn't exist.
     */
    public void deletePlayer(String p_PlayerName) throws WarzoneValidationException {
        if (!d_CurrGameSession.getPlayers().containsKey(p_PlayerName)) {
            System.out.println("Player with name: "+ p_PlayerName + " doesn't exist");
            throw new WarzoneValidationException("Player with given name: " + p_PlayerName + "doesn't exist");
        }
        d_Players.remove(p_PlayerName);
        // Remove owner from countries
        Iterator<Map.Entry<String, Country>> l_CountryIterator = d_CurrGameSession.getCountriesInSession().entrySet().iterator();
        while (l_CountryIterator.hasNext()) {
            Map.Entry<String, Country> l_CountryEntry = l_CountryIterator.next();
            if (l_CountryEntry.getValue().getOwner().equals(p_PlayerName)) {
                l_CountryEntry.getValue().setOwner(null);
            }
        }
        System.out.println("Player deletion accomplished: "+ p_PlayerName);
    }

    /**
     * Assigns the specified country to the specified player.
     *
     * @param p_PlayerName    The name of the player.
     * @param p_CountryName   The name of the country to assign.
     * @throws WarzoneValidationException if either the player or the country doesn't exist.
     */
    public void assignCountryToPlayer(String p_PlayerName, String p_CountryName) throws WarzoneValidationException {
        if (!d_CurrGameSession.getPlayers().containsKey(p_PlayerName)) {
            throw new WarzoneValidationException("Player with given name: " + p_PlayerName + "doesn't exist");
        }
        if (!d_CurrGameSession.getCountriesInSession().containsKey(p_CountryName)) {
            throw new WarzoneValidationException("Country with given name: " + p_CountryName + "doesn't exist");
        }


        d_CurrGameSession.getPlayers().get(p_PlayerName).addOwnedCountry(p_CountryName);
        d_CurrGameSession.getCountriesInSession().get(p_CountryName).setOwner(p_PlayerName);
        System.out.println("Country: " + p_CountryName + " assigned to player: " + p_PlayerName);
    }

}

