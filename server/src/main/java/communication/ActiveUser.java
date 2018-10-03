package communication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jalton on 10/3/18.
 */

public class ActiveUser {

    Player player;
    String authToken;
    List<String> joinedGames;

    public ActiveUser(Player player, String authToken) {
        this.player = player;
        this.authToken = authToken;
        joinedGames = new ArrayList<>();
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Player getPlayer() {
        return player;
    }

    public String getAuthToken() {
        return authToken;
    }

    public List<String> getJoinedGames() {
        return joinedGames;
    }

    void joinGame(String gameID){
        joinedGames.add(gameID);
    }
}
