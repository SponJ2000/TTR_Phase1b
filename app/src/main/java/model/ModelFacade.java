package model;


import com.obfuscation.ttr_phase1b.activity.PresenterFacade;

import java.util.ArrayList;
import java.util.List;

import server.Game;
import server.Player;
import server.Result;
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
        mCurrentGame = new Game("new republic (the game id)", mHost.getmUsername(), fakePlayers, 5);
    }

    public static ModelFacade getInstance() {
        if (modelFacade == null) {
            return new ModelFacade();
        }
        return modelFacade;
    }

    public Result Login(String id, String password){
        ServerProxy serverProxy = new ServerProxy();
        serverProxy.Login(id,password);
        PresenterFacade.getInstance().onComplete(null);
        return null;
    }

    public Result Register(String id, String password) {
        ServerProxy serverProxy = new ServerProxy();
        serverProxy.Register(id,password);
        PresenterFacade.getInstance().onComplete(null);
        return null;
    }

    public Result JoinGame(String id, Game game) {
        ServerProxy serverProxy = new ServerProxy();
//        serverProxy.JoinGame(id,game);
        PresenterFacade.getInstance().onComplete(null);
        return null;
    }

    public Result CreateGame(Game game) {
        ServerProxy serverProxy = new ServerProxy();
//        serverProxy.CreateGame(game);
        PresenterFacade.getInstance().onComplete(null);
        return null;
    }

    public Result LeaveGame(Game game) {
        ServerProxy serverProxy = new ServerProxy();
//        serverProxy.StartGame(game);
        PresenterFacade.getInstance().onComplete(null);
        return null;
    }

    public Result StartGame(Game game) {
        ServerProxy serverProxy = new ServerProxy();
//        serverProxy.StartGame(game);
        PresenterFacade.getInstance().onComplete(null);
        return null;
    }

    public Result GetGameList() {
        ServerProxy serverProxy = new ServerProxy();
//        serverProxy.GetGameList();
        PresenterFacade.getInstance().onComplete(null);
        return null;
    }

    public Game GetCurrentGame() {
        return mCurrentGame;
    }

}
