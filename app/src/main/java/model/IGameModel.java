package model;

import java.util.List;

import communication.Card;
import communication.GameColor;
import communication.GameColor;
import communication.GameMap;
import communication.Message;
import communication.Player;
import communication.Result;
import communication.Route;
import communication.Ticket;

public interface IGameModel {

    boolean isMyTurn();

    void setMyTurn(boolean isTurn);

    String getUserName();

    GameMap getMap();

    Player getPlayer();

    Result claimRoute(Route route, Player player);

    void updateTickets();

    void updateChoiceTickets();

    List<Ticket> getTickets();

    List<Ticket> getChoiceTickets();

    void chooseTickets(List<Ticket> tickets);

    void updateCards();

    void updateFaceCards();

    List<Card> getCards();

    List<Card> getFaceCards();

    /**
     * Checks if the player has enough cards of color and enough trains left to claim a route of
     * given length. Also checks to see if it is a dual route, and, if so, checks to be sure the
     * player has not claimed its sibling.
     * @param color
     * @param length
     * @return
     */
    Boolean checkRouteCanClaim(GameColor color, int length);

    /**
     * Returns the color of the selected face-up card
     * @param index
     * @return
     */
    GameColor checkCard(int index);

    void chooseCard(int index);

    int getDeckSize();

    void updateMessages();

    List<Message> getMessages();

    void sendMessage(Message message);

    List<Player> getPlayers();

    void addPoints(int p);

    void useCards(GameColor color, int number);

    void addTickets(List<Ticket> tickets);

    void removeTicket(int index);

    void updateOpponent();

}
