package com.obfuscation.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import communication.Card;
import communication.GameClient;
import communication.LobbyGame;
import communication.GameServer;
import communication.IServer;
import communication.Message;
import communication.PlayerOpponent;
import communication.PlayerUser;
import communication.Result;
import communication.Serializer;
import communication.Ticket;

/**
 * Created by jalton on 10/1/18.
 */

public class ServerFacade implements IServer {

    private Database db = Database.getInstance();
    private List<ClientProxy> clientproxies = new ArrayList<>();
    private Map<String, List<ClientProxy>> gameIDclientProxyMap = new HashMap<>();
    private static ServerFacade instance = new ServerFacade();

    public static ServerFacade getInstance(){
        return instance;
    }

    @Override
    public Result SendMessage(String authToken, String gameID, Message message) {
        try {
            System.out.println("Updating the message");
            //FIXME**
            Result result = db.sendMessage(gameID, message.getText(), authToken);
            System.out.println(result.toString());
            if (result.isSuccess()) {
                for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
                    clientProxy.updateChat(gameID, (Message) result.getData());
                }
            }
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false, null, e.getMessage());
        }
    }

    @Override
    public Result DrawTrainCard(Integer index, String authToken) {
        return null;
    }

    private ServerFacade() {
        clientproxies.add(new ClientProxy("authBob"));
        clientproxies.add(new ClientProxy("authJoe"));
        gameIDclientProxyMap.put("GAME", new ArrayList<ClientProxy>());
        gameIDclientProxyMap.get("GAME").add(clientproxies.get(0));
        gameIDclientProxyMap.get("GAME").add(clientproxies.get(1));
        StartGame("GAME", "authBob");
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
        try {
            System.out.println("On login");
            Result result = db.login(id, password);
            if (result.isSuccess()) {
                clientproxies.add(new ClientProxy((String) result.getData()));
            }
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false, null, e.getMessage());
        }
    }

    @Override
    public Result Register(String id, String password) {
        try {
            Result result = db.register(id, password);
            if (result.isSuccess()) {
                clientproxies.add(new ClientProxy((String) result.getData()));
            }
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false, null, e.getMessage());
        }
    }

    @Override
    public Result JoinLobby(String id, String gameID, String authToken) {
        try {
            if (!db.checkAuthToken(authToken, id)) {
                return new Result(false, null, "Error: Invalid authorization");
            }
            Result result = db.joinGame(id, gameID);
            if (result.isSuccess()) {
                for (ClientProxy clientProxy : clientproxies) {
                    clientProxy.updateGameLobbyList(null);
                }

                for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
                    clientProxy.updateGame(gameID);
                }
                gameIDclientProxyMap.get(gameID).add(getClientProxyByAuthToken(authToken));
                System.out.println("JOINING GAME : " + gameIDclientProxyMap.get(gameID).size());
            }
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false, null, e.getMessage());
        }
    }

    @Override
    public Result LeaveGame(String id, String gameID, String authToken) {
        return null;
    }

    @Override
    public Result LeaveLobbyGame(String id, String gameID, String authToken) {
        try {
            if (!db.checkAuthToken(authToken, id)) {
                return new Result(false, null, "Error: Invalid authorization");
            }

            Result result = db.leaveGame(gameID, id);
            if (result.isSuccess()) {
                for (ClientProxy clientProxy : clientproxies) {
                    clientProxy.updateGameLobbyList(null);
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
            return new Result(false, null, e.getMessage());
        }
    }

    @Override
    public Result CreateLobby(LobbyGame lobbyGame, String authToken) {
        try {
            System.out.println(authToken + " EEE");
            Result result = db.newGameLobby(lobbyGame, authToken);
            System.out.println("WHAT IS " + result.toString());
            if (result.isSuccess()) {
                for (ClientProxy clientProxy : clientproxies) {
                    clientProxy.updateGameLobbyList(lobbyGame.getGameID());
                }
                gameIDclientProxyMap.put(lobbyGame.getGameID(), new ArrayList<>());
                System.out.println("AUTH TOKEN " + authToken);
                gameIDclientProxyMap.get(lobbyGame.getGameID()).add(getClientProxyByAuthToken(authToken));
            }
            System.out.println("CREATE " + result.toString());
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false, null, e.getMessage());
        }
    }

    @Override
    public Result StartGame(String gameID, String authToken) {
        try {
            Result result = db.startGame(gameID, authToken);
            if (result.isSuccess()) {
                GameServer game = (GameServer) result.getData();
                for (ClientProxy clientProxy : clientproxies) {
                    clientProxy.updateGameLobbyList(game.getGameID());
                }

                //Initializes everything except the tickets
                //TODO : need to figure out how to handle this part
                db.setupGame(gameID);
                ArrayList<Object> objects = new ArrayList<>();
                game = db.findGameByID(gameID);
                objects.add(game);
                System.out.println("STARTING GAME : " + gameIDclientProxyMap.get(gameID).size());
                for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
                    System.out.println("UPDATING : " + clientProxy.getAuthToken());
                    System.out.println(db.findUsernameByAuthToken(clientProxy.getAuthToken()));
                    clientProxy.updateGame(gameID);
                    clientProxy.updateGame(gameID);
                    String username = db.findUsernameByAuthToken(clientProxy.getAuthToken());

                    //initalize game TODO : SET GAMECLIENTS
                    GameClient gameClient = new GameClient();
                    gameClient.setGameID(gameID);
                    gameClient.setPlayerUser(game.getPlayerbyUserName(username));
                    gameClient.setTrainCardDeckSize(game.getTrainCards().size());
                    gameClient.setFaceUpTrainCarCards(game.getFaceUpTrainCarCards());
                    gameClient.getPlayerUser().setTicketToChoose(game.getPlayerbyUserName(username).getTicketToChoose());
                    gameClient.setTicketDeckSize(game.getTickets().size());

                    //initialize user player
                    PlayerUser playerUser = new PlayerUser(username);
                    ArrayList<PlayerOpponent> opponents = new ArrayList<>();

                    //initialize opponents
                    for (ClientProxy clientProxy1 : gameIDclientProxyMap.get(gameID)) {
                        if (!clientProxy.getAuthToken().equals(clientProxy1.getAuthToken())) {
                            opponents.add(new PlayerOpponent(db.findUsernameByAuthToken(clientProxy1.getAuthToken()), 0, 40, 4));
                        }
                    }
                    gameClient.setPlayerOpponents(opponents);

                    objects.add(gameClient);
                    clientProxy.initializeGame(gameClient);


                    //add command to initialize
                }
                return new Result(true, objects, null);

//            //update tickets (distribute 3 cards)
//            for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
//                String playerID = db.findUsernameByAuthToken(clientProxy.getAuthToken());
//                clientProxy.updateTickets(gameID, game.getPlayerbyUserName(playerID).getTickets());
//                Result result1 = GetTickets(gameID, clientProxy.getAuthToken());
//            }

                //update destination card deck number
//                for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
//                    clientProxy.updateDestinationDeck(gameID, game.getTickets().size());
//                }
//
//                //TODO : needed? Taken care of in the initalize game
//                //update train cards for each player (4 cards)
//                for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
//                    String username = db.findUsernameByAuthToken(clientProxy.getAuthToken());
//                    System.out.println("UPDATING TRAIN CARDS");
//                    System.out.println(game.getPlayerbyUserName(username).getCards());
//                    clientProxy.updateTrainCards(gameID, game.getPlayerbyUserName(username).getCards());
//                }
//
//                //update train card deck
//                for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
//                    clientProxy.updateTrainDeck(gameID, game.getFaceUpTrainCarCards(), game.getTrainCards().size());
//                }
            }
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false, null, e.getMessage());
        }
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
        try {
            for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
                if (clientProxy.getAuthToken().equals(authToken)) {
                    return new Result(true, clientProxy.getNotSeenCommands(gameID, state), null);
                }
            }
            return new Result(false, null, "Error : Client not found");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false, null, e.getMessage());
        }
    }

    @Override
    public Result GetLobbyList(String authToken) {
        try {
            return new Result(true, db.getLobbyList(), null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false, null, e.getMessage());
        }
    }

    @Override
    public Result GetGame(String gameID, String authToken) {
        try {
            return new Result(true, db.findGameByID(gameID), null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false, null, e.getMessage());
        }
    }

    @Override
    public Result CheckGameList(String authToken){
        try {
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
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false, null, e.getMessage());
        }
    }

    @Override
    public Result CheckGame(String authToken, String gameID, Integer state) {
        try {
            for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
                if (clientProxy.getAuthToken().equals(authToken)) {
                    return clientProxy.getNotSeenCommands(gameID, state);
                }
            }
            return new Result(false, null, "Error : Client not found");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false, null, e.getMessage());
        }
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
            return new Result(false, null, e.getMessage());
        }
    }

    @Override
    public Result ClaimRoute(String gameID, String routeID, List<Card> cards, String authToken) {
        try {
            Result result = db.claimRoute(gameID, routeID, cards, authToken);
            return new Result(true, "D", null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false, null, e.getMessage());
        }
    }

    // -1 -> from the deck
    // index -> faceup
    // returns result with the card
