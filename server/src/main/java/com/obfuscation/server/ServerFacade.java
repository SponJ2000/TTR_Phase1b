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
        clientproxies.add(new ClientProxy("authBob"));
        clientproxies.add(new ClientProxy("authJoe"));
        gameIDclientProxyMap.put("GAME", new ArrayList<ClientProxy>());
        gameIDclientProxyMap.get("GAME").add(clientproxies.get(0));
        gameIDclientProxyMap.get("GAME").add(clientproxies.get(1));
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
        Result result = db.register(id, password);
        if (result.isSuccess()) {
            clientproxies.add(new ClientProxy((String)result.getData()));
        }
        return result;
    }

    @Override
    public Result JoinGame(String id, String gameID, String authToken) {
        try {
            if (!db.checkAuthToken(authToken, id)) {
                return new Result(false, null, "Error: Invalid authorization");
            }
            Result result = db.joinGame(id, gameID);
            if (result.isSuccess()) {
                for (ClientProxy clientProxy : clientproxies) {
                    clientProxy.updateGameList(null);
                }
                for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
                    clientProxy.updateGame(gameID);
                }
                gameIDclientProxyMap.get(gameID).add(getClientProxyByAuthToken(authToken));
            }
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Result LeaveGame(String id, String gameID, String authToken) {
        try {
            if (!db.checkAuthToken(authToken, id)) {
                return new Result(false, null, "Error: Invalid authorization");
            }

            Result result = db.leaveGame(gameID, id);
            if (result.isSuccess()) {
                for (ClientProxy clientProxy : clientproxies) {
                    clientProxy.updateGameList(null);
                }
                gameIDclientProxyMap.get(gameID).remove(getClientProxyByAuthToken(authToken));
                for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
                    clientProxy.updateGame(gameID);
                }
            }
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Result CreateGame(Game game, String authToken) {
        try {
            Result result = db.newGame(game, authToken);
            System.out.println("WHAT IS " + result.toString());
            if (result.isSuccess()) {
                for (ClientProxy clientProxy : clientproxies) {
                    clientProxy.updateGameList(game.getGameID());
                }
                gameIDclientProxyMap.put(game.getGameID(), new ArrayList<ClientProxy>());
                gameIDclientProxyMap.get(game.getGameID()).add(getClientProxyByAuthToken(authToken));
            }
            System.out.println("CREATE " + result.toString());
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Result StartGame(String gameID, String authToken) {
        Result result = db.startGame(gameID, authToken);
        if(result.isSuccess()) {
            Game game = (Game) result.getData();
            for (ClientProxy clientProxy : clientproxies) {
                clientProxy.updateGameList(game.getGameID());
            }

            //set colors and set orders
            for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
                clientProxy.initializeGame(game);
            }

            db.setupGame(gameID);
            game = db.getGame(gameID);

//            //update tickets (distribute 3 cards)
//            for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
//                String playerID = db.findPlayerIDByAuthToken(clientProxy.getAuthToken());
//                clientProxy.updateTickets(gameID, game.getPlayerbyID(playerID).getTickets());
//            }
//
//            //update destination card deck number
//            for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
//                clientProxy.updateDestinationDeck(gameID, game.getTickets().size());
//            }
//
//            //update train cards for each player (4 cards)
//            for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
//                String playerID = db.findPlayerIDByAuthToken(clientProxy.getAuthToken());
//                clientProxy.updateTrainCards(gameID, game.getPlayerbyID(playerID).getCards());
//            }
//
//            //update train card deck
//            for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
//                clientProxy.updateTrainDeck(gameID, game.getTrainCards(), game.getTrainCards().size());
//            }
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
                return new Result(true, clientProxy.getNotSeenCommands(gameID, state), null);
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
        System.out.println("EE");
        System.out.println(result.toString());
        return result;
    }

    @Override
    public Result CheckGame(String authToken, String gameID, Integer state) {
        for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
            if (clientProxy.getAuthToken().equals(authToken)) {
                return clientProxy.getNotSeenCommands(gameID, state);
            }
        }
        return new Result(false, null, "Error : Client not found");
    }

    @Override
    public Result CheckGameLobby(String authToken, String gameID) {
        try {
            System.out.println("User checking gsame");
            Result result = null;
            for (ClientProxy clientProxy : clientproxies) {
                System.out.println(clientProxy.getAuthToken());
                System.out.println(authToken);
                System.out.println("^^^^^^^^^");
                if (clientProxy.getAuthToken().equals(authToken)) {
                    result = clientProxy.checkUpdates(gameID);
                }
            }

            System.out.println("With isSuccess" + result.isSuccess());
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Result ClaimRoute(String routeID, ArrayList<Card> cards, String authToken) {
        return null;
    }

    // -1 -> from the deck
    // index -> faceup
    // returns result with the card
    @Override
    public Result DrawTrainCard(Integer index, String authToken) { //TODO : need gameID
//        Result result = db.drawTrainCard(gameID, );
//        //update tickets (distribute 3 cards)
//        for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
//            String playerID = db.findPlayerIDByAuthToken(clientProxy.getAuthToken());
//            clientProxy.updateTickets(gameID, game.getPlayerbyID(playerID).getTickets());
//        }
        return null;
    }

    //
    @Override
    public Result GetTickets(String gameID, String authToken) { //TODO : need gameID

        //return the tickets to the clients
        return new Result(true, db.getTickets(gameID, authToken), null);
    }

    //
    @Override
    public Result ReturnTickets(String gameID, String authToken, List<Ticket> ticketsToKeep) {
        Result result = db.setTickets(gameID, authToken, ticketsToKeep);
        String playerID = db.findPlayerIDByAuthToken(authToken);
        if (result.isSuccess()) {
            for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
                clientProxy.updateOpponentTickets(gameID, playerID, (Integer) result.getData());
            }
        }
        return result;
    }
}
