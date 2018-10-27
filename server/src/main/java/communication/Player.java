package communication;

import java.util.ArrayList;

/**
 * Created by jalton on 10/1/18.
 */

public class Player {
    private String id;
    private String playerName;
    private ArrayList<DestinationTicketCard> destinationTickectCardArrayList = new ArrayList<DestinationTicketCard>();

    public Player(String id, String playerName) {
        this.id = id;
        this.playerName = playerName;
    }

    public Player(String playerName) {
        this.id = null;
        this.playerName = playerName;
    }

    public String getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }
}
