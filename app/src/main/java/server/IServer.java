package server;

import model.Game;

/**
 * Created by hao on 10/3/18.
 */

public interface IServer {
    public Result Login(String id, String password);
    public Result Register(String id, String password);
    public Result JoinGame(String id, String gameID, String authToken);
    public Result CreateGame(Game game, String authToken);
    public Result StartGame(String gameID, String authToken);
    public Result GetGameList(String authToken);
    public Result GetPlayerList(String gameID, String authToken);
}
