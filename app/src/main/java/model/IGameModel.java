package model;

import java.util.List;

import communication.Card;
import communication.GameMap;
import communication.Message;
import communication.Ticket;

public interface IGameModel {

    boolean isMyTurn();

    String getUserName();


    GameMap getMap();


    List<Ticket> getTickets();

    List<Ticket> getTicketsToChoose();

    void chooseTickets(List<Ticket> tickets);


    List<Card> getCards();

    List<Card> getFaceCards();

    void chooseCard(int index);


    List<Message> getMessages();

    void sendMessage(Message message);

}
