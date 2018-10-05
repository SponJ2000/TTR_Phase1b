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
    List<Player> absentPlayers;
    int maxPlayers;
    boolean isStarted;

    public Game(String name, int maxPlayers) {
        this.name = name;
        this.maxPlayers = maxPlayers;

        players = new ArrayList<>();
        absentPlayers = new ArrayList<>();
        isStarted = false;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public Result addPlayer(Player player){
        if (players.size() < maxPlayers) {
            if (!players.contains(player)) {
                players.add(player);
                return new Result(true, true, null);
            }
            else return new Result(false, null, "Error: player already in game");
        }
        else return new Result(false, null, "Error: game is full");
    }

    public Result removePlayer(Player player){
        if (!players.contains(player)) return new Result(false, false, "Error: Player not in game");

        if (isStarted){
            absentPlayers.add(player);
        }
        else players.remove(player);
        return new Result(true, true, null);
    }

    public Result rejoinGame(Player player) {
        if(!players.contains(player)) return new Result(false, null, "Error: Player not found");
        else if (absentPlayers.contains(player)) {
            absentPlayers.remove(player);
        }

        return new Result(true, true, null);
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
