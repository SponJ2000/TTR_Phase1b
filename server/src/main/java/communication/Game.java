package communication;

import com.obfuscation.server.GenericCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by jalton on 10/1/18.
 */

public class Game {
    //shared --------------------------------
    private String gameID;
    private ArrayList<Message> messages;
    private ArrayList<Card> faceUpTrainCarCards;
    private GameMap mMap;

    public Game() {
        gameID = UUID.randomUUID().toString();
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String mGameID) {
        this.gameID = mGameID;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public ArrayList<Card> getFaceUpTrainCarCards() {
        return faceUpTrainCarCards;
    }

    public void setFaceUpTrainCarCards(ArrayList<Card> faceUpTrainCarCards) {
        this.faceUpTrainCarCards = faceUpTrainCarCards;
    }

    public GameMap getmMap() {
        return mMap;
    }

    public void setmMap(GameMap mMap) {
        this.mMap = mMap;
    }

    public void claimRoute(Route route, Player player) {
        mMap.claimRoute(route, player);
    }
    public void insertMessage(Message m) {
        messages.add(m);
    }




}
