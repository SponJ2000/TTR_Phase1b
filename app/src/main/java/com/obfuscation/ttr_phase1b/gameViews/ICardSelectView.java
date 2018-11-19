package com.obfuscation.ttr_phase1b.gameViews;

import java.util.List;

/**
 * Created by jalton on 11/19/18.
 */

public interface ICardSelectView extends IView {

    /**
     * Sets the hand of cards for the player
     * @param hand a list of ints for the hand
     */
    void setHand(int[] hand);

    void setCardsToSelect(int cardsToSelect);

}
