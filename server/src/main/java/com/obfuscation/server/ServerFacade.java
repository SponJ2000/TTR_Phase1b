package com.obfuscation.server;

import communication.Game;
import communication.IServer;
import communication.Player;
import communication.Result;

/**
 * Created by jalton on 10/1/18.
 */

public class ServerFacade implements IServer {
    private Database db = Database.getInstance();
    private ClientProxy clientProxy = ClientProxy.getInstance();

    private static ServerFacade instance = new ServerFacade();

    public static ServerFacade getInstance(){
        return instance;
    }

    private ServerFacade() {

    }

    @Override
    public Result Login(String id, String password) {
        return db.login(id, password);
    }

    @Override
    public Result Register(String id, String password) {
        return db.register(id, password);
    }

    @Override
    public Result JoinGame(String id, String gameID, String authToken) {
        //TODO : authToken?
        clientProxy.updatePlayerList(gameID);
        return db.joinGame(id, gameID);
    }

    @Override
    public Result CreateGame(Game game, String authToken) {
        clientProxy.updateGameList();
        return db.newGame(game, authToken);
    }

    @Override
    public Result StartGame(String gameID, String authToken) {
        clientProxy.updateGameList();
        return db.startGame(gameID, authToken);
    }

    @Override
    public Result GetGameList(String authToken) {
        return new Result(true, db.getGameList(), null);
    }

    @Override
    public Result GetPlayerList(String gameID, String authToken) {
        return new Result(true, db.getActiveUsers(), null);
    }

    @Override
    public Result CheckUpdates(){
        return clientProxy.checkUpdates();
    }
}
