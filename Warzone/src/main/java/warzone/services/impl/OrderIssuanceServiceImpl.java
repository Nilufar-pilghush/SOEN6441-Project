package main.java.warzone.services.impl;
import main.java.warzone.constants.WarzoneConstants;
import main.java.warzone.entities.GamePhase;
import main.java.warzone.entities.GameSession;
import main.java.warzone.entities.Player;
import main.java.warzone.entities.orders.Order;
import main.java.warzone.services.GamePhaseService;
import main.java.warzone.utils.CmdUtils;
import main.java.warzone.utils.logging.impl.LogEntryBuffer;

import java.util.*;

/**
 * Implements the {@link GamePhaseService} interface managing the order issuance phase in the Warzone main.java.game.
 * Initializes with the current main.java.game session and prepares to take input from players
 * for issuing orders.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 2.0.0
 */

public class OrderIssuanceServiceImpl implements GamePhaseService {

    /**
     * Current game session instance
     */
    private GameSession d_GameSession;

    /**
     * LogEntryBuffer object to log the information and
     * notify all the observers
     */
    private LogEntryBuffer d_LogEntryBuffer;

    /**
     * Scanner to take user input
     */
    private Scanner d_InputScanner;

    /**
     * Constructor to initialize IssueOrdersService
     */
    public OrderIssuanceServiceImpl(){
        d_InputScanner = new Scanner(System.in);
        d_GameSession = GameSession.getInstance();
        d_LogEntryBuffer = LogEntryBuffer.getInstance();
    }

    
    /**
     * Manages the present game phase and carries out tasks related to giving orders.
     *
     * @param p_CurrPhase the ongoing game phase
     * @return the subsequent game phase post the tasks. In this version, it consistently returns {@link GamePhase#EXIT}
     */
    @Override
    public GamePhase handleGamePhase(GamePhase p_CurrPhase) {
        Scanner l_InputScanner = new Scanner(System.in);
        d_LogEntryBuffer.logData("Issue order service handler");
        d_LogEntryBuffer.logData("Looping over players and asking them to issue orders");

        // Loop over players & ask them to issue orders
        Iterator<Map.Entry<String, Player>> l_PlayerIterator = d_GameSession.getPlayers().entrySet().iterator();
        while (l_PlayerIterator.hasNext()) {
            Map.Entry<String, Player> l_PlayerEntry = l_PlayerIterator.next();
            Player l_Player = l_PlayerEntry.getValue();
            l_Player.issueOrder(d_GameSession);

        }
        return p_CurrPhase.validateAndMoveToNextState(GamePhase.MAIN_GAME_LOOP);
    }
}

