package communication;

/**
 * Created by jalton on 10/1/18.
 */

public interface IClient {


    void updateGameList(String gameID);

    //TODO: this functon should be replaced with function that has specific purpose, like OpponentClaimRoute()...
    void updateGame(String gameID);

    void updatePlayerPoints(String gameID, String plyerID, int points);

    void updateTrainCards(String gameID, Card trainCard);

    void updateTickets(String gameID, DestinationTicketCard destinationTicketCard);

    void updateOpponentTrainCards(String gameID, String playerID, int cardNum);

    void updateOpponentTrainCars(String gameID, String playerID, int carNum);

    void updateOpponentTickets(String gameID, String playerID, int cardNum);

    void updateTrainDeck(String gameID, Card faceCards, int downCardNum);

    void updateDestinationDeck(String gameID, int cardNum);

    void claimRoute(String gameID, String playerID, String routeID);

    void updateChat(String gameID, Message m);
}
