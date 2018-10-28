package com.obfuscation.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import communication.Card;
import communication.DestinationTicketCard;
import communication.IClient;
import communication.ICommand;
import communication.Message;
import communication.Result;

/**
 * Created by jalton on 10/1/18.
 */

public class ClientProxy implements IClient {
    private Map<GamePlayerPair, List<ICommand>> notSeenCommands = new HashMap();
    private static final String CLIENT_FACADE = "ClientFacade";

    @Override
    public void updatePlayerPoints(String gameID, String plyerID, int points) {

    }

    @Override
    public void updateTrainCards(String gameID, Card trainCard) {

    }

    @Override
    public void updateTickets(String gameID, DestinationTicketCard destinationTicketCard) {

    }

    @Override
    public void updateOpponentTrainCards(String gameID, String playerID, int cardNum) {

    }

    @Override
    public void updateOpponentTrainCars(String gameID, String playerID, int carNum) {

    }

    @Override
    public void updateOpponentTickets(String gameID, String playerID, int cardNum) {

    }

    @Override
    public void updateTrainDeck(String gameID, Card faceCards, int downCardNum) {

    }

    @Override
    public void updateDestinationDeck(String gameID, int cardNum) {

    }

    @Override
    public void claimRoute(String gameID, String playerID, String routeID) {

    }

    @Override
    public void updateChat(String gameID, Message m) {

    }

    private int version;
    private Map<String, Integer> gameVersion;

    private static ClientProxy instance = null;

    public static ClientProxy getInstance(){
        if (instance == null) {
            instance = new ClientProxy();
        }

        return instance;
    }

    private ClientProxy() {
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
