package main.java.warzone.utils;

import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.Continent;
import main.java.warzone.entities.Country;
import main.java.warzone.entities.*;

import java.util.*;

/**
 * validates the created main.java.game Session.
 * Validations include: All the countries and continents are reachable from a given point.
 * All the continents are connected.
 * every continent has at least one country.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */
public class GameSessionValidator {

    /**
     * Method to validate a given main.java.game world.
     *
     * @param p_GameSession Entire main.java.game world which has all the continents and their countries with adjacency
     * @return true if given main.java.game world map is valid, false otherwise.
     */
    public static boolean validateMap(GameSession p_GameSession) {
        System.out.println("Please wait while we are validating your main.java.game world....");
        if (isWorldEmpty(p_GameSession)) {
            return false;
        }
        if (!doesContinentHaveAtLeastMinimumCountries(p_GameSession)) {
            return false;
        }
        if (!areAllCountriesConnectedInEveryContinent(p_GameSession)) {
            return false;
        }
        if (!areContinentsConnected(p_GameSession)) {
            System.out.println("All the continents are not connected");
            return false;
        }
        System.out.println("Game Session validated successfully.");
        return true;
    }

    /**
     * Validates if the given main.java.game Session is empty (no continents).
     *
     * @param p_GameSession main.java.game Session to be validated
     * @return true if the given Session is empty, false otherwise
     */
    public static boolean isWorldEmpty(GameSession p_GameSession) {
        if (Objects.isNull(p_GameSession) || p_GameSession.getContinentsInSession().isEmpty()) {
            System.out.println("Game Session is empty.");
            return true;
        }
        return false;
    }

    /**
     * Method to validate if players are added to main.java.game world
     *
     * @param p_GameSession Current main.java.game Session instance
     * @return True if players are added to the main.java.game Session, false otherwise
     */
    public static boolean arePlayersAdded(GameSession p_GameSession) {
        if (Objects.isNull(p_GameSession) || p_GameSession.getPlayers().isEmpty()) {
            System.out.println("No players added to the main.java.game");
            return false;
        }
        return true;
    }

    /**
     * Validates if given main.java.game world minimum number countries in every continent
     *
     * @param p_GameSession current main.java.game world which has all the countries and continents along with their neighbors
     * @return true if given main.java.game world continents have at least minimum countries present, false otherwise
     */
    private static boolean doesContinentHaveAtLeastMinimumCountries(GameSession p_GameSession) {
        boolean isValid = true;
        for (Continent l_Continent : p_GameSession.getContinentsInSession().values()) {
            System.out.println("Validating minimum countries in continent--->" + l_Continent.getName());
            if (l_Continent.getCountries().keySet().isEmpty()) {
                System.out.println("No Country found in continent--->" + l_Continent.getName());
                isValid = false;
            }
        }
        return isValid;
    }

    /**
     * Validates if all the countries of every continent are connected
     *
     * @param p_GameSession current main.java.game world which has all the countries and continents along with their neighbors
     * @return true if all the countries of every continent are connected, false otherwise
     */
    private static boolean areAllCountriesConnectedInEveryContinent(GameSession p_GameSession) {
        for (Continent l_Continent : p_GameSession.getContinentsInSession().values()) {
            System.out.println("Validating connectedness of countries in continent--->" + l_Continent.getName());
            Set<String> l_VisitedCountries = new HashSet<>();
            Country l_StartCountry = new ArrayList<>(l_Continent.getCountries().values()).get(0);
            depthFirstTraversal(l_StartCountry, p_GameSession, l_VisitedCountries, l_Continent);
            System.out.println("Visited countries in continent--->" + l_Continent.getName() + " are " + l_VisitedCountries);
            if (l_VisitedCountries.containsAll(l_Continent.getCountries().keySet())) {
                continue;
            } else {
                System.out.println("All countries of continent " + l_Continent.getName() + " are not connected");
                return false;
            }
        }
        return true;
    }

    /**
     * Helper method to perform depth-first traversal to validate if all countries are connected in a given continent.
     *
     * @param p_StartCountry        Country under observation for dfs
     * @param p_GameSession           Entire main.java.game world which has all the continents and their countries with adjacency
     * @param p_VisitedCountries    Countries visited in the current dfs
     * @param p_ValidatingContinent Continent getting validated
     */
    private static void depthFirstTraversal(Country p_StartCountry, GameSession p_GameSession, Set<String> p_VisitedCountries, Continent p_ValidatingContinent) {
        p_VisitedCountries.add(p_StartCountry.getName());
        for (String l_NeighbourCountryName : p_StartCountry.getAdjacentCountries().values()) {
            Country l_NeighborCountry = p_GameSession.getCountriesInSession().get(l_NeighbourCountryName);
            if (!p_VisitedCountries.contains(l_NeighbourCountryName) && l_NeighborCountry.getIsInContinent().equals(p_ValidatingContinent.getName())) {
                depthFirstTraversal(l_NeighborCountry, p_GameSession, p_VisitedCountries, p_ValidatingContinent);
            }
        }

    }

    /**
     * Method to validate if all the continents of the world are connected or not
     *
     * @param p_GameSession Entire main.java.game world which has all the continents and their countries with adjacency
     * @return true if all the continents of the world are connected, false otherwise
     */
    private static boolean areContinentsConnected(GameSession p_GameSession) {
        List<Continent> l_Continents = new ArrayList<>(p_GameSession.getContinentsInSession().values());
        Set<String> l_VisitedContinents = new HashSet<>();
        Set<String> l_VisitedCountries = new HashSet<>();

        Continent l_StartContinent = l_Continents.get(0);
        Country l_StartCountry = new ArrayList<>(l_StartContinent.getCountries().values()).get(0);
        depthFirstTraversal(l_StartCountry, p_GameSession, l_VisitedContinents, l_VisitedCountries);
        if (l_VisitedContinents.containsAll(p_GameSession.getContinentsInSession().keySet())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Helper method to perform depth-first traversal to validate if all continents are connected in the world.
     *
     * @param p_StartCountry      Country under observation for dfs
     * @param p_GameSession         Entire main.java.game world which has all the continents and their countries with adjacency
     * @param p_VisitedContinents Continents visited in the current dfs
     * @param p_VisitedCountries  Countries visited in the current dfs
     */
    private static void depthFirstTraversal(Country p_StartCountry, GameSession p_GameSession, Set<String> p_VisitedContinents, Set<String> p_VisitedCountries) {
        p_VisitedCountries.add(p_StartCountry.getName());
        p_VisitedContinents.add(p_StartCountry.getIsInContinent());
        for (String l_NeighborCountryName : p_StartCountry.getAdjacentCountries().values()) {
            if (!p_VisitedCountries.contains(l_NeighborCountryName)) {
                Country l_NeighborCountry = p_GameSession.getCountriesInSession().get(l_NeighborCountryName);
                depthFirstTraversal(l_NeighborCountry, p_GameSession, p_VisitedContinents, p_VisitedCountries);
            }
        }
    }
}
