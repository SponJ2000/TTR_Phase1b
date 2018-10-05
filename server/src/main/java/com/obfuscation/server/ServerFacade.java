package com.obfuscation.server;

import communication.Game;
import communication.IServer;
import communication.Result;

/**
 * Created by jalton on 10/1/18.
 */

public class ServerFacade implements IServer {

    private static ServerFacade instance = new ServerFacade();

    public static ServerFacade getInstance(){
        return instance;
    }

    private ServerFacade() {

    }

    @Override
    public Result Login(String id, String password) {
        Database.getInstance();
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
