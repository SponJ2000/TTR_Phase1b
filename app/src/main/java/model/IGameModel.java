package model;

import java.util.List;

import communication.Card;
import communication.GameMap;
import communication.Message;
import communication.Player;
import communication.Ticket;

public interface IGameModel {

    List<Card> getCards();

    GameMap getMap();

    Player getPlayer();

    List<Ticket> getTickets();

    List<Message> getMessages();

}
