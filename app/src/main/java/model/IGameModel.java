package model;

import java.util.List;

import communication.Card;
import communication.GameMap;
import communication.Message;
import communication.Player;
import communication.Ticket;

public interface IGameModel {

    boolean isMyTurn();

    String getUserName();


    GameMap getMap();

    Player getPlayer();

    void updateTickets();

    void updateChoiceTickets();

    List<Ticket> getTickets();

    List<Ticket> getChoiceTickets();

    void chooseTickets(List<Ticket> tickets);


    void updateCards();

    void updateFaceCards();

    List<Card> getCards();

    List<Card> getFaceCards();

    void chooseCard(int index);

    int getDeckSize();

    void updateMessages();

    List<Message> getMessages();

    void sendMessage(Message message);

}
