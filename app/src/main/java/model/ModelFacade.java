package model;


import server.Result;
import server.ServerProxy;

/**
 * Created by hao on 10/3/18.
 */

public class ModelFacade {

    private static ModelFacade modelFacade;

    public static ModelFacade getInstance() {
        if (modelFacade == null) {
            return new ModelFacade();
        }
        return modelFacade;
    }

    public Result Login(String id, String password){
        ServerProxy serverProxy = new ServerProxy();
        serverProxy.Login(id,password);
        return null;
    }

    public Result Register(String id, String password) {
        ServerProxy serverProxy = new ServerProxy();
        serverProxy.Register(id,password);
        return null;
    }

    public Result JoinGame(String id, Game game) {
        ServerProxy serverProxy = new ServerProxy();
        serverProxy.JoinGame(id,game);
        return null;
    }

    public Result CreateGame(Game game) {
        ServerProxy serverProxy = new ServerProxy();
        serverProxy.CreateGame(game);
        return null;
    }
    public Result StartGame(Game game) {
        ServerProxy serverProxy = new ServerProxy();
        serverProxy.StartGame(game);
        return null;
    }

    public Result GetGameList() {
        ServerProxy serverProxy = new ServerProxy();
        serverProxy.GetGameList();
        return null;
    }
}
