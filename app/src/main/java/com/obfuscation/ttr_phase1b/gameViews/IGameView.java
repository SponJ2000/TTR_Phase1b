package com.obfuscation.ttr_phase1b.gameViews;

import java.util.List;

import communication.Card;
import communication.GameMap;
import communication.Player;

/**
 * Created by jalton on 10/24/18.
 */

public interface IGameView {
    void UpdateUI();

    void setMap(GameMap map);

    void setCards(List<Card> cards);

    void setPlayer(Player player);
}
