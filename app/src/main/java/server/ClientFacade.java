package server;

import communication.Card;
import communication.DestinationTicketCard;
import communication.IClient;
import communication.Message;

/**
 * Created by hao on 10/25/18.
 */

public class ClientFacade implements IClient{
    @Override
    public void updateGameList(String gameID) {

    }

    @Override
    public void updateGame(String gameID) {

    }

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
}
