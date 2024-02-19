package main.java.warzone.services.io;

import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.Continent;
import main.java.warzone.entities.Country;
import main.java.warzone.entities.GameSession;
import main.java.warzone.exceptions.WarzoneRuntimeException;
import main.java.warzone.exceptions.WarzoneValidationException;
import main.java.warzone.services.GameMapDataHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Implements the {@link GameMapDataHandler} interface.
 * Handles the creation and saving of main.java.game maps, including continents, countries, and borders.
 * Reads map data from an input stream and writes formatted map data to an output stream.
 * Supports operations such as creating continents, countries, and borders, and saving the modified map data to a new file.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */
public class GameMapDataHandlerImpl implements GameMapDataHandler {

    /**
     * Instance of the current main.java.game session
     */
    final private GameSession d_CurrGameMap;

    /**
     * BufferedReader to read the map file.
     */
    private BufferedReader d_MapReader;

    /**
     * Constructs a GameMapDataHandlerImpl object.
     * Initializes the current main.java.game session and map reader.
     */
    public GameMapDataHandlerImpl() {
        d_CurrGameMap = GameSession.getInstance();
        d_MapReader = null;
    }

    /**
     * Reads the map data from the input stream and creates the game map.
     * Parses the input stream to extract continent, country, and border data.
     *
     * @param p_InputStream The input stream containing map data.
     * @throws WarzoneRuntimeException If the input stream is null or if there's an error reading the data.
     * @throws WarzoneValidationException If the map data is invalid.
     */
    @Override
    public void createGameMap(InputStream p_InputStream) throws WarzoneRuntimeException, WarzoneValidationException{
        if (p_InputStream == null) {
            throw new WarzoneRuntimeException("Unable to find map file.");
        }
        d_MapReader = new BufferedReader(new InputStreamReader(p_InputStream, StandardCharsets.UTF_8));
        d_CurrGameMap.clearPreviousSession();
        String l_CurrMapDataLine = null;
        try {
            while ((l_CurrMapDataLine = d_MapReader.readLine()) != null) {
                l_CurrMapDataLine = l_CurrMapDataLine.trim();
                if (l_CurrMapDataLine.contains("[continents]")) {
                    readContinents(d_MapReader);
                }
                else if (l_CurrMapDataLine.contains("[countries]")) {
                    readCountries(d_MapReader);
                }
                else if (l_CurrMapDataLine.contains("[borders]")) {
                   readBorders(d_MapReader);
                }
            }
        } catch (IOException e) {
            throw new WarzoneRuntimeException("Cannot read data from file");
        }
        System.out.println("Map loaded successfully");
    }

