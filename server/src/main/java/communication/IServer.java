package communication;

/**
 * Created by jalton on 10/1/18.
 */

public interface IServer {

    Result Login(String id, String password);
    Result Register(String id, String password);
    Result JoinGame(String id, String gameID, String authToken);
    Result CreateGame(Game game, String authToken);
    Result StartGame(Game game, String authToken);
    Result GetGameList(String authToken);
    Result GetPlayerList(String gameID, String authToken);
}
