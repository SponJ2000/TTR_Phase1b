package server;

/**
 * Created by hao on 10/3/18.
 */

public class ServerProxy implements IServer{

    public Result Login(String id, String password) {


        return null;
    }
    public Result Register(String id, String password) {
        return null;
    }
    public Result JoinGame(String id, String gameID, String authToken) {
        return null;
    }
    public Result CreateGame(Game game, String authToken) {
        return null;
    }
    public Result StartGame(Game game, String authToken) {
        return null;
    }
    public Result GetGameList(String authToken) {
        return null;
    }
    public Result GetPlayerList(String gameID, String authToken) {
        return null;
    }
}
