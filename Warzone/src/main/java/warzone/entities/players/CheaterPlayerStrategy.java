package main.java.warzone.entities.players;

import main.java.warzone.entities.Country;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Specifies the implementation of a player using the cheater
 * strategy and is a realization of {@Link PlayerStrategy}
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */
public class CheaterPlayerStrategy implements PlayerStrategy, Serializable {

    /**
     * Method to issue cheater player orders.
     *
     * @param p_Player      Player to issue order.
     * @param p_GameSession GameSession instance
     */
    @Override
    public void issuePlayerOrder(Player p_Player, GameSession p_GameSession) {
        /**
         * A cheater computer player strategy whose issueOrder() method conquers all the immediate neighboring enemy countries,
         * and then doubles the number of armies on its countries that have enemy neighbors. In order to achieve this, the
         * cheater’s strategy implementation will still be called when the issueOrder() method, but will not end up creating orders, but
         * rather implement the above-stated behavior by directly affecting the map during the order creation phase
         */
        List<String> l_PlayerOwnedCountries = new ArrayList<>(p_Player.getOwnedCountries());
        // Conquer all the immediate neighboring enemy countries
        for (String l_CountryName : l_PlayerOwnedCountries) {
            Country l_Country = p_GameSession.getCountriesInSession().get(l_CountryName);
            for (String l_AdjacentCountryName : l_Country.getAdjacentCountries().values()) {
                Country l_AdjacentCountry = p_GameSession.getCountriesInSession().get(l_AdjacentCountryName);
                String l_AdjacentCountryOwner = l_AdjacentCountry.getOwner();
                if (l_AdjacentCountryOwner == null || !l_AdjacentCountryOwner.equals(p_Player.getName())) {
                    l_AdjacentCountry.setOwner(p_Player.getName());
                    l_AdjacentCountry.setNumberOfArmies(1);
                    p_Player.addOwnedCountry(l_AdjacentCountryName);
                    if (l_AdjacentCountryOwner != null) {
                        Player l_AdjacentCountryOwnerPlayer = p_GameSession.getPlayers().get(l_AdjacentCountryOwner);
                        l_AdjacentCountryOwnerPlayer.removeOwnedCountry(l_AdjacentCountryName);
                    }
                }
            }
        }

        // Double the number of armies on its countries that have enemy neighbors
        for (String l_CountryName : p_Player.getOwnedCountries()) {
            Country l_Country = p_GameSession.getCountriesInSession().get(l_CountryName);
            for (String l_AdjacentCountryName : l_Country.getAdjacentCountries().values()) {
                Country l_AdjacentCountry = p_GameSession.getCountriesInSession().get(l_AdjacentCountryName);
                String l_AdjacentCountryOwner = l_AdjacentCountry.getOwner();
                if (l_AdjacentCountryOwner != null && !l_AdjacentCountry.getOwner().equals(p_Player.getName())) {
                    l_Country.setNumberOfArmies(l_Country.getNumberOfArmies() * 2);
                    break;
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
        return "cheater";
    }
}
