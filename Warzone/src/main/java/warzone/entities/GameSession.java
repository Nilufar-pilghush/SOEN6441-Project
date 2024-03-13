package main.java.warzone.entities;

import main.java.warzone.exceptions.WarzoneValidationException;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;

import java.util.*;

/**
 * Concrete class to access and manage the parts of game session.
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */
public class GameSession {
    /**
     * Current Game session of game
     */
    private static GameSession d_CurrGameSession;

    /**
     * Map containing name of continents of session with their
     * corresponding continent objects
     */
    private Map<String, Continent> d_ContinentsInSession;

    /**
     * List maintaining the order of continents of session
     */
    private List<String> d_ContinentsInOrder;

    /**
     * Map containing name of all countries of session with their
     * corresponding country objects
     */
    private Map<String, Country> d_CountriesInSession;

    /**
     * Map maintaining ids of countries to their names
     */
    private Map<Long, String> d_CountryIdsToCountryNames;

    /**
     * Map containing name of players with their corresponding
     * player objects
     */
    private Map<String, Player> d_Players;

    /**
     * Current game phase
     */
    private GamePhase d_CurrGamePhase;

    /**
     * LogEntryBuffer object to log the information
     * and notifying all the observers
     */
    private LogEntryBuffer d_LogEntryBuffer;

    /**
     * Constructor to implement singleton design pattern so that there is
     * only one object of GameSession during entire play of game.
     */
    private GameSession() {
        d_ContinentsInSession = new HashMap<>();
        d_ContinentsInOrder = new ArrayList<>();
        d_CountriesInSession = new HashMap<>();
        d_Players = new HashMap<>();
        d_CountryIdsToCountryNames = new HashMap<>();
        d_CurrGamePhase = null;
        d_LogEntryBuffer = LogEntryBuffer.getInstance();
    }

    /**
     * Provides the singleton instance of the GameSession.
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
     * Retrieves the continents in the game session.
     *
     * @return Map of continents by their name.
     */
    public Map<String, Continent> getContinentsInSession() {
        return this.d_ContinentsInSession;
    }

    /**
     * Retrieves the order in which continents were added.
     *
     * @return List of continent names.
     */
    public List<String> getContinentsInOrder() {
        return this.d_ContinentsInOrder;
    }

    /**
     * Retrieves the countries in the game session.
     *
     * @return Map of countries by their name.
     */
    public Map<String, Country> getCountriesInSession() {
        return this.d_CountriesInSession;
    }

    /**
     * Retrieves the mapping of country IDs to country names.
     *
     * @return Map of country IDs to country names.
     */
    public Map<Long, String> getCountryIds() {
        return this.d_CountryIdsToCountryNames;
    }

    /**
     * Retrieves the players in the game.
     *
     * @return Map of players by their name.
     */
    public Map<String, Player> getPlayers() {
        return this.d_Players;
    }

    /**
     * Method to get current phase of game.
     *
     * @return Current game phase.
     */
    public GamePhase getCurrGamePhase() {
        return this.d_CurrGamePhase;
    }

    /**
     * Sets current game phase to given game phase.
     *
     * @param p_CurrGamePhase Game phase to set as current game phase.
     */
    public void setCurrGamePhase(GamePhase p_CurrGamePhase) {

        this.d_CurrGamePhase = p_CurrGamePhase;
    }

    /**
     * Creates a new continent.
     *
     * @param p_ContinentName         Name of the continent.
     * @param p_ContinentControlValue Control value (ID) of the continent.
     * @throws WarzoneValidationException If the provided continent ID format is invalid or if the continent already exists.
     */
    public void createContinent(String p_ContinentName, String p_ContinentControlValue) throws WarzoneValidationException {
        int l_ContinentControlValue = validateContinentCountryIdFormats(p_ContinentControlValue);
        if (d_CurrGameSession.getContinentsInSession().containsKey(p_ContinentName)) {
            d_LogEntryBuffer.logData("Continent with id: " + p_ContinentName + " already exists");
            throw new WarzoneValidationException("Continent with id: " + p_ContinentName + " already exists");
        }

        Continent l_CreatedContinent = new Continent();
        l_CreatedContinent.setName(p_ContinentName);
        l_CreatedContinent.setControlValue(l_ContinentControlValue);
        d_CurrGameSession.getContinentsInSession().put(p_ContinentName, l_CreatedContinent);
        d_CurrGameSession.getContinentsInOrder().add(p_ContinentName);
        d_LogEntryBuffer.logData("Continent creation accomplished: " + p_ContinentName + ", " + l_ContinentControlValue);
    }

    /**
     * Deletes a continent from the game session.
     *
     * @param p_ContinentName Name of the continent.
     * @throws WarzoneValidationException If the continent doesn't exist.
     */
    public void deleteContinent(String p_ContinentName) throws WarzoneValidationException {
        if (!d_CurrGameSession.getContinentsInSession().containsKey(p_ContinentName)) {
            d_LogEntryBuffer.logData("Continent with id: " + p_ContinentName + " doesn't exist");
            throw new WarzoneValidationException("Continent with id: " + p_ContinentName + " doesn't exist");
        }
        d_CurrGameSession.getContinentsInSession().remove(p_ContinentName);
        d_CurrGameSession.getContinentsInOrder().remove(p_ContinentName);
        d_LogEntryBuffer.logData("Continent deletion accomplished: " + p_ContinentName);
    }

