package main.java.warzone.entities.players;

import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.Country;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Specifies the implementation of an aggressive player
 * strategy and is a realization of {@Link PlayerStrategy}
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */
public class AggressivePlayerStrategy implements PlayerStrategy, Serializable {

    /**
     * Method to issue aggressive player orders.
     *
     * @param p_Player      Player to issue order.
     * @param p_GameSession GameSession instance
     */
    @Override
    public void issuePlayerOrder(Player p_Player, GameSession p_GameSession) {
        /*
         * A strategy for an aggressive computer player that prioritizes centralizing forces before attacking.
         * It deploys reinforcements to its strongest country, launches attacks from this point of strength,
         * and rearranges its armies to consolidate power in a single location.
         */
        List<Country> ownedCountriesStrongestToWeakest = getOwnedCountriesFromStrongestToWeakest(p_Player, p_GameSession);
        if (!ownedCountriesStrongestToWeakest.isEmpty()) {
            Country l_StrongestCountry = ownedCountriesStrongestToWeakest.get(0);
            int l_NumberOfArmiesToDeploy = p_Player.getNumberOfArmies();
            // Deploy on strongest country
            p_Player.addDeployOrder(
                    l_StrongestCountry.getName(),
                    l_NumberOfArmiesToDeploy
            );
        }
        // Attack with strongest country
        for (Country l_Country : ownedCountriesStrongestToWeakest) {
            if (l_Country.getNumberOfArmies() > 1) {
                List<String> l_AdjacentCountries = l_Country.getAdjacentCountries().values().stream().toList();
                for (String l_AdjacentCountryName : l_AdjacentCountries) {
                    Country l_AdjacentCountry = p_GameSession.getCountriesInSession().get(l_AdjacentCountryName);
                    String l_AdjacentCountryOwner = l_AdjacentCountry.getOwner();
                    if (l_AdjacentCountryOwner == null || !l_AdjacentCountry.getOwner().equals(p_Player.getName()) && l_Country.getNumberOfArmies() > (l_AdjacentCountry.getNumberOfArmies()*2)) {
                        p_Player.addAttackOrder(
                                l_Country.getName(),
                                l_AdjacentCountryName,
                                l_Country.getNumberOfArmies() - 1
                        );
                        break;
                    }
                }

            }
        }
        // Move armies to strongest country
        List<Country> ownedCountriesWeakestToStrongest = new ArrayList<>(ownedCountriesStrongestToWeakest);
        Collections.reverse(ownedCountriesWeakestToStrongest);
        for (Country l_Country : ownedCountriesWeakestToStrongest) {
            if (l_Country.getNumberOfArmies() > 1) {
                List<String> l_AdjacentCountries = l_Country.getAdjacentCountries().values().stream().toList();
                for (String l_AdjacentCountryName : l_AdjacentCountries) {
                    Country l_AdjacentCountry = p_GameSession.getCountriesInSession().get(l_AdjacentCountryName);
                    String l_AdjacentCountryOwner = l_AdjacentCountry.getOwner();
                    if (l_AdjacentCountryOwner != null && l_AdjacentCountryOwner.equals(p_Player.getName())) {
                        if (l_AdjacentCountry.getNumberOfArmies() > l_Country.getNumberOfArmies()) {
                            p_Player.addAttackOrder(
                                    l_Country.getName(),
                                    l_AdjacentCountryName,
                                    l_Country.getNumberOfArmies() - 1
                            );
                        }
                    }
                }
            }
        }
    }

    /**
     * Method to return strategy name.
     *
     * @return String strategy name.
     */
    @Override
    public String getStrategyNameString() {
        return "aggressive";
    }

    /**
     * Method to get list of owned countries sorted by strongest to weakest.
     *
     * @param p_Player    Current player
     * @param p_GameSession Current game session
     * @return List of countries
     */
    public List<Country> getOwnedCountriesFromStrongestToWeakest(Player p_Player, GameSession p_GameSession) {
        List<Country> ownedCountries = new ArrayList<>();
        for (String l_CountryName : p_Player.getOwnedCountries()) {
            ownedCountries.add(p_GameSession.getCountriesInSession().get(l_CountryName));
        }
        ownedCountries.sort(Comparator.comparingInt(Country::getNumberOfArmies).reversed());
        return ownedCountries;
    }
}
