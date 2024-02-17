package main.java.com.warzone.Service.impl;

// implement the GameMapDataHandler interface methods

import main.java.com.warzone.Entities.Continent;
import main.java.com.warzone.Entities.Country;
import main.java.com.warzone.Entities.GameSession;
import main.java.com.warzone.Service.GameMapDataHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Implements the {@link GameMapDataHandler} interface. Used to manage the game maps
 * like creation/saving from/to user specified files.
 */
public class GameMapDataHandlerImpl implements GameMapDataHandler {

    /**
     * Instance of the current game session
     */
    final private GameSession d_CurrGameMap;

    /**
     * Reader for processing game map file input streams.
     */
    private BufferedReader d_MapReader;

    /**
     * Initialization constructor for the GameMapDataHandler
     */
    public GameMapDataHandlerImpl() {
        d_CurrGameMap = GameSession.getInstance();
        d_MapReader = null;
    }

    /**
     * Method loads a game map from an InputStream.
     * @param p_InputStream The InputStream of the map file.
     * @throws Exception If the file cannot be found or read.
     */
    @Override
    public void createGameMap(InputStream p_InputStream) throws Exception {
        if (p_InputStream == null) {
            throw new FileNotFoundException("Unable to find map file.");
        }
        d_MapReader = new BufferedReader(new InputStreamReader(p_InputStream, StandardCharsets.UTF_8));
        d_CurrGameMap.clearExistingMap();
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
            System.out.println("Cannot read data from file");
        }
        System.out.println("Map loaded successfully");
    }


    /**
     * Method saves the current game map to an OutputStream.
     * @param p_GameMapNewFileName The OutputStream to save the map to.
     */
    @Override
    public void saveGameMap(OutputStream p_GameMapNewFileName) {
        StringBuilder l_NewMapData = new StringBuilder();
        Map<String, Continent> l_CurrContinentsInSession = d_CurrGameMap.getContinentsInSession();
        List<String> l_ContinentsInOrder = d_CurrGameMap.getContinentsInOrder();

        // loop through the continents and add their data to l_NewMapData
        l_NewMapData.append("[continents]");
        l_NewMapData.append("\n");
        for (String l_ContinentName : l_ContinentsInOrder) {
            l_NewMapData.append(l_ContinentName).append("\\s+").append(l_CurrContinentsInSession.get(l_ContinentName).getD_ControlValue());
            l_NewMapData.append("\n");
        }

        // loop through the countries and add their data to l_NewMapData
        l_NewMapData.append("\n");
        l_NewMapData.append("[countries]");
        l_NewMapData.append("\n");
        for (String l_CountryName : d_CurrGameMap.getCountriesInSession().keySet()) {
            Country l_Country = d_CurrGameMap.getCountriesInSession().get(l_CountryName);
            int l_ContinentIndex = l_ContinentsInOrder.indexOf(l_Country.get_IsInContinent()) + 1;
            l_NewMapData.append(l_Country.get_Id()).append("\\s+").append(l_CountryName).append("\\s+").append(l_ContinentIndex);
            l_NewMapData.append("\n");
        }

        // loop through the borders and add their data to l_NewMapData
        l_NewMapData.append("\n");
        l_NewMapData.append("[borders]");
        l_NewMapData.append("\n");
        for (String l_CountryName : d_CurrGameMap.getCountriesInSession().keySet()) {
            Country l_Country = d_CurrGameMap.getCountriesInSession().get(l_CountryName);
            l_NewMapData.append(l_Country.get_Id());
            for (Long l_Neighbor : l_Country.getD_AdjacentCountries().keySet()) {
                l_NewMapData.append("\\s+").append(l_Neighbor);
            }
            l_NewMapData.append("\n");
        }

        // write l_NewMapData to the newMapfile stated on the outpstream
        PrintWriter l_FileWrite = null;
        try {
            l_FileWrite = new PrintWriter("GameMap" + "/" + p_GameMapNewFileName);
            l_FileWrite.println(l_NewMapData);
            System.out.println("Map successfully saved in the file " + p_GameMapNewFileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method reads the continent data from the map file.
     * @param p_MapReader The BufferedReader for reading the map file.
     * @throws Exception If there's an issue reading the continent data.
     */
    private void readContinents(BufferedReader p_MapReader)  {
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
                    throw new Error("Invalid continent data, format incorrect.");
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
     * @throws Exception If there's an issue reading the country data.
     */
    private void readCountries(BufferedReader p_MapReader) {
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
                    throw new Error("Invalid country data, format incorrect.");
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
                        d_CurrGameMap.createNeighbors(l_CountryName, l_NeighboringCountry);
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