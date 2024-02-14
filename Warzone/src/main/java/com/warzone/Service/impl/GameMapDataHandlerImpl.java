package main.java.com.warzone.Service.impl;

// implement the GameMapDataHandler interface methods

import main.java.com.warzone.Entities.GameSession;
import main.java.com.warzone.Service.GameMapDataHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class GameMapDataHandlerImpl implements GameMapDataHandler {

    private GameSession d_CurrGameMap;
    private BufferedReader d_MapReader;

    public GameMapDataHandlerImpl() {
        d_CurrGameMap = GameSession.getInstance();
        d_MapReader = null;
    }

    // check if the input stream is there
    // read the map data from the stream
    // add the relevant data to the game map variables (countries, continents, borders)
    @Override
    public void createGameMap(InputStream p_InputStream) throws FileNotFoundException {
        if (p_InputStream == null) {
            throw new FileNotFoundException("Unable to find map file.");
        }
//        InputStreamReader l_InputStreamReader = new InputStreamReader(p_InputStream, StandardCharsets.UTF_8);
        d_MapReader = new BufferedReader(new InputStreamReader(p_InputStream, StandardCharsets.UTF_8));
        String l_CurrMapDataLine = null;
        try {
            while ((l_CurrMapDataLine = d_MapReader.readLine()) != null){
                if (l_CurrMapDataLine.contains("[continents]")) {
                    String[] l_ContinentData = l_CurrMapDataLine.split("\\s+");
                    // verify that the continent data is correct
                    if (l_ContinentData.length < 2) {
                        throw new Error("Invalid continent data, format incorrect.");
                    }
                    d_CurrGameMap.createContinent(l_ContinentData[0], l_ContinentData[1]);
                }
                else if (l_CurrMapDataLine.contains("[countries]")) {
                    String[] l_CountryData = l_CurrMapDataLine.split("\\s+");
                    if (l_CountryData.length < 3) {
                        throw new Error("Invalid country data, format incorrect.");
                    }
                    String l_ContinentOfCountry = d_CurrGameMap.getContinentsInOrder().get(Integer.parseInt(l_CountryData[2]) - 1);
                    d_CurrGameMap.createCountry(l_CountryData[1], l_ContinentOfCountry, Long.parseLong(l_CountryData[0]));
                }
                else if (l_CurrMapDataLine.contains("[borders]")) {
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
            System.out.println("Cannot be read data from file");
        }
    }

    // get the current map file being changed
    // change the continents, countries and borders in according to the user input
    // add all the changes in to a new file and that file will become the map for the game
    @Override
    public void saveGameMap(OutputStream p_OutputStream) {

    }
}
