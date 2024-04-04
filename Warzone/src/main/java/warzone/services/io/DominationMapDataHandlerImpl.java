package main.java.warzone.services.io;

import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.Continent;
import main.java.warzone.entities.Country;
import main.java.warzone.entities.GameSession;
import main.java.warzone.exceptions.WarzoneRuntimeException;
import main.java.warzone.exceptions.WarzoneValidationException;
import main.java.warzone.services.GameMapDataHandler;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Concrete class implementing the behaviors necessary to manage
 * game session creation/saving from/to user specified files.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */
public class DominationMapDataHandlerImpl implements GameMapDataHandler {

    /**
     * Current game session instance
     */
    private final GameSession d_CurrGameSession;

    /**
     * LogEntryBuffer object to log the information
     * and notifying all the observers
     */
    private LogEntryBuffer d_LogEntryBuffer;

    /**
     * Reader to load game from file
     */
    private BufferedReader d_GameSessionReader;

    /**
     * Constructor to initialize GameMapDataManager instance
     */
    public DominationMapDataHandlerImpl() {
        d_CurrGameSession = GameSession.getInstance();
        d_LogEntryBuffer = LogEntryBuffer.getInstance();
        d_GameSessionReader = null;
    }

    @Override
    public void makeGameSession(InputStream p_InputStream) throws WarzoneValidationException, WarzoneRuntimeException {
        if (p_InputStream == null) {
            throw new WarzoneRuntimeException("Unable to find map!");
        }
        InputStreamReader l_InputSteamReader = new InputStreamReader(p_InputStream, StandardCharsets.UTF_8);
        d_CurrGameSession.clearPreviousSession();
        d_GameSessionReader = new BufferedReader(l_InputSteamReader);
        String l_GameSessiondData = null;
        try {
            while ((l_GameSessiondData = d_GameSessionReader.readLine()) != null) {
                if (l_GameSessiondData.contains(WarzoneConstants.CONTINENTS)) {
                    continentsReadingHandler(d_GameSessionReader);
                } else if (l_GameSessiondData.contains(WarzoneConstants.COUNTRIES)) {
                    countriesReadingHandler(d_GameSessionReader);
                } else if (l_GameSessiondData.contains(WarzoneConstants.BORDERS)) {
                    bordersReadingHandler(d_GameSessionReader);
                }
            }
        } catch (IOException e) {
            throw new WarzoneRuntimeException("Failed to read map data from file");
        }
        d_LogEntryBuffer.logData("Map successfully loaded....");
    }

    @Override
    public void saveGameSession(String p_GameSessionFileName) {
        StringBuilder l_Content = new StringBuilder();
        l_Content.append(WarzoneConstants.CONTINENTS);
        l_Content.append(WarzoneConstants.NEW_LINE);
        Map<String, Continent> l_ContinentsOfSession = d_CurrGameSession.getContinentsInSession();
        List<String> l_ContinentsOrder = d_CurrGameSession.getContinentsInOrder();
        //store continents of session with continentName controlValue
        for (String l_ContinentName : l_ContinentsOrder) {
            l_Content.append(l_ContinentName).append(WarzoneConstants.SPACE).append(l_ContinentsOfSession.get(l_ContinentName).getControlValue());
            l_Content.append(WarzoneConstants.NEW_LINE);
        }
        l_Content.append(WarzoneConstants.NEW_LINE);
        l_Content.append(WarzoneConstants.COUNTRIES);
        l_Content.append(WarzoneConstants.NEW_LINE);
        //store countries in [countryId countryName continentIndex] order
        for (String l_CountryName : d_CurrGameSession.getCountriesInSession().keySet()) {
            Country l_Country = d_CurrGameSession.getCountriesInSession().get(l_CountryName);
            int l_IndexOfContinent = l_ContinentsOrder.indexOf(l_Country.getIsInContinent()) + 1;
            l_Content.append(l_Country.getId()).append(WarzoneConstants.SPACE).append(l_CountryName).append(WarzoneConstants.SPACE).append(l_IndexOfContinent);
            l_Content.append(WarzoneConstants.NEW_LINE);
        }
        l_Content.append(WarzoneConstants.NEW_LINE);
        l_Content.append(WarzoneConstants.BORDERS);
        l_Content.append(WarzoneConstants.NEW_LINE);
        for (String l_CountryName : d_CurrGameSession.getCountriesInSession().keySet()) {
            Country l_Country = d_CurrGameSession.getCountriesInSession().get(l_CountryName);
            l_Content.append(l_Country.getId());
            for (Long l_Neighbor : l_Country.getAdjacentCountries().keySet()) {
                l_Content.append(WarzoneConstants.SPACE).append(l_Neighbor);
            }
            l_Content.append(WarzoneConstants.NEW_LINE);
        }

        saveContentToFile(p_GameSessionFileName, l_Content);
    }

