package com.obfuscation.ttr_phase1b.gameViews;

import java.util.List;

import communication.Card;
import communication.GameMap;

/**
 * Created by jalton on 10/24/18.
 */

public interface IGameView extends IView {

    void setMap(GameMap map);

    void setCards(List<Card> cards);

    void setUsername(String username);

}
