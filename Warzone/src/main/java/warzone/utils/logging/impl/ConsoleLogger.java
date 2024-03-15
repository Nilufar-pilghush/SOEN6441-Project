package main.java.warzone.utils.logging.impl;

import main.java.warzone.utils.logging.Observer;

/**
 * A realization of {@link Observer} to update the messages in the
 * console of the warzone game
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @since 1.0.0
 */
public class ConsoleLogger implements Observer {

    @Override
    public boolean update(String p_Notification) {
        System.out.println(p_Notification);
        return true;
    }
}