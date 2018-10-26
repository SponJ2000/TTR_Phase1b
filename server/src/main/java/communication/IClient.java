package communication;

/**
 * Created by jalton on 10/1/18.
 */

public interface IClient {


    void updateGameList(String gameID);

    //TODO: this functon should be replaced with function that has specific purpose, like OpponentClaimRoute()...
    void updateGame(String gameID);

    void updateChat();
}