//   card @Override
//    public Result DrawTrainCard(Integer index, String gameID, String authToken) { //TODO : need gameID
////        Result result = db.drawTrainCard(gameID, );
////        //update tickets (distribute 3 cards)
////        for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
////            String playerID = db.findUsernameByAuthToken(clientProxy.getAuthToken());
////            clientProxy.updateTickets(gameID, game.getPlayerbyUserName(playerID).getTickets());
////        }
//        return null;
//    }

    //
    @Override
    public Result GetTickets(String gameID, String authToken) { //TODO : need gameID
        try {

            //return the tickets to the clients
            return new Result(true, db.getTickets(gameID, authToken), null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false, null, e.getMessage());
        }
    }

    //
    @Override
    public Result ReturnTickets(String gameID, String authToken, List<Ticket> ticketsToKeep) {
        try {
            Serializer serializer = new Serializer();
            ArrayList<Ticket> ticketsToChoose2 = new ArrayList<>();
            for (Object o : ticketsToKeep) {
                ticketsToChoose2.add((Ticket) serializer.deserializeTicket(o.toString()));
            }
            System.out.println("RETRUN TICKET CALLED");
            Result result = db.setTickets(gameID, authToken, ticketsToChoose2);
            String playerID = db.findUsernameByAuthToken(authToken);
            if (result.isSuccess()) {
                for (ClientProxy clientProxy : gameIDclientProxyMap.get(gameID)) {
                    clientProxy.updateOpponentTickets(gameID, playerID, new Integer(((ArrayList<Ticket>) result.getData()).size()));
                    System.out.println("UPDATING DECK " + db.findGameByID(gameID).getTickets().size());
                    clientProxy.updateDestinationDeck(gameID, new Integer(db.findGameByID(gameID).getTickets().size()));
                }
            }
            return new Result(true, true, null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false, null, e.getMessage());
        }
    }

    //--------------------------------FOR TEST-------------------------------------------
    public Result GetLobbyList() {
        try {
            return new Result(true, db.getLobbyList(), null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Result GetLobby(String gameID) {
        try {
            if (db.findGameLobbyByID(gameID) == null) {
                return new Result(false, null, "lobby doesn't exist");
            }
            else {
                return new Result(true, db.findGameLobbyByID(gameID), null);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Result GetGameList() {
        try {
            return new Result(true, db.getGameList(), null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Result GetGame(String gameID) {
        try {
            if (db.findGameLobbyByID(gameID) == null) {
                return new Result(false, null, "game doesn't exist");
            }
            else {
                return new Result(true, db.findGameByID(gameID), null);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