    /**
     * Creates a new country in a specific continent.
     *
     * @param p_CountryName   Name of the country.
     * @param p_continentName Name of the continent the country belongs to.
     * @throws WarzoneValidationException If the continent doesn't exist or the country already exists.
     */
    public void createCountry(String p_CountryName, String p_continentName) throws WarzoneValidationException {
        Long l_CountryId = getUniqueCountryId();
        createCountry(p_CountryName, p_continentName, l_CountryId);
    }

    /**
     * Creates a new country in a specific continent with a specific ID.
     *
     * @param p_CountryName   Name of the country.
     * @param p_continentName Name of the continent the country belongs to.
     * @param p_CountryId     ID for the new country.
     * @throws WarzoneValidationException If the continent doesn't exist or the country already exists.
     */
    public void createCountry(String p_CountryName, String p_continentName, Long p_CountryId) throws WarzoneValidationException {
        if (!d_CurrGameSession.getContinentsInSession().containsKey(p_continentName)) {
            d_LogEntryBuffer.logData("Continent with id:" + p_continentName + "doesn't exist");
            throw new WarzoneValidationException("Continent with id: " + p_continentName + " doesn't exist");
        }
        if (d_CurrGameSession.getCountriesInSession().containsKey(p_CountryName)) {
            d_LogEntryBuffer.logData("Country with id: " + p_CountryName + " already exists");
            throw new WarzoneValidationException("Country with id: " + p_CountryName + " already exist");
        }
        Country l_CreatedCountry = new Country(p_CountryName, p_continentName, p_CountryId);
        d_CurrGameSession.getCountriesInSession().put(p_CountryName, l_CreatedCountry);
        d_CurrGameSession.getContinentsInSession().get(p_continentName).getCountries().put(p_CountryName, l_CreatedCountry);
        d_LogEntryBuffer.logData("Country creation accomplished: " + p_CountryName + " in continent: " + p_continentName);
        d_CurrGameSession.getCountryIds().put(p_CountryId, p_CountryName);
    }

    /**
     * Deletes a country from the game session.
     *
     * @param p_CountryName Name of the country.
     * @throws WarzoneValidationException If the country doesn't exist.
     */
    public void deleteCountry(String p_CountryName) throws WarzoneValidationException {
        if (!d_CurrGameSession.getCountriesInSession().containsKey(p_CountryName)) {
            d_LogEntryBuffer.logData("Country with id: " + p_CountryName + " doesn't exist");
            throw new WarzoneValidationException("Country with id: " + p_CountryName + " doesn't exist");
        }
        String l_ContinentOfCountry = d_CurrGameSession.getCountriesInSession().get(p_CountryName).getIsInContinent();
        Long l_IdOfCountry = d_CurrGameSession.getCountriesInSession().get(p_CountryName).getId();
        d_CurrGameSession.getCountriesInSession().remove(p_CountryName);
        d_CurrGameSession.getContinentsInSession().get(l_ContinentOfCountry).getCountries().remove(p_CountryName);
        d_CurrGameSession.getCountryIds().remove(l_IdOfCountry);
        d_LogEntryBuffer.logData("Country deletion accomplished for countryId: " + p_CountryName);
    }

    /**
     * Makes two countries neighbors.
     *
     * @param p_CountryName         Name of the first country.
     * @param p_NeighborCountryName Name of the second country.
     * @throws WarzoneValidationException If either country doesn't exist.
     */
    public void makeNeighbors(String p_CountryName, String p_NeighborCountryName) throws WarzoneValidationException {
        Map<String, Country> l_CountriesInSession = d_CurrGameSession.getCountriesInSession();
        if (!l_CountriesInSession.containsKey(p_CountryName)) {
            d_LogEntryBuffer.logData("Country with id:" + p_CountryName + " doesn't exist");
            throw new WarzoneValidationException("Country with id: " + p_CountryName + " doesn't exist");
        }
        if (!l_CountriesInSession.containsKey(p_NeighborCountryName)) {
            throw new WarzoneValidationException("Country with id: " + p_NeighborCountryName + " doesn't exist");
        }

        Country l_Country = l_CountriesInSession.get(p_CountryName);
        Country l_NeighborCountry = l_CountriesInSession.get(p_NeighborCountryName);

        l_Country.addAdjacentCountry(l_NeighborCountry.getId(), p_NeighborCountryName);
        l_NeighborCountry.addAdjacentCountry(l_Country.getId(), p_CountryName);
        d_LogEntryBuffer.logData(String.format("Neighbors created successfully: %s---%s", p_CountryName, p_NeighborCountryName));
    }

