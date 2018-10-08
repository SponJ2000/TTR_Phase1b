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

    Database database;

    private static ServerFacade instance = new ServerFacade();

    public static ServerFacade getInstance(){

        return instance;
    }

    private ServerFacade() {
        database = Database.getInstance();
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
        if(!db.checkAuthToken(id, authToken)) {
            return new Result(false, null, "Error: Invalid authorization");
        }
        clientProxy.updateGameList(null);
        clientProxy.updateGame(gameID);
        return db.joinGame(id, gameID);
    }

    @Override
    public Result LeaveGame(String id, String gameID, String authToken) {
        if(!db.checkAuthToken(id, authToken)) {
            return new Result(false, null, "Error: Invalid authorization");
        }
        clientProxy.updateGameList(null);
        clientProxy.updateGame(gameID);
        return db.leaveGame(id, gameID);
    }

    @Override
    public Result CreateGame(Game game, String authToken) {
        clientProxy.updateGameList(game.getGameID());
        return db.newGame(game, authToken);
    }

    @Override
    public Result StartGame(String gameID, String authToken) {
        clientProxy.updateGame(gameID);
        return db.startGame(gameID, authToken);
    }

    @Override
    public Result GetGameList(String authToken) {
        return new Result(true, db.getGameList(), null);
    }

    @Override
    public Result GetGame(String gameID, String authToken) {
        return new Result(true, db.getGame(gameID), null);
    }

    @Override
    public Result CheckUpdates(String authToken){
        return clientProxy.checkUpdates(null);
    }

    @Override
    public Result CheckGame(String authToken, String gameID){
        return clientProxy.checkUpdates(gameID);
    }
}
