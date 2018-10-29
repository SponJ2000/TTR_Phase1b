package model;

import java.util.List;

import communication.Card;
import communication.GameMap;
import communication.Message;
import communication.Player;
import communication.Ticket;

public interface IGameModel {

    boolean isMyTurn();

    List<Card> getCards();

    GameMap getMap();

//  get the current user
    Player getPlayer();

    List<Ticket> getTickets();

    List<Ticket> getTicketsToChoose();

    List<Message> getMessages();

    void sendMessage(Message message);

    void chooseTickets(List<Ticket> tickets);

    void chooseCard(int index);

}