    /**
     * Removes the neighbor relationship between two countries.
     *
     * @param p_CountryName         Name of the first country.
     * @param p_NeighborCountryName Name of the second country.
     * @throws WarzoneValidationException If the countries aren't neighbors or don't exist.
     */
    public void removeNeighbors(String p_CountryName, String p_NeighborCountryName) throws WarzoneValidationException {
        Map<String, Country> l_CountriesInSession = d_CurrGameSession.getCountriesInSession();
        if (!l_CountriesInSession.containsKey(p_CountryName)) {
            d_LogEntryBuffer.logData("Country with id: " + p_CountryName + " doesn't exist");
            throw new WarzoneValidationException("Country with id: " + p_CountryName + "doesn't exist");
        }
        if (!l_CountriesInSession.containsKey(p_NeighborCountryName)) {
            d_LogEntryBuffer.logData("Country with id: " + p_NeighborCountryName + " doesn't exist");
            throw new WarzoneValidationException("Country with id: " + p_NeighborCountryName + " doesn't exist");
        }

        //if neighbor country is not in the adjacency list, then these countries are not adjacent, invalid operation
        if (!l_CountriesInSession.get(p_CountryName).getAdjacentCountries().containsValue(p_NeighborCountryName)) {
            d_LogEntryBuffer.logData("Country with id: " + p_CountryName + " and" + " are not neighbors");
            throw new WarzoneValidationException("Country with id: " + p_CountryName + " and " + p_NeighborCountryName + " are not neighbors");
        }
        Country l_Country = l_CountriesInSession.get(p_CountryName);
        Country l_NeighborCountry = l_CountriesInSession.get(p_NeighborCountryName);
        l_Country.getAdjacentCountries().remove(l_NeighborCountry.getId());
        l_NeighborCountry.getAdjacentCountries().remove(l_Country.getId());
        d_LogEntryBuffer.logData(String.format("Neighbors removed successfully: %s, %s", p_CountryName, p_NeighborCountryName));
    }

    /**
     * Creates a new player.
     *
     * @param p_PlayerName Name of the new player.
     * @throws WarzoneValidationException If the player already exists.
     */
    public void createPlayer(String p_PlayerName) throws WarzoneValidationException {
        if (d_CurrGameSession.getPlayers().containsKey(p_PlayerName)) {
            d_LogEntryBuffer.logData("Player with name: " + p_PlayerName + " already exists");
            throw new WarzoneValidationException("Player with given name " + p_PlayerName + "already exists");
        }
        Player l_CreatedPlayer = new Player(p_PlayerName);
        d_Players.put(p_PlayerName, l_CreatedPlayer);
        d_LogEntryBuffer.logData("Player creation accomplished: " + p_PlayerName);
    }

    /**
     * Deletes a player from the game.
     *
     * @param p_PlayerName Name of the player.
     * @throws WarzoneValidationException If the player doesn't exist.
     */
    public void deletePlayer(String p_PlayerName) throws WarzoneValidationException {
        if (!d_CurrGameSession.getPlayers().containsKey(p_PlayerName)) {
            d_LogEntryBuffer.logData("Player with name: " + p_PlayerName + " doesn't exist");
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
        d_LogEntryBuffer.logData("Player deletion accomplished: " + p_PlayerName);
    }

    /**
     * Generates a unique ID for a country.
     *
     * @return Unique country ID.
     */
    private Long getUniqueCountryId() {
        Long l_CountryId = 1L;
        while (d_CurrGameSession.getCountryIds().containsKey(l_CountryId)) {
            l_CountryId++;
        }
        return l_CountryId;
    }

    /**
     * Clears all the data structures, effectively resetting the game session.
     */
    public void clearPreviousSession() {
        d_CurrGameSession.getContinentsInSession().clear();
        d_CurrGameSession.getCountriesInSession().clear();
        d_CurrGameSession.getPlayers().clear();
        d_CurrGameSession.getContinentsInSession().clear();
        d_CurrGameSession.getCountryIds().clear();
    }


    /**
     * Validates the format of the provided continent or country ID.
     *
     * @param p_InputContinentId ID to validate.
     * @return Long representation of the provided ID.
     * @throws WarzoneValidationException If the provided ID format is invalid.
     */
    private int validateContinentCountryIdFormats(String p_InputContinentId) throws WarzoneValidationException {
        try {
            return Integer.parseInt(p_InputContinentId);
        } catch (NumberFormatException e) {
            throw new WarzoneValidationException("Invalid format of id: " + p_InputContinentId);
        }
    }

    /**
     * Method to assign player to given country
     *
     * @param p_PlayerName  Name of the player
     * @param p_CountryName Name of the country
     * @throws WarzoneValidationException If given player already exists or country doesn't exist.
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
        d_LogEntryBuffer.logData("Country: " + p_CountryName + " assigned to player: " + p_PlayerName);
    }

    /**
     * Method to check if country exists with given name
     *
     * @param p_CountryName Name of the country
     * @return True if country exists, false otherwise
     */
    public boolean isCountryExists(String p_CountryName) {
        return d_CurrGameSession.getCountriesInSession().containsKey(p_CountryName);
    }
}