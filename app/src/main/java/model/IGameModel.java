package model;

import java.util.List;

import communication.Card;
import communication.DestinationTicketCard;
import communication.GameMap;
import communication.Message;
import communication.Player;
import communication.Ticket;
import communication.TrainCarCard;

public interface IGameModel {


    List<Card> getCards();

    GameMap getMap();

//  get the current user
    Player getPlayer();


    List<Ticket> getTickets();

    List<Message> getMessages();

    void sendMessage(Message message);

    void chooseTickets(List<Ticket> tickets);

    void chooseCard(int index);

}
