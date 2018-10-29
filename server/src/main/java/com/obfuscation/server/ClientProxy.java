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

    /**
     * private member and class name strings
     */
    private Map<GamePlayerPair, List<ICommand>> notSeenCommands = new HashMap();
    private static final String CLIENT_FACADE = "ClientFacade";
    private static final String STRING = "java.lang.String";
    private static final String INTEGER = Integer.TYPE.getName();
    private static final String CARD = Card.class.getName();
    private static final String TICKET = Ticket.class.getName();
    private static final String MESSAGE = Message.class.getName();
    private static final String GAME = Game.class.getName();

    /**
     * getters and setters
     * @return
     */
    public Map<GamePlayerPair, List<ICommand>> getNotSeenCommands() {
        return notSeenCommands;
    }

    public void setNotSeenCommands(Map<GamePlayerPair, List<ICommand>> notSeenCommands) {
        this.notSeenCommands = notSeenCommands;
    }

    /**
     * make key at the start of the game
     * @param gameID
     * @param playerID
     */
    public void insertKey(String gameID, String playerID) {
        GamePlayerPair gamePlayerPair = new GamePlayerPair(gameID, playerID);
        notSeenCommands.put(gamePlayerPair, new ArrayList<ICommand>());
    }

    @Override
    public void initializeGame(Game game) {
        for (Player player : game.getPlayers()) {
            GamePlayerPair gamePlayerPair = new GamePlayerPair(game.getGameID(), player.getId());
            ICommand command = new GenericCommand(
                    CLIENT_FACADE
                    , "initializeGame"
                    , new String[]{GAME}
                    , new Object[] {game});
            notSeenCommands.get(gamePlayerPair).add(command);
        }
    }

    @Override
    public void updatePlayerPoints(String gameID, String plyerID, Integer points) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updatePlayerPoints"
                , new String[]{STRING, STRING, INTEGER}
                , new Object[] {gameID, plyerID, points});
        for (GamePlayerPair gamePlayerPair : notSeenCommands.keySet()) {
            if (gamePlayerPair.getGameID().equals(gameID)) {
                notSeenCommands.get(gamePlayerPair).add(command);
            }
        }
    }

    @Override
    public void updateTrainCards(String gameID, Card trainCard) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateTrainCards"
                , new String[]{STRING, CARD}
                , new Object[] {gameID, trainCard});
        for (GamePlayerPair gamePlayerPair : notSeenCommands.keySet()) {
            if (gamePlayerPair.getGameID().equals(gameID)) {
                notSeenCommands.get(gamePlayerPair).add(command);
            }
        }
    }

    @Override
    public void updateTickets(String gameID, List<Ticket> tickets) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateTickets"
                , new String[]{STRING, TICKET}
                , new Object[] {gameID, tickets});
        for (GamePlayerPair gamePlayerPair : notSeenCommands.keySet()) {
            if (gamePlayerPair.getGameID().equals(gameID)) {
                notSeenCommands.get(gamePlayerPair).add(command);
            }
        }
    }

    @Override
    public void updateOpponentTrainCards(String gameID, String playerID, int cardNum) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateOpponentTrainCards"
                , new String[]{STRING, STRING, INTEGER}
                , new Object[] {gameID, playerID, cardNum});
        for (GamePlayerPair gamePlayerPair : notSeenCommands.keySet()) {
            if (gamePlayerPair.getGameID().equals(gameID)) {
                notSeenCommands.get(gamePlayerPair).add(command);
            }
        }
    }

    @Override
    public void updateOpponentTrainCars(String gameID, String playerID, Integer carNum) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateOpponentTrainCars"
                , new String[]{STRING, STRING, INTEGER}
                , new Object[] {gameID, playerID, carNum});
        for (GamePlayerPair gamePlayerPair : notSeenCommands.keySet()) {
            if (gamePlayerPair.getGameID().equals(gameID)) {
                notSeenCommands.get(gamePlayerPair).add(command);
            }
        }
    }

    @Override
    public void updateOpponentTickets(String gameID, String playerID, Integer cardNum) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateOpponentTickets"
                , new String[]{STRING, STRING, INTEGER}
                , new Object[] {gameID, playerID, cardNum});
        for (GamePlayerPair gamePlayerPair : notSeenCommands.keySet()) {
            if (gamePlayerPair.getGameID().equals(gameID)) {
                notSeenCommands.get(gamePlayerPair).add(command);
            }
        }
    }

    @Override
    public void updateTrainDeck(String gameID, Card faceCards, Integer downCardNum) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateTrainDeck"
                , new String[]{STRING, CARD, INTEGER}
                , new Object[] {gameID, faceCards, downCardNum});
        for (GamePlayerPair gamePlayerPair : notSeenCommands.keySet()) {
            if (gamePlayerPair.getGameID().equals(gameID)) {
                notSeenCommands.get(gamePlayerPair).add(command);
            }
        }
    }

    @Override
    public void updateDestinationDeck(String gameID, Integer cardNum) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateDestinationDeck"
                , new String[]{STRING, INTEGER}
                , new Object[] {gameID, cardNum});
        for (GamePlayerPair gamePlayerPair : notSeenCommands.keySet()) {
            if (gamePlayerPair.getGameID().equals(gameID)) {
                notSeenCommands.get(gamePlayerPair).add(command);
            }
        }
    }

    @Override
    public void claimRoute(String gameID, String playerID, String routeID) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateDestinationDeck"
                , new String[]{STRING, STRING, STRING}
                , new Object[] {gameID, playerID, routeID});
        for (GamePlayerPair gamePlayerPair : notSeenCommands.keySet()) {
            if (gamePlayerPair.getGameID().equals(gameID)) {
                notSeenCommands.get(gamePlayerPair).add(command);
            }
        }
    }

    @Override
    public void updateChat(String gameID, Message m) {
        ICommand command = new GenericCommand(
                CLIENT_FACADE
                , "updateChat"
                , new String[]{STRING, MESSAGE}
                , new Object[] {gameID, m});
        for (GamePlayerPair gamePlayerPair : notSeenCommands.keySet()) {
            if (gamePlayerPair.getGameID().equals(gameID)) {
                notSeenCommands.get(gamePlayerPair).add(command);
            }
        }
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

    /**
     * The client gets the list of commands through this function. After this has been executed, clears the command list.
     * TODO : better to have authToken?
     * @param gameID
     * @param playerID
     * @return
     */
    public Result getNotSeenCommands(String gameID, String playerID) {
        GamePlayerPair gamePlayerPair = new GamePlayerPair(gameID, playerID);
        if (notSeenCommands.containsKey(gamePlayerPair)) {
            if (notSeenCommands.get(gamePlayerPair) != null) {
                Result result = new Result(true, notSeenCommands.get(gamePlayerPair), null);
                notSeenCommands.get(gamePlayerPair).clear();
                return result;
            }
        }
        return new Result(false, null, "Error : gamePlayerPair not found");
    }


    /**
     * A helper function that allows us to use gameID and playerID as key.
     */
    public class GamePlayerPair {
        String gameID;
        String playerID;

        public String getGameID() {
            return gameID;
        }

        public void setGameID(String gameID) {
            this.gameID = gameID;
        }

        public String getPlayerID() {
            return playerID;
        }

        public void setPlayerID(String playerID) {
            this.playerID = playerID;
        }

        public GamePlayerPair(String gameID, String playerID) {

            this.gameID = gameID;
            this.playerID = playerID;
        }
    }
}
