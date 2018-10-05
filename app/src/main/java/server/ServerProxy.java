package server;


import model.Game;

/**
 * Created by hao on 10/5/18.
 */

public class ServerProxy implements IServer {
    @Override
    public Result Login(String id, String password) {
//        GenericCommand genericCommand = new GenericCommand("ServerProxy","Login", new String[] {"String","String"},[id,password]);
        return null;
    }

    @Override
    public Result Register(String id, String password) {
        return null;
    }

    @Override
    public Result JoinGame(String id, String gameID, String authToken) {
        return null;
    }

    @Override
    public Result CreateGame(Game game, String authToken) {
        return null;
    }

    @Override
    public Result StartGame(String gameID, String authToken) {
        return null;
    }

    @Override
    public Result GetGameList(String authToken) {
        return null;
    }

    @Override
    public Result GetPlayerList(String gameID, String authToken) {
        return null;
    }
}