    /**
     * Method to save content to file
     *
     * @param p_GameSessionFileName Name of the file
     * @param p_Content Content to write
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
     * Method to read continents
     *
     * @param p_GameSessionFileName reader instance
     * @throws IOException                If unable to read data from file
     * @throws WarzoneValidationException if format is not valid
     */
    private void continentsReadingHandler(BufferedReader p_GameSessionFileName) throws IOException, WarzoneValidationException {
        String l_GameSessionData = null;
        d_LogEntryBuffer.logData("Reading continents....");
        while ((l_GameSessionData = p_GameSessionFileName.readLine()) != null) {
            l_GameSessionData = l_GameSessionData.trim();
            if (l_GameSessionData.isEmpty() || l_GameSessionData.startsWith(WarzoneConstants.OPEN_SQUARE_BRACKET)) {
                d_LogEntryBuffer.logData("Continents reading completed...");
                return;
            }
            String[] l_ContinentData = l_GameSessionData.split(WarzoneConstants.SPACE_REGEX);
            if (l_ContinentData.length < 2) {
                throw new WarzoneValidationException("Invalid continentId continentValue format");
            }
            if (!d_CurrGameSession.getContinentsInSession().containsKey(l_ContinentData[0])) {
                d_CurrGameSession.createContinent(l_ContinentData[0], l_ContinentData[1]);
            }
        }
    }

    /**
     * Method to read countries
     *
     * @param p_GameSessionReader Reader instance
     * @throws WarzoneValidationException If format is not valid
     * @throws IOException                if unable to read data from file
     */
    private void countriesReadingHandler(BufferedReader p_GameSessionReader) throws WarzoneValidationException, IOException {
        String l_GameSessionData = null;
        d_LogEntryBuffer.logData("Reading countries....");
        while ((l_GameSessionData = p_GameSessionReader.readLine()) != null) {
            l_GameSessionData = l_GameSessionData.trim();
            if (l_GameSessionData.isEmpty() || l_GameSessionData.startsWith(WarzoneConstants.OPEN_SQUARE_BRACKET)) {
                d_LogEntryBuffer.logData("Countries reading completed...");
                return;
            }
            String[] l_CountryData = l_GameSessionData.split(WarzoneConstants.SPACE_REGEX);
            if (l_CountryData.length < 3) {
                throw new WarzoneValidationException("Invalid countryId continentId format");
            }
            if (!l_CountryData[0].isEmpty()) {
                String l_ContinentOfCountry = d_CurrGameSession.getContinentsInOrder().get(Integer.parseInt(l_CountryData[2]) - 1);
                d_CurrGameSession.createCountry(l_CountryData[1], l_ContinentOfCountry, Long.parseLong(l_CountryData[0]));
            }
        }
    }

    /**
     * Method to read borders
     *
     * @param p_GameSessionReader Reader instance
     * @throws IOException                If unable to read data from reader
     * @throws WarzoneValidationException if format is not valid
     */
    private void bordersReadingHandler(BufferedReader p_GameSessionReader) throws IOException, WarzoneValidationException {
        String l_GameSessionData = null;
        d_LogEntryBuffer.logData("Reading borders....");
        while ((l_GameSessionData = p_GameSessionReader.readLine()) != null) {
            l_GameSessionData = l_GameSessionData.trim();
            if (l_GameSessionData.isEmpty() || l_GameSessionData.startsWith(WarzoneConstants.OPEN_SQUARE_BRACKET)) {
                d_LogEntryBuffer.logData("Borders creation completed...");
                return;
            }
            String[] l_BordersData = l_GameSessionData.split(WarzoneConstants.SPACE_REGEX);
            String l_CountryName = d_CurrGameSession.getCountryIds().get(Long.parseLong(l_BordersData[0]));
            if (l_CountryName != null) {
                for (int neighbor = 1; neighbor < l_BordersData.length; neighbor++) {
                    String l_NeighborCountryName = d_CurrGameSession.getCountryIds().get(Long.parseLong(l_BordersData[neighbor]));
                    d_CurrGameSession.makeNeighbors(l_CountryName, l_NeighborCountryName);
                }
            }
        }
    }
}
