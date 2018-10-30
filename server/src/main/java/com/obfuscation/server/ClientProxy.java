package com.obfuscation.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import communication.Card;
import communication.DestinationTicketCard;
import communication.Game;
import communication.IClient;
import communication.ICommand;
import communication.Message;
import communication.Player;
import communication.Result;
import communication.Ticket;

/**
 * Created by jalton on 10/1/18.
 */

public class ClientProxy implements IClient {
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public ClientProxy(String authToken) {
        this.authToken = authToken;
    }

    /**
     * private member and class name strings
     */
    private List<ICommand> notSeenCommands = new ArrayList<>();
    private static final String CLIENT_FACADE = "ClientFacade";
    private static final String STRING = "java.lang.String";
    private static final String INTEGER = Integer.TYPE.getName();
    private static final String CARD = Card.class.getName();
    private static final String TICKET = Ticket.class.getName();
    private static final String MESSAGE = Message.class.getName();
    private static final String GAME = Game.class.getName();
    private static final String LIST = List.class.getName();

    /**
     * getters and setters
     * @return
     */
    public List<ICommand> getNotSeenCommands() {
        return notSeenCommands;
    }

    public void setNotSeenCommands(List<ICommand> notSeenCommands) {
        this.notSeenCommands = notSeenCommands;
    }

    @Override
    public void initializeGame(Game game) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "initializeGame"
                , new String[]{GAME}
                , new Object[] {game});
        notSeenCommands.add(command);
    }

    @Override
    public void updatePlayerPoints(String gameID, String plyerID, Integer points) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updatePlayerPoints"
                , new String[]{STRING, STRING, INTEGER}
                , new Object[] {gameID, plyerID, points});
        notSeenCommands.add(command);
    }

    @Override
    public void updateTrainCards(String gameID, List<Card> trainCards) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateTrainCards"
                , new String[]{STRING, CARD}
                , new Object[] {gameID, trainCards});
        notSeenCommands.add(command);
    }

    @Override
    public void updateTickets(String gameID, List<Ticket> tickets) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateTickets"
                , new String[]{STRING, LIST}
                , new Object[] {gameID, tickets});
        notSeenCommands.add(command);
    }

    @Override
    public void updateOpponentTrainCards(String gameID, String playerID, Integer cardNum) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateOpponentTrainCards"
                , new String[]{STRING, STRING, INTEGER}
                , new Object[] {gameID, playerID, cardNum});
        notSeenCommands.add(command);
    }

    @Override
    public void updateOpponentTrainCars(String gameID, String playerID, Integer carNum) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateOpponentTrainCars"
                , new String[]{STRING, STRING, INTEGER}
                , new Object[] {gameID, playerID, carNum});
        notSeenCommands.add(command);
    }

    @Override
    public void updateOpponentTickets(String gameID, String playerID, Integer cardNum) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateOpponentTickets"
                , new String[]{STRING, STRING, INTEGER}
                , new Object[] {gameID, playerID, cardNum});
        notSeenCommands.add(command);
    }

    @Override
    public void updateTrainDeck(String gameID, ArrayList<Card> faceCards, Integer downCardNum) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateTrainDeck"
                , new String[]{STRING, CARD, INTEGER}
                , new Object[] {gameID, faceCards, downCardNum});
        notSeenCommands.add(command);
    }

    @Override
    public void updateDestinationDeck(String gameID, Integer cardNum) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateDestinationDeck"
                , new String[]{STRING, INTEGER}
                , new Object[] {gameID, cardNum});
        notSeenCommands.add(command);
    }

    @Override
    public void claimRoute(String gameID, String playerID, String routeID) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateDestinationDeck"
                , new String[]{STRING, STRING, STRING}
                , new Object[] {gameID, playerID, routeID});
        notSeenCommands.add(command);
    }

    @Override
    public void updateChat(String gameID, Message m) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateChat"
                , new String[]{STRING, MESSAGE}
                , new Object[] {gameID, m});
        notSeenCommands.add(command);
    }

    private int version;
    private Map<String, Integer> gameVersion;

    public ClientProxy() {
        version = 0;
        gameVersion = new HashMap<>();
    }

    @Override
    public void updateGameList(String gameID) {
        version++;
        if (gameID != null) gameVersion.put(gameID, 0);
    }

    @Override
    public void updateGame(String gameID) {
        int v = gameVersion.get(gameID);
        v += 1;
        gameVersion.put(gameID, v);
    }

    public Result checkUpdates(String gameID){
        if (gameID == null) {
            return new Result(true, version, null);
        }
        else {
            if (gameVersion.get(gameID) == null) {
                return new Result(false, null, "Error: Game not found");
            }
            else return new Result(true, gameVersion.get(gameID), null);
        }
    }
    //TODO : when the game ends, clear the commands list and erase the key


    //TODO : provide a way to check if commands are transmitted successfully or not
    //TODO : get different commands for different games
    public Result getNotSeenCommands(String gameID) {
        if (notSeenCommands != null) {
            return new Result(true, notSeenCommands, null);
        }
        return new Result(false, null, "Error : gamePlayerPair not found");
    }
}
