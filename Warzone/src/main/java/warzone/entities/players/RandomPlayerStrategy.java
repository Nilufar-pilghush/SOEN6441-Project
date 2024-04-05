package main.java.warzone.entities.players;

import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.Country;
import main.java.warzone.entities.Player;
import main.java.warzone.constants.WarzoneConstants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * A realization of {@link PlayerStrategy}
 * specifying the implementation of random player strategy.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */
public class RandomPlayerStrategy implements PlayerStrategy, Serializable {

    /**
     * Method to issue random player orders.
     *
     * @param p_Player    Player to issue order.
     * @param p_GameSession GameSession instance
     */
    @Override
    public void issuePlayerOrder(Player p_Player, GameSession p_GameSession) {
        Country l_RandomCountry = getRandomOwnedCountry(p_Player, p_GameSession);
        int l_NumberOfArmiesToDeploy = p_Player.getNumberOfArmies();

        p_Player.addDeployOrder(
                l_RandomCountry.getName(),
                l_NumberOfArmiesToDeploy
        );

        List<String> l_AdjacentCountries = l_RandomCountry.getAdjacentCountries().values().stream().toList();
        for (String l_AdjacentCountryName : l_AdjacentCountries) {
            Country l_AdjacentCountry = p_GameSession.getCountriesInSession().get(l_AdjacentCountryName);
            String l_AdjacentCountryOwner = l_AdjacentCountry.getOwner();
            if (l_AdjacentCountryOwner != null && !l_AdjacentCountry.getOwner().equals(p_Player.getName())) {
                p_Player.addAttackOrder(
                        l_RandomCountry.getName(),
                        l_AdjacentCountryName,
                        l_RandomCountry.getNumberOfArmies() - 1
                );
                break;
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
        return WarzoneConstants.RANDOM;
    }

    /**
     * Method to get random owned country.
     *
     * @param p_Player Current player
     * @param p_GameSession Current game session
     * @return random owned country.
     */
    private Country getRandomOwnedCountry(Player p_Player, GameSession p_GameSession) {
        List<Country> ownedCountries = new ArrayList<>();
        for (String l_CountryName : p_Player.getOwnedCountries()) {
            ownedCountries.add(p_GameSession.getCountriesInSession().get(l_CountryName));
        }
        return ownedCountries.get((int) (Math.random() * ownedCountries.size()));
    }

}