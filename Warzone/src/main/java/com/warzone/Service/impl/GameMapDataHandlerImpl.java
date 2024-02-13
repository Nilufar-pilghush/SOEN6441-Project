package main.java.com.warzone.Service.impl;

// implement the GameMapDataHandler interface methods

import main.java.com.warzone.Service.GameMapDataHandler;

import java.io.InputStream;
import java.io.OutputStream;

public class GameMapDataHandlerImpl implements GameMapDataHandler {

    // check if the input stream is there
    // read the map data from the stream
    // add the relevant data to the game map variables (countries, continents, borders)
    @Override
    public void createGameMap(InputStream p_InputStream) {

    }

    // get the current map file being changed
    // change the continents, countries and borders in according to the user input
    // add all the changes in to a new file and that file will become the map for the game
    @Override
    public void saveGameMap(OutputStream p_OutputStream) {

    }
}
