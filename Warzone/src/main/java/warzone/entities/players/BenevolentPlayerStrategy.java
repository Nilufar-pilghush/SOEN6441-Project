package main.java.warzone.entities.players;

import main.java.warzone.entities.Country;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Specifies the implementation of a benevolent player
 * strategy and is a realization of {@link PlayerStrategy}
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */
public class BenevolentPlayerStrategy implements PlayerStrategy, Serializable {

    /**
     * Method to issue benevolent player order.
     *
     * @param p_Player      Player to issue order.
     * @param p_GameSession GameSession instance
     */
    @Override
    public void issuePlayerOrder(Player p_Player, GameSession p_GameSession) {
        /**
         * A benevolent strategy  focuses on protecting its weak countries by deploying on its weakest country, never
         * attacking, then moves its armies in order to reinforce its weaker country.
         */
        List<Country> ownedCountriesWeakestToStrongest = getOwnedCountriesFromWeakestToStrongest(p_Player, p_GameSession);
        Country l_WeakestCountry = ownedCountriesWeakestToStrongest.get(0);
        int l_NumberOfArmiesToDeploy = p_Player.getNumberOfArmies();
        // Deploy on weakest country
        p_Player.addDeployOrder(
                l_WeakestCountry.getName(),
                l_NumberOfArmiesToDeploy
        );
        // Move armies to weakest country
        for (Country l_Country : ownedCountriesWeakestToStrongest) {
            if (l_Country.getNumberOfArmies() > 1) {
                List<String> l_AdjacentCountries = l_Country.getAdjacentCountries().values().stream().toList();
                for (String l_AdjacentCountryName : l_AdjacentCountries) {
                    Country l_AdjacentCountry = p_GameSession.getCountriesInSession().get(l_AdjacentCountryName);
                    String l_AdjacentCountryOwner = l_AdjacentCountry.getOwner();
                    if (l_AdjacentCountryOwner != null && l_AdjacentCountryOwner.equals(p_Player.getName())) {
                        if (l_AdjacentCountry.getNumberOfArmies() > l_Country.getNumberOfArmies()) {
                            p_Player.addAttackOrder(
                                    l_AdjacentCountryName,
                                    l_Country.getName(),
                                    l_AdjacentCountry.getNumberOfArmies() - l_Country.getNumberOfArmies()
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
        return "benevolent";
    }

    private List<Country> getOwnedCountriesFromWeakestToStrongest(Player p_Player, GameSession p_GameSession) {
        List<Country> ownedCountries = new ArrayList<>();
        for (String l_CountryName : p_Player.getOwnedCountries()) {
            ownedCountries.add(p_GameSession.getCountriesInSession().get(l_CountryName));
        }
        ownedCountries.sort(Comparator.comparingInt(Country::getNumberOfArmies));
        return ownedCountries;
    }
}
