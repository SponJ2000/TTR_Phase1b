package communication;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by urimaj on 11/9/18.
 */

public class LobbyGame {

    private String gameID;
    private String host;
    private int maxPlayers;
    // this is the state that increment when players join in or leave the lobby
    private int playerListVerisonNum;
    private ArrayList<Player> players = new ArrayList<>();

    private boolean started;

    public LobbyGame(String host, int maxPlayers) {
        this.host = host;
        this.maxPlayers = maxPlayers;
        this.gameID = UUID.randomUUID().toString();
    }

    public LobbyGame(String gameid, String host, ArrayList<Player> players, int maxPlayers) {
        this.gameID = gameid;
        this.host = host;
        this.players = players;
        this.maxPlayers = maxPlayers;
    }

    public LobbyGame(String host) {
        this.host = host;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public int getPlayerListVerisonNum() {
        return playerListVerisonNum;
    }

    public void setPlayerListVerisonNum(int playerListVerisonNum) {
        this.playerListVerisonNum = playerListVerisonNum;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}