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

    public void Login(String id, String password){
        ServerProxy serverProxy = new ServerProxy();
        serverProxy.Login(id,password);

    }

    public void Register(String id, String password) {
        ServerProxy serverProxy = new ServerProxy();
        serverProxy.Register(id,password);

    }

    public void JoinGame(String id, Game game) {
        ServerProxy serverProxy = new ServerProxy();
        serverProxy.JoinGame(id,game);
    }

    public void CreateGame(Game game) {
        ServerProxy serverProxy = new ServerProxy();
        serverProxy.CreateGame(game);

    }
    public void StartGame(Game game) {
        ServerProxy serverProxy = new ServerProxy();
        serverProxy.StartGame(game);

    }

    public void GetGameList() {
        ServerProxy serverProxy = new ServerProxy();
        serverProxy.GetGameList();
    }
}
