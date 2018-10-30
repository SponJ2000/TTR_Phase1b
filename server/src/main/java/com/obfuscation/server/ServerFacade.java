package com.obfuscation.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import communication.Card;
import communication.Game;
import communication.IServer;
import communication.Message;
import communication.Player;
import communication.Result;
import communication.Ticket;

/**
 * Created by jalton on 10/1/18.
 */

public class ServerFacade implements IServer {
    @Override
    public Result SendMessage(String authToken, String gameID, Message message) {
        System.out.println("Updating the message");
        //FIXME**
        Result result = db.sendMessage(gameID, message.getText(), authToken);
        if(result.isSuccess()) {
            for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
                clientProxy.updateChat(gameID, (Message) result.getData());
            }
        }
        return result;
    }

    @Override
    public Result ChooseTicket(String authToken, String gameID, List<Ticket> chosenTickets) {
        //TODO : implement this!
        return null;
    }

    private Database db = Database.getInstance();
    private List<ClientProxy> clientproxies = new ArrayList<>();
    private Map<String, List<ClientProxy>> gameIDclientProxyMap = new HashMap<>();
    private static ServerFacade instance = new ServerFacade();

    public static ServerFacade getInstance(){
        return instance;
    }

    private ServerFacade() {

    }

    private ClientProxy getClientProxyByAuthToken(String authToken) {
        for (ClientProxy clientProxy : clientproxies) {
            if (clientProxy.getAuthToken().equals(authToken)) {
                return clientProxy;
            }
        }
        return null;
    }

    @Override
    public Result Login(String id, String password) {
        System.out.println("On login");
        Result result = db.login(id, password);
        if (result.isSuccess()) {
            clientproxies.add(new ClientProxy((String)result.getData()));
        }
        return result;
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
            for (ClientProxy clientProxy : clientproxies) {
                clientProxy.updateGame(gameID);
                clientProxy.updateGameList(gameID);
            }
            gameIDclientProxyMap.get(gameID).add(getClientProxyByAuthToken(authToken));
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
            for (ClientProxy clientProxy : clientproxies) {
                clientProxy.updateGameList(gameID);
                clientProxy.updateGame(gameID);
            }
            for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
                if (clientProxy.getAuthToken().equals(authToken)) {
                    gameIDclientProxyMap.get(gameID).remove(clientProxy);
                }
            }
        }
        return result;
    }

    @Override
    public Result CreateGame(Game game, String authToken) {
        Result result = db.newGame(game, authToken);
        if(result.isSuccess()) {
            for (ClientProxy clientProxy : clientproxies) {
                clientProxy.updateGameList(game.getGameID());
            }
            gameIDclientProxyMap.put(game.getGameID(), new ArrayList<ClientProxy>());
        }
        return result;
    }

    @Override
    public Result StartGame(String gameID, String authToken) {
        Result result = db.startGame(gameID, authToken);
        if(result.isSuccess()) {
            Game game = (Game) result.getData();
            for (ClientProxy clientProxy : clientproxies) {
                clientProxy.updateGameList(game.getGameID());
            }

            //set colors
            for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
                clientProxy.initializeGame(game);
            }

            db.setupGame(gameID);
            game = db.getGame(gameID);

            //update tickets (distribute 3 cards)
            for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
                String playerID = db.findPlayerIDByAuthToken(clientProxy.getAuthToken());
                clientProxy.updateTickets(gameID, game.getPlayerbyID(playerID).getTickets());
            }

            //update destination card deck number
            for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
                clientProxy.updateDestinationDeck(gameID, game.getTickets().size());
            }

            //update destination cards for each player (4 cards)
            for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
                String playerID = db.findPlayerIDByAuthToken(clientProxy.getAuthToken());
                clientProxy.updateTrainCards(gameID, game.getPlayerbyID(playerID).getCards());
            }
        }
        return result;
    }

    /**
     * client calls this method to fetch unprocessed commands from the server.
     * @param authToken a String object containing the user's authToken
     * @param gameID    a String object containing the gameID of the game to be started
     * @param state     an Integer that record the number of command has been processed
     * @return
     */
    @Override
    public Result GetUpdates(String authToken, String gameID, Integer state) {
        for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
            if (clientProxy.getAuthToken().equals(authToken)) {
                return new Result(true, clientProxy.getNotSeenCommands(), null);
            }
        }
        return new Result(false, null, "Error : Client not found");
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
        Result result = null;
        for (ClientProxy clientProxy : clientproxies) {
            if (clientProxy.getAuthToken().equals(authToken)) {
                result = clientProxy.checkUpdates(null);
            }
        }
        return result;
    }

    @Override
    public Result CheckGame(String authToken, String gameID, Integer state) {
        System.out.println("User checking game");
        Result result = null;
        for (ClientProxy clientProxy : clientproxies) {
            if (clientProxy.getAuthToken().equals(authToken)) {
                result = clientProxy.checkUpdates(gameID);
            }
        }

        System.out.println("With isSuccess" + result.isSuccess());


        return result;
    }

    @Override
    public Result CheckGameLobby(String authToken, String gameID) {
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
}
