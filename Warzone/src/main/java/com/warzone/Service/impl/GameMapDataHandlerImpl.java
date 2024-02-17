package main.java.com.warzone.Service.impl;
import main.java.com.warzone.Entities.Continent;
import main.java.com.warzone.Entities.Country;
import main.java.com.warzone.Entities.GameSession;
import main.java.com.warzone.Service.GameMapDataHandler;
import main.java.com.warzone.Service.GamePhaseService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
/**
 * Implements the {@link GameMapDataHandler} interface.
 * Handles the creation and saving of game maps, including continents, countries, and borders.
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
     * Represents the current game session containing map data.
     */
    private GameSession d_CurrGameMap;

    /**
     * BufferedReader to read the map file.
     */
    private BufferedReader d_MapReader;

    /**
     * Constructs a GameMapDataHandlerImpl object.
     * Initializes the current game session and map reader.
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
     * @throws Exception If the input stream is null or if there's an error reading the data.
     */
    @Override
    public void createGameMap(InputStream p_InputStream) throws Exception {
        if (p_InputStream == null) {
            throw new FileNotFoundException("Unable to find map file.");
        }
//        InputStreamReader l_InputStreamReader = new InputStreamReader(p_InputStream, StandardCharsets.UTF_8);
        d_MapReader = new BufferedReader(new InputStreamReader(p_InputStream, StandardCharsets.UTF_8));
        String l_CurrMapDataLine = null;
        boolean isReadingContinents = false, isReadingCountries = false, isReadingBorders = false;

        try {
            while ((l_CurrMapDataLine = d_MapReader.readLine()) != null) {
                l_CurrMapDataLine = l_CurrMapDataLine.trim();
                if (l_CurrMapDataLine.contains("[continents]")) {
                    isReadingContinents = true;
                    isReadingCountries = false;
                    isReadingBorders = false;
                    continue;
                }
                else if (l_CurrMapDataLine.contains("[countries]")) {
                    isReadingCountries = true;
                    isReadingContinents = false;
                    isReadingBorders = false;
                    continue;
                }
                else if (l_CurrMapDataLine.contains("[borders]")) {
                    isReadingBorders = true;
                    isReadingContinents = false;
                    isReadingCountries = false;
                    continue;
                }

                if (isReadingContinents && !l_CurrMapDataLine.isEmpty()) {
                    String[] l_ContinentData = l_CurrMapDataLine.split("\\s+");
                    // verify that the continent data is correct
                    if (l_ContinentData.length < 2) {
                        throw new Error("Invalid continent data, format incorrect.");
                    }
                    d_CurrGameMap.createContinent(l_ContinentData[0], l_ContinentData[1]);
                }
                else if (isReadingCountries && !l_CurrMapDataLine.isEmpty()) {
                    String[] l_CountryData = l_CurrMapDataLine.split("\\s+");
                    if (l_CountryData.length < 3) {
                        throw new Error("Invalid country data, format incorrect.");
                    }
                    String l_ContinentOfCountry = d_CurrGameMap.getContinentsInOrder().get(Integer.parseInt(l_CountryData[2]) - 1);
                    d_CurrGameMap.createCountry(l_CountryData[1], l_ContinentOfCountry, Long.parseLong(l_CountryData[0]));
                }
                else if (isReadingBorders && !l_CurrMapDataLine.isEmpty()) {
                    String[] l_BordersData = l_CurrMapDataLine.split("\\s+");
                    String l_CountryName = d_CurrGameMap.getCountryIds().get(Long.parseLong(l_BordersData[0]));
                    if (l_CountryName != null) {
                        for (int neighbor = 1; neighbor < l_BordersData.length; neighbor++) {
                            String l_NeighboringCountry = d_CurrGameMap.getCountryIds().get(Long.parseLong(l_BordersData[neighbor]));
                            d_CurrGameMap.createNeighbors(l_CountryName, l_NeighboringCountry);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Cannot read data from file");
        }
    }

    /**
     * Writes the current game map data to a new file.
     * Formats the map data including continents, countries, and borders.
     * Writes the formatted data to the specified output stream.
     *
     * @param p_GameMapNewFileName The name of the new file to save the map data.
     */
    @Override
    public void saveGameMap(OutputStream p_GameMapNewFileName) {
        StringBuilder l_NewMapData = new StringBuilder();
        // Get the current continents in the game map
        Map<String, Continent> l_CurrContinentsInSession = d_CurrGameMap.getContinentsInSession();
        // Get the list of current continents in the game map which should be added in order
        List<String> l_ContinentsInOrder = d_CurrGameMap.getContinentsInOrder();

        // Loop through the continents and add their data to l_NewMapData
        l_NewMapData.append("[continents]");
        l_NewMapData.append("\n");
        for (String l_ContinentName : l_ContinentsInOrder) {
            l_NewMapData.append(l_ContinentName).append("\\s+").append(l_CurrContinentsInSession.get(l_ContinentName).getControlValue());
            l_NewMapData.append("\n");
        }

        /* Loop through the countries and add their data to l_NewMapData */
        l_NewMapData.append("\n");
        l_NewMapData.append("[countries]");
        l_NewMapData.append("\n");
        for (String l_CountryName : d_CurrGameMap.getCountriesInSession().keySet()) {
            Country l_Country = d_CurrGameMap.getCountriesInSession().get(l_CountryName);
            int l_ContinentIndex = l_ContinentsInOrder.indexOf(l_Country.getIsInContinent()) + 1;
            l_NewMapData.append(l_Country.getId()).append("\\s+").append(l_CountryName).append("\\s+").append(l_ContinentIndex);
            l_NewMapData.append("\n");
        }

        /* Loop through the borders and add their data to l_NewMapData */
        l_NewMapData.append("\n");
        l_NewMapData.append("[borders]");
        l_NewMapData.append("\n");
        for (String l_CountryName : d_CurrGameMap.getCountriesInSession().keySet()) {
            Country l_Country = d_CurrGameMap.getCountriesInSession().get(l_CountryName);
            l_NewMapData.append(l_Country.getId());
            for (Long l_Neighbor : l_Country.getAdjacentCountries().keySet()) {
                l_NewMapData.append("\\s+").append(l_Neighbor);
            }
            l_NewMapData.append("\n");
        }

        /* write l_NewMapData to the newMapfile stated on the outpstream */
        PrintWriter l_FileWrite = null;
        try {
            l_FileWrite = new PrintWriter("GameMap" + "/" + p_GameMapNewFileName);
            l_FileWrite.println(l_NewMapData);
            System.out.println("Map successfully saved in the file " + p_GameMapNewFileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}