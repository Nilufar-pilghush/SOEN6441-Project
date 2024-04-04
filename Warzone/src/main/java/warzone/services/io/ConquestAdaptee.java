package main.java.warzone.services.io;

import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.Continent;
import main.java.warzone.entities.Country;
import main.java.warzone.entities.GameSession;
import main.java.warzone.exceptions.WarzoneRuntimeException;
import main.java.warzone.exceptions.WarzoneValidationException;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Adaptee providing the implementation to read/write data
 * as per conquest map format.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */
public class ConquestAdaptee {

    /**
     * Current game session instance
     */
    private final GameSession d_CurrGameSession;


    /**
     * Reader to load game from file
     */
    private BufferedReader d_GameSessionReader;

    /**
     * LogEntryBuffer object to log the information
     * and notifying all the observers
     */
    private LogEntryBuffer d_LogEntryBuffer;

    /**
     * Constructor to initialize adaptee
     */
    public ConquestAdaptee() {
        d_CurrGameSession = GameSession.getInstance();
        d_LogEntryBuffer = LogEntryBuffer.getInstance();
    }

    /**
     * Method to save game session as per conquest format
     *
     * @param p_GameSessionFileName Name of the file
     */
    public void saveGameSession(String p_GameSessionFileName) {
        StringBuilder l_Content = new StringBuilder();
        l_Content.append(WarzoneConstants.MAP);
        l_Content.append(WarzoneConstants.NEW_LINE);
        l_Content.append(WarzoneConstants.AUTHOR).append(WarzoneConstants.EQUALS).append(WarzoneConstants.U3);
        l_Content.append(WarzoneConstants.NEW_LINE);
        l_Content.append(WarzoneConstants.NEW_LINE);
        l_Content.append(WarzoneConstants.CONTINENTS);
        l_Content.append(WarzoneConstants.NEW_LINE);
        Map<String, Continent> l_ContinentsOfSession = d_CurrGameSession.getContinentsInSession();
        for (String l_ContinentName : l_ContinentsOfSession.keySet()) {
            l_Content.append(l_ContinentName).append(WarzoneConstants.EQUALS).append(l_ContinentsOfSession.get(l_ContinentName).getControlValue());
            l_Content.append(WarzoneConstants.NEW_LINE);
        }
        l_Content.append(WarzoneConstants.NEW_LINE);
        l_Content.append(WarzoneConstants.TERRITORIES);
        l_Content.append(WarzoneConstants.NEW_LINE);
        Map<String, Country> l_CountriesOfSession = d_CurrGameSession.getCountriesInSession();
        for (String l_CountryName : l_CountriesOfSession.keySet()) {
            Country l_Country = l_CountriesOfSession.get(l_CountryName);
            l_Content.append(l_CountryName).append(WarzoneConstants.COMMA).append(l_Country.getIsInContinent());
            List<String> l_Neighbours = new ArrayList<>(l_Country.getAdjacentCountries().values());
            if (!l_Neighbours.isEmpty()) {
                l_Content.append(WarzoneConstants.COMMA);
            }
            int size = l_Neighbours.size();
            for (int index = 0; index < size; index++) {
                l_Content.append(l_Neighbours.get(index));
                if (index != size - 1) {
                    l_Content.append(WarzoneConstants.COMMA);
                }
            }
            l_Content.append(WarzoneConstants.NEW_LINE);
        }
        saveContentToFile(p_GameSessionFileName, l_Content);
    }

    /**
     * Method to save content to file
     *
     * @param p_GameSessionFileName Name of the file
     * @param p_Content Content of file
     */
    private void saveContentToFile(String p_GameSessionFileName, StringBuilder p_Content) {
        try (PrintWriter l_Out = new PrintWriter(WarzoneConstants.GAME_SESSIONS + WarzoneConstants.FORWARD_SLASH + p_GameSessionFileName)) {
            l_Out.println(p_Content);
            d_LogEntryBuffer.logData("Map successfully stored in file: " + p_GameSessionFileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to make game session
     *
     * @param p_InputStream Stream of input
     * @throws WarzoneRuntimeException If map is not found
     * @throws WarzoneValidationException If map is not valid
     */
    public void makeGameSession(InputStream p_InputStream) throws WarzoneRuntimeException, WarzoneValidationException {
        if (p_InputStream == null) {
            throw new WarzoneRuntimeException("Unable to find map!");
        }
        InputStreamReader l_InputSteamReader = new InputStreamReader(p_InputStream, StandardCharsets.UTF_8);
        d_CurrGameSession.clearPreviousSession();
        d_GameSessionReader = new BufferedReader(l_InputSteamReader);
        String l_GameSessionData = null;
        try {
            while ((l_GameSessionData = d_GameSessionReader.readLine()) != null) {
                if (l_GameSessionData.contains(WarzoneConstants.CONTINENTS)) {
                    continentsReadingHandler(d_GameSessionReader);
                } else if (l_GameSessionData.contains(WarzoneConstants.TERRITORIES)) {
                    countriesNeighborsReadingHandler(d_GameSessionReader);
                }
            }
        } catch (IOException e) {
            throw new WarzoneRuntimeException("Failed to read map data from file");
        }
        d_LogEntryBuffer.logData("Map successfully loaded....");

    }

    /**
     * Method to read continents
     *
     * @param p_GameSessionReader Reader from the file
     * @throws IOException If reading fails
     * @throws WarzoneValidationException If format is not valid
     */
    private void continentsReadingHandler(BufferedReader p_GameSessionReader) throws IOException, WarzoneValidationException {
        String l_GameSessionData = null;
        d_LogEntryBuffer.logData("Reading continents....");
        while ((l_GameSessionData = p_GameSessionReader.readLine()) != null) {
            l_GameSessionData = l_GameSessionData.trim();
            if (l_GameSessionData.isEmpty() || l_GameSessionData.startsWith(WarzoneConstants.OPEN_SQUARE_BRACKET)) {
                d_LogEntryBuffer.logData("Continents reading completed...");
                return;
            }
            String[] l_ContinentData = l_GameSessionData.split(WarzoneConstants.EQUALS);
            if (l_ContinentData.length < 2) {
                throw new WarzoneValidationException("Invalid continentId continentValue format");
            }
            if (!d_CurrGameSession.getContinentsInSession().containsKey(l_ContinentData[0])) {
                d_CurrGameSession.createContinent(l_ContinentData[0], l_ContinentData[1]);
            }
        }
    }

    /**
     * Method to read neighbors from the map
     *
     * @param p_GameSessionReader Reader from the file
     * @throws IOException If file is not read
     * @throws WarzoneValidationException If format is not valid
     */
    private void countriesNeighborsReadingHandler(BufferedReader p_GameSessionReader) throws IOException, WarzoneValidationException {
        String l_GameSessionData = null;
        d_LogEntryBuffer.logData("Reading countries and neighbors....");
        while ((l_GameSessionData = p_GameSessionReader.readLine()) != null) {
            if (l_GameSessionData.isEmpty() || l_GameSessionData.startsWith(WarzoneConstants.OPEN_SQUARE_BRACKET)) {
                d_LogEntryBuffer.logData("Countries and neighbors reading completed...");
                return;
            }
            String[] l_CountriesNbrs = l_GameSessionData.split(WarzoneConstants.COMMA);
            if (l_CountriesNbrs.length < 3) {
                throw new WarzoneValidationException("Countries, continent, neighbors not in desired format.");
            }
            String l_ContinentName = l_CountriesNbrs[1];
            if (!d_CurrGameSession.getCountriesInSession().containsKey(l_CountriesNbrs[0])) {
                if (!d_CurrGameSession.getContinentsInSession().containsKey(l_ContinentName)) {
                    throw new WarzoneValidationException("Continent " + l_ContinentName + " not found in created continents");
                }
                d_CurrGameSession.createCountry(l_CountriesNbrs[0], l_ContinentName);
            }

            for(int index = 2;index<l_CountriesNbrs.length;index++) {
                String l_CountryForNbr = l_CountriesNbrs[index];
                if (!d_CurrGameSession.getCountriesInSession().containsKey(l_CountryForNbr)) {
                    d_CurrGameSession.createCountry(l_CountryForNbr, l_ContinentName);
                }
                d_CurrGameSession.makeNeighbors(l_CountriesNbrs[0], l_CountryForNbr);
            }
        }
    }
}
