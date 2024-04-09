package main.java.warzone.services.io;

import main.java.warzone.exceptions.WarzoneRuntimeException;
import main.java.warzone.exceptions.WarzoneValidationException;

import java.io.InputStream;

/**
 * Adapter to adapt changes as per conquest map format.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 3.0.0
 */
public class ConquestAdapter extends DominationMapDataHandlerImpl {
    /**
     * Adaptee instance to provide implementation
     */
    private ConquestAdaptee d_ConquestAdaptee;
    /**
     * Constructor to instantiate adaptee
     *
     * @param p_ConquestAdaptee Adaptee to use for instantiation
     */
    public ConquestAdapter(ConquestAdaptee p_ConquestAdaptee) {
        this.d_ConquestAdaptee = p_ConquestAdaptee;
    }

    @Override
    public void saveGameSession(String p_GameSessionFileName) {
        d_ConquestAdaptee.saveGameSession(p_GameSessionFileName);
    }

    @Override
    public void makeGameSession(InputStream p_InputStream) throws WarzoneRuntimeException, WarzoneValidationException {
        d_ConquestAdaptee.makeGameSession(p_InputStream);
    }

}
