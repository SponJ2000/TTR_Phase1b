package communication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jalton on 10/1/18.
 */

public class Game {
    String name;
    String gameID;
    List<Player> players;
    int maxPlayers;
    boolean isStarted;

    public Game(String name, int maxPlayers) {
        this.name = name;
        this.maxPlayers = maxPlayers;

        players = new ArrayList<>();
        isStarted = false;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public void addPlayer(Player player){
        if (players.size() < maxPlayers) {
            players.add(player);
        }
    }

    public void removePlayer(Player player){
        players.remove(player);
    }

    public void startGame(){
        isStarted = true;
    }

    public String getName() {
        return name;
    }

    public String getGameID() {
        return gameID;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public boolean isStarted() {
        return isStarted;
    }
}
