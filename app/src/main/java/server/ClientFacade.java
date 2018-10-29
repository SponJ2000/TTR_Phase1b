package server;

import java.util.ArrayList;

import communication.Card;
import communication.DestinationTicketCard;
import communication.Game;
import communication.IClient;
import communication.Message;
import communication.Player;
import model.ModelRoot;

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
    public void updatePlayerPoints(String gameID, String plyerID, Integer points) {
        Game g = ModelRoot.getInstance().getGameByGameID(gameID);
        if (g != null) {
            Player p = g.getPlayerbyID(plyerID);
            if (p != null) {
                p.setPoint(points);
            }
        }
    }

    @Override
    public void updateTrainCards(String gameID, Card trainCard) {

    }

    @Override
    public void updateTickets(String gameID, DestinationTicketCard destinationTicketCard) {

    }

    @Override
    public void updateOpponentTrainCards(String gameID, String playerID, Integer cardNum) {

    }

    @Override
    public void updateOpponentTrainCars(String gameID, String playerID, Integer carNum) {
        Game g = ModelRoot.getInstance().getGameByGameID(gameID);
        if (g != null) {
            Player p = g.getPlayerbyID(playerID);
            if (p != null) {
                p.setTrainCarNum(carNum);
            }
        }
    }

    @Override
    public void updateOpponentTickets(String gameID, String playerID, Integer cardNum) {
        Game g = ModelRoot.getInstance().getGameByGameID(gameID);
        if (g != null) {
            Player p = g.getPlayerbyID(playerID);
            if (p != null) {
                p.setTicketNum(cardNum);
            }
        }
    }

    @Override
    public void updateTrainDeck(String gameID, ArrayList<Card> faceCards, Integer downCardNum) {
        Game g = ModelRoot.getInstance().getGameByGameID(gameID);
        if (g != null) {
            g.setFaceUpTrainCarCards(faceCards);
        }
    }

    @Override
    public void updateDestinationDeck(String gameID, Integer cardNum) {
        Game g = ModelRoot.getInstance().getGameByGameID(gameID);
        if (g != null) {

        }
    }

    @Override
    public void claimRoute(String gameID, String playerID, String routeID) {
        Game g = ModelRoot.getInstance().getGameByGameID(gameID);
        if (g != null) {
            g.getPlayerbyID(playerID).addRouteAsClaimed(routeID);
        }
    }

    @Override
    public void updateChat(String gameID, Message m) {
        Game g = ModelRoot.getInstance().getGameByGameID(gameID);
        if (g != null) {
            g.insertMessage(m);
        }
    }
}
