package communication;

/**
 * Created by urimaj on 11/14/18.
 */

public class GameHistory {

    String gameID;

    String playerName;

    String action;

    public GameHistory(String gameID, String playerName, String action) {
        this.gameID = gameID;
        this.playerName = playerName;
        this.action = action;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String toString() {
        return playerName + "_" + action;
    }
}
