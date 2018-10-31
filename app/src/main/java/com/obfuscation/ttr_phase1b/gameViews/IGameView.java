package com.obfuscation.ttr_phase1b.gameViews;

import java.util.List;

import communication.Card;
import communication.GameMap;
import communication.Player;
import communication.Ticket;

/**
 * Created by jalton on 10/24/18.
 */

public interface IGameView extends IView {

    void setMap(GameMap map);

    void setCards(List<Card> cards);

    void setFaceCards(List<Card> cards);

    void setTickets(List<Ticket> tickets);

    void setUsername(String username);

    void setIsTurn(boolean isTurn);

    void setPlayer(Player player);

    void setPoints(int points);

    void setTrains(int trains);

}
