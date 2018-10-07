package model;


import java.util.ArrayList;
import java.util.List;

import communication.Player;
import server.ServerProxy;
import task.GenericTask;

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
        GenericTask genericTask = new GenericTask("Login");
        genericTask.execute(id,password);
    }

    public void Register(String id, String password) {
        GenericTask genericTask = new GenericTask("Register");
        genericTask.execute(id,password);
    }

    public void JoinGame(String id, Game game) {
        GenericTask genericTask = new GenericTask("JoinGame");
        genericTask.execute(id, game, ModelRoot.getInstance().getAuthToken());
    }

    public void CreateGame(Game game) {
        GenericTask genericTask = new GenericTask("CreateGame");
        genericTask.execute(game, ModelRoot.getInstance().getAuthToken());
    }

    public void LeaveGame(Game game) {
        GenericTask genericTask = new GenericTask("LeaveGame");
        genericTask.execute(game);
    }

    public void StartGame(Game game) {
        GenericTask genericTask = new GenericTask("StartGame");
        genericTask.execute(game, ModelRoot.getInstance().getAuthToken());
    }

    public void GetGameList() {
        GenericTask genericTask = new GenericTask("GetGameList");
        genericTask.execute();
    }

    public Game GetCurrentGame() {
        return mCurrentGame;
    }

}
