package communication;

import com.obfuscation.server.GenericCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jalton on 10/1/18.
 */

public class Game {
    private String mGameID;
    private String mHost;
    private int mMaxPlayers;
    private ArrayList<Player> mPlayers;
    private ArrayList<Player> mAbsentPlayers;
    private ArrayList<Message> messages;

    //Client only data member-----------------------------------------
    private int state;
    //----------------------------------------------------------------

    //Server only data member ----------------------------------------
    private HashMap<Integer, GenericCommand> stateCommandMap;

    //----------------------------------------------------------------




    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    private boolean misStarted;

    public Game(String mGameID, String mUsername, ArrayList<Player> mPlayers, int mMaxPlayers) {
        this.mGameID = mGameID;
        this.mHost = mUsername;
        this.mPlayers = mPlayers;
        this.mMaxPlayers = mMaxPlayers;
        this.mAbsentPlayers = new ArrayList<>();
        this.misStarted = false;
    }

    public String getGameID() {
        return mGameID;
    }

    public void setGameID(String mGameID) {
        this.mGameID = mGameID;
    }

    public String getHost() {
        return mHost;
    }

    public void setHost(String host) {
        this.mHost = host;
    }

    public List<Player> getPlayers() {
        return mPlayers;
    }

    public void setPlayers(ArrayList<Player> mPlayers) {
        this.mPlayers = mPlayers;
    }

    public int getPlayerCount() {
        return this.mPlayers.size();
    }

    public int getMaxPlayers() {
        return mMaxPlayers;
    }

    public void setMaxPlayers(int mMaxPlayers) {
        this.mMaxPlayers = mMaxPlayers;
    }

    public Result addPlayer(communication.Player player){
        if (misStarted) {
            if (mPlayers.contains(player)) {
                return rejoinGame(player);
            } else return new Result(false, null, "Error: game has started");
        }
        else if (mPlayers.size() < mMaxPlayers) {
            if (!mPlayers.contains(player)) {
                mPlayers.add(player);
                return new Result(true, true, null);
            }
            else return new Result(false, null, "Error: player already in game");
        }
        else return new Result(false, null, "Error: game is full");
    }

    public Result removePlayer(communication.Player player){
        if (!mPlayers.contains(player)) return new Result(false, false, "Error: Player not in game");

        if (misStarted){
            mAbsentPlayers.add(player);
        }
        else mPlayers.remove(player);
        return new Result(true, true, null);
    }

    public Result rejoinGame(communication.Player player) {
        if(!mPlayers.contains(player)) return new Result(false, null, "Error: Player not found");
        else if (mAbsentPlayers.contains(player)) {
            mAbsentPlayers.remove(player);
        }

        return new Result(true, true, null);
    }


    public void startGame(){
        misStarted = true;
    }

    public boolean isStarted() {
        return misStarted;
    }

    public String toString() {
        return "{ " + mGameID + ", " + mHost + ", " + mPlayers.size() + ", " + mMaxPlayers + " }";
    }
}