    /**
     * Writes the current main.java.game map data to a new file.
     * Formats the map data including continents, countries, and borders.
     * Writes the formatted data to the specified output stream.
     *
     * @param p_GameMapFileName The name of the new file to save the map data.
     */
    @Override
    public void saveGameMap(String p_GameMapFileName) {
        StringBuilder l_Content = new StringBuilder();
        l_Content.append(WarzoneConstants.CONTINENTS);
        l_Content.append(WarzoneConstants.NEW_LINE);
        Map<String, Continent> l_ContinentsOfWorld = d_CurrGameMap.getContinentsInSession();
        List<String> l_ContinentsOrder = d_CurrGameMap.getContinentsInOrder();
        //store continents of world with continentName controlValue
        for (String l_ContinentName : l_ContinentsOrder) {
            l_Content.append(l_ContinentName).append(WarzoneConstants.SPACE).append(l_ContinentsOfWorld.get(l_ContinentName).getControlValue());
            l_Content.append(WarzoneConstants.NEW_LINE);
        }
        l_Content.append(WarzoneConstants.NEW_LINE);
        l_Content.append(WarzoneConstants.COUNTRIES);
        l_Content.append(WarzoneConstants.NEW_LINE);
        //store countries in [countryId countryName continentIndex] order
        for (String l_CountryName : d_CurrGameMap.getCountriesInSession().keySet()) {
            Country l_Country = d_CurrGameMap.getCountriesInSession().get(l_CountryName);
            int l_IndexOfContinent = l_ContinentsOrder.indexOf(l_Country.getIsInContinent()) + 1;
            l_Content.append(l_Country.getId()).append(WarzoneConstants.SPACE).append(l_CountryName).append(WarzoneConstants.SPACE).append(l_IndexOfContinent);
            l_Content.append(WarzoneConstants.NEW_LINE);
        }
        l_Content.append(WarzoneConstants.NEW_LINE);
        l_Content.append(WarzoneConstants.BORDERS);
        l_Content.append(WarzoneConstants.NEW_LINE);
        for (String l_CountryName : d_CurrGameMap.getCountriesInSession().keySet()) {
            Country l_Country = d_CurrGameMap.getCountriesInSession().get(l_CountryName);
            l_Content.append(l_Country.getId());
            for (Long l_Neighbor : l_Country.getAdjacentCountries().keySet()) {
                l_Content.append(WarzoneConstants.SPACE).append(l_Neighbor);
            }
            l_Content.append(WarzoneConstants.NEW_LINE);
        }

        try (PrintWriter l_Out = new PrintWriter(WarzoneConstants.GAME_SESSIONS + WarzoneConstants.FORWARD_SLASH + p_GameMapFileName)) {
            l_Out.println(l_Content);
            System.out.println("Map successfully stored in file: " + p_GameMapFileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method reads the continent data from the map file.
     * @param p_MapReader The BufferedReader for reading the map file.
     * @throws WarzoneValidationException If there's an issue reading the continent data.
     */
    private void readContinents(BufferedReader p_MapReader) {
        System.out.println("Reading continents");
        String l_CurrMapDataLine = null;
        try {
            while ((l_CurrMapDataLine = p_MapReader.readLine()) != null) {
                l_CurrMapDataLine = l_CurrMapDataLine.trim();
                if (l_CurrMapDataLine.isEmpty() || l_CurrMapDataLine.startsWith("[")) {
                    System.out.println("Continents successfully read");
                    return;
                }
                String[] l_ContinentData = l_CurrMapDataLine.split("\\s+");
                // verify that the continent data is correct
                if (l_ContinentData.length < 2) {
                    throw new WarzoneValidationException("Invalid continent data, format incorrect.");
                }
                if (!d_CurrGameMap.getContinentsInSession().containsKey(l_ContinentData[0])) {
                    d_CurrGameMap.createContinent(l_ContinentData[0], l_ContinentData[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Cannot read data from file");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method reads the country data from the map file.
     * @param p_MapReader The BufferedReader for reading the map file.
     * @throws WarzoneValidationException If there's an issue reading the country data.
     */
    private void readCountries(BufferedReader p_MapReader) throws WarzoneValidationException{
        System.out.println("Reading countries");
        String l_CurrMapDataLine = null;
        try {
            while ((l_CurrMapDataLine = p_MapReader.readLine()) != null) {
                l_CurrMapDataLine = l_CurrMapDataLine.trim();
                if (l_CurrMapDataLine.isEmpty() || l_CurrMapDataLine.startsWith("[")) {
                    System.out.println("Countries successfully read");
                    return;
                }
                String[] l_CountryData = l_CurrMapDataLine.split("\\s+");
                if (l_CountryData.length < 3) {
                    throw new WarzoneValidationException("Invalid country data, format incorrect.");
                }
                if (!l_CountryData[0].isEmpty()) {
                    String l_ContinentOfCountry = d_CurrGameMap.getContinentsInOrder().get(Integer.parseInt(l_CountryData[2]) - 1);
                    d_CurrGameMap.createCountry(l_CountryData[1], l_ContinentOfCountry, Long.parseLong(l_CountryData[0]));
                }
            }
        } catch (IOException e) {
            System.out.println("Cannot read data from file");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method reads the border data from the map file.
     * @param p_MapReader The BufferedReader for reading the map file.
     * @throws Exception If there's an issue reading the border data.
     */
    private void readBorders(BufferedReader p_MapReader) {
        System.out.println("Reading borders");
        String l_CurrMapDataLine = null;
        try {
            while ((l_CurrMapDataLine = p_MapReader.readLine()) != null) {
                l_CurrMapDataLine = l_CurrMapDataLine.trim();

                if (l_CurrMapDataLine.isEmpty() || l_CurrMapDataLine.startsWith("[")) {
                    System.out.println("Borders added successfully");
                    return;
                }

                String[] l_BordersData = l_CurrMapDataLine.split("\\s+");
                String l_CountryName = d_CurrGameMap.getCountryIds().get(Long.parseLong(l_BordersData[0]));
                if (l_CountryName != null) {
                    for (int neighbor = 1; neighbor < l_BordersData.length; neighbor++) {
                        String l_NeighboringCountry = d_CurrGameMap.getCountryIds().get(Long.parseLong(l_BordersData[neighbor]));
                        d_CurrGameMap.makeNeighbors(l_CountryName, l_NeighboringCountry);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Cannot read data from file");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}