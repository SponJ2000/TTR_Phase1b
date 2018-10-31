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
        version = 0;
        gameVersion = new HashMap<>();
        this.authToken = authToken;
    }

    /**
     * private member and class name strings
     */
    private Map<String, List<ICommand>> notSeenCommands = new HashMap<>();
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

    @Override
    public void initializeGame(Game game) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "initializeGame"
                , new String[]{GAME}
                , new Object[] {game});
        notSeenCommands.put(game.getGameID(), new ArrayList<ICommand>());
        notSeenCommands.get(game.getGameID()).add(command);
    }

    @Override
    public void updatePlayerPoints(String gameID, String plyerID, Integer points) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updatePlayerPoints"
                , new String[]{STRING, STRING, INTEGER}
                , new Object[] {gameID, plyerID, points});
        notSeenCommands.get(gameID).add(command);
    }

    @Override
    public void updateTrainCards(String gameID, List<Card> trainCards) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateTrainCards"
                , new String[]{STRING, CARD}
                , new Object[] {gameID, trainCards});
        notSeenCommands.get(gameID).add(command);
    }

    @Override
    public void updateTickets(String gameID, List<Ticket> tickets) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateTickets"
                , new String[]{STRING, LIST}
                , new Object[] {gameID, tickets});
        notSeenCommands.get(gameID).add(command);
    }

    @Override
    public void updateOpponentTrainCards(String gameID, String playerID, Integer cardNum) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateOpponentTrainCards"
                , new String[]{STRING, STRING, INTEGER}
                , new Object[] {gameID, playerID, cardNum});
        notSeenCommands.get(gameID).add(command);
    }

    @Override
    public void updateOpponentTrainCars(String gameID, String playerID, Integer carNum) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateOpponentTrainCars"
                , new String[]{STRING, STRING, INTEGER}
                , new Object[] {gameID, playerID, carNum});
        notSeenCommands.get(gameID).add(command);
    }

    @Override
    public void updateOpponentTickets(String gameID, String playerID, Integer cardNum) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateOpponentTickets"
                , new String[]{STRING, STRING, INTEGER}
                , new Object[] {gameID, playerID, cardNum});
        notSeenCommands.get(gameID).add(command);
    }

    @Override
    public void updateTrainDeck(String gameID, ArrayList<Card> faceCards, Integer downCardNum) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateTrainDeck"
                , new String[]{STRING, CARD, INTEGER}
                , new Object[] {gameID, faceCards, downCardNum});
        notSeenCommands.get(gameID).add(command);
    }

    @Override
    public void updateDestinationDeck(String gameID, Integer cardNum) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateDestinationDeck"
                , new String[]{STRING, INTEGER}
                , new Object[] {gameID, cardNum});
        notSeenCommands.get(gameID).add(command);
    }

    @Override
    public void claimRoute(String gameID, String playerID, String routeID) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateDestinationDeck"
                , new String[]{STRING, STRING, STRING}
                , new Object[] {gameID, playerID, routeID});
        notSeenCommands.get(gameID).add(command);
    }

    @Override
    public void updateChat(String gameID, Message m) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateChat"
                , new String[]{STRING, MESSAGE}
                , new Object[] {gameID, m});
        notSeenCommands.get(gameID).add(command);
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
    //TODO : clients have to keep track of games and the last command id, and send them (keep map<gameID, commandID>)
    public Result getNotSeenCommands(String gameID, String lastCommandID) {
        if (notSeenCommands != null) {
            if (notSeenCommands.get(gameID) != null) {
                //first time
                if (lastCommandID == null) {
                    return new Result(true, notSeenCommands.get(gameID), null);
                }
                int index = -1;
                for (int i = 0; i < notSeenCommands.get(gameID).size(); i++) {
                    ICommand command = notSeenCommands.get(gameID).get(i);
                    GenericCommand genericCommand = (GenericCommand) command;
                    if (lastCommandID.equals(genericCommand.getCommandID())) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {

                    //removes the previous commands that has been transmitted
                    notSeenCommands.get(gameID).subList(0, index).clear();
                    return new Result(true, notSeenCommands.get(gameID), null);
                }
            }
        }
        return new Result(false, null, "Error : commands are null");
    }
}
