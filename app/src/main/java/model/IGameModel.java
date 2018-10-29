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

    // TODO: do you mean current user? i am gonna set it up as if it is asking for current user
    Player getPlayer();


    List<Ticket> getTickets();

    List<Message> getMessages();

}
