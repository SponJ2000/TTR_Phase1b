package com.obfuscation.server;

import java.util.ArrayList;
import java.util.List;

import communication.Card;
import communication.Game;
import communication.IServer;
import communication.Player;
import communication.Result;
import communication.Ticket;

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
        System.out.println("On login");
        return db.login(id, password);
    }

    @Override
    public Result Register(String id, String password) {
        return db.register(id, password);
    }

    @Override
    public Result JoinGame(String id, String gameID, String authToken) {
        if(!db.checkAuthToken(authToken, id)) {
            return new Result(false, null, "Error: Invalid authorization");
        }
        Result result = db.joinGame(id, gameID);
        if(result.isSuccess()) {
            clientProxy.updateGameList(null);
            clientProxy.updateGame(gameID);
        }
        return result;
    }

    @Override
    public Result LeaveGame(String id, String gameID, String authToken) {
        if(!db.checkAuthToken(authToken, id)) {
            return new Result(false, null, "Error: Invalid authorization");
        }

        Result result = db.leaveGame(gameID, id);
        if(result.isSuccess()) {
            clientProxy.updateGameList(null);
            clientProxy.updateGame(gameID);
        }
        return result;
    }

    @Override
    public Result CreateGame(Game game, String authToken) {
        Result result = db.newGame(game, authToken);
        if(result.isSuccess()) {
            clientProxy.updateGameList(game.getGameID());
        }
        return result;
    }

    @Override
    public Result StartGame(String gameID, String authToken) {
        Result result = db.startGame(gameID, authToken);
        if(result.isSuccess()) {
            clientProxy.updateGameList(gameID);
        }
        return result;
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
    public Result CheckGameList(String authToken){
        System.out.println("User Checking gamelist");
        return clientProxy.checkUpdates(null);
    }

    @Override
    public Result CheckGame(String authToken, String gameID, int state) {
        System.out.println("User checking game");
        Result result = clientProxy.checkUpdates(gameID);
        System.out.println("With isSuccess" + result.isSuccess());
        return result;
    }

    @Override
    public Result CheckGameLobby(String authToken, String gameID) {
        return null;
    }

    @Override
    public Result GetUpdates(String authToken, String gameID, int state) {
        return null;
    }

    @Override
    public Result ClaimRoute(String routeID, ArrayList<Card> cards, String authToken) {
        return null;
    }

    @Override
    public Result DrawTrainCard(Integer index, String authToken) {
        return null;
    }

    @Override
    public Result GetTickets(String authToken) {
        return null;
    }

    @Override
    public Result ReturnTickets(List<Ticket> tickets, String authToken) {
        return null;
    }

    @Override
    public Result SendMessage(String gameID, String message, String authToken) {
        Result result = db.sendMessage(gameID, message, authToken);
        if(result.isSuccess()) {
            clientProxy.updateGameList(gameID);
        }
        return result;
    }
}
