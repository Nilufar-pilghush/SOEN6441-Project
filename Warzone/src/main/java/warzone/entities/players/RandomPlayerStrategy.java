package main.java.warzone.entities.players;

import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.Country;
import main.java.warzone.entities.Player;
import main.java.constants.WarzoneConstants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
public class RandomPlayerStrategy implements PlayerStrategy, Serializable {

    @Override
    public void issuePlayerOrder(Player p_Player, GameSession p_GameSession) {}


    @Override
    public String getStrategyNameString() {
        return WarzoneConstants.RANDOM;
    }

    private Country getRandomOwnedCountry(Player p_Player, GameSession p_GameSession) {
        List<Country> ownedCountries = new ArrayList<>();
        for (String l_CountryName : p_Player.getOwnedCountries()) {
            ownedCountries.add(p_GameSession.getCountriesOfSession().get(l_CountryName));
        }
        return ownedCountries.get((int) (Math.random() * ownedCountries.size()));
    }

}