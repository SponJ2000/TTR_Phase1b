package communication;

/**
 * Created by jalton on 10/1/18.
 */

public class Player {
    String id;
    String playerName;

    public Player(String id, String playerName) {
        this.id = id;
        this.playerName = playerName;
    }

    public String getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }
}
