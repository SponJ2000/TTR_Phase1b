package model;

import com.google.gson.Gson;

import java.util.ArrayList;

import communication.DestinationTickectCardMaker;
import communication.DestinationTicketCard;

/**
 * Created by hao on 10/27/18.
 */

public class TestingDummy {

    public static void main(String[] argv) {
        DestinationTickectCardMaker cardMaker = new DestinationTickectCardMaker();

        ArrayList<DestinationTicketCard> cardDeck = cardMaker.MakeCards();

        System.out.println(cardDeck.size());
    }
}
