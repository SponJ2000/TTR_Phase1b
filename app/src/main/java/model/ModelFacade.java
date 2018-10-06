package model;


import java.util.ArrayList;
import java.util.List;

import communication.Player;
import server.ServerProxy;

/**
 * Created by hao on 10/3/18.
 */

public class ModelFacade {

    private static ModelFacade modelFacade;

    private Game mCurrentGame;

    private ModelFacade() {
        Player mHost = new Player("Bob (the host)");
        List<Player> fakePlayers = new ArrayList<>();
        fakePlayers.add(mHost);
        fakePlayers.add( new Player("player 2") );
        fakePlayers.add( new Player("player 3") );
        fakePlayers.add( new Player("player 4") );
        mCurrentGame = new Game("new republic (the game id)", mHost.getPlayerName(), fakePlayers, 5);
    }

    public static ModelFacade getInstance() {
        if (modelFacade == null) {
            modelFacade = new ModelFacade();
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
//        serverProxy.JoinGame(id,game);
    }

    public void CreateGame(Game game) {
        ServerProxy serverProxy = new ServerProxy();
//        serverProxy.CreateGame(game);
    }

    public void LeaveGame(Game game) {
        ServerProxy serverProxy = new ServerProxy();
//        serverProxy.StartGame(game);
    }

    public void StartGame(Game game) {
        ServerProxy serverProxy = new ServerProxy();
//        serverProxy.CreateGame(game);
    }

    public void GetGameList() {
        ServerProxy serverProxy = new ServerProxy();
//        serverProxy.GetGameList();
    }

    public Game GetCurrentGame() {
        return mCurrentGame;
    }

}
