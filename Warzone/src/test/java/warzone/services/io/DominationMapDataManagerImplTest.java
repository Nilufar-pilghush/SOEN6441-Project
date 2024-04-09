package test.java.warzone.services.io;

import main.java.warzone.services.io.DominationMapDataHandlerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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
public class DominationMapDataManagerImplTest {

    /**
     * Class to be tested
     */
    private DominationMapDataHandlerImpl gameMapDataManager;

    /**
     * Constructor for initialization
     */
    public DominationMapDataManagerImplTest(){
        gameMapDataManager = new DominationMapDataHandlerImpl();
    }

    /**
     * Test case to create game world from file
     */
    @Test
    public void whenMakeGameWorld_ExpectGameWorld(){
        String input1 = "[continents]\n[countries]\n[borders]";
        InputStream in = new ByteArrayInputStream(input1.getBytes());
        Assertions.assertDoesNotThrow(()-> gameMapDataManager.makeGameSession(in));
    }
}
