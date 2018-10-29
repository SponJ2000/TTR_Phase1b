package communication;

import java.util.ArrayList;

/**
 * Created by jalton on 10/1/18.
 */

public interface IClient {


    void updateGameList(String gameID);

    //TODO: this functon should be replaced with function that has specific purpose, like OpponentClaimRoute()...
    void updateGame(String gameID);

    void updatePlayerPoints(String gameID, String plyerID, Integer points);

    //TODO: what dose this suppose to do? what do we want to do this card passed in as a parameter?
    void updateTrainCards(String gameID, Card trainCard);

    void updateTickets(String gameID, DestinationTicketCard destinationTicketCard);

    void updateOpponentTrainCards(String gameID, String playerID, Integer cardNum);

    void updateOpponentTrainCars(String gameID, String playerID, Integer carNum);

    void updateOpponentTickets(String gameID, String playerID, Integer cardNum);

    void updateTrainDeck(String gameID, ArrayList<Card> faceCards, Integer downCardNum);

    void updateDestinationDeck(String gameID, Integer cardNum);

    void claimRoute(String gameID, String playerID, String routeID);

    // todo: are you planning on just appending a message into the chat? I am doing it assuming you are append a message
    void updateChat(String gameID, Message m);
}
