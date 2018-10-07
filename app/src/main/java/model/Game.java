package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import communication.Result;
import communication.Player;

public class Game extends Observable{

    private String mGameID;
    private String mUsername;
    private int mMaxPlayers;
    private List<Player> mPlayers;
    private List<Player> mAbsentPlayers;

    private boolean misStarted;

    public Game(String mGameID, String mUsername, List<Player> mPlayers, int mMaxPlayers) {
        this.mGameID = mGameID;
        this.mUsername = mUsername;
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

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public List<Player> getPlayers() {
        return mPlayers;
    }

    public void setPlayers(List<Player> mPlayers) {
        this.mPlayers = mPlayers;
        setChanged();
        notifyObservers();
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
        if (mPlayers.size() < mMaxPlayers) {
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
        return "{ " + mGameID + ", " + mUsername + ", " + mPlayers.size() + ", " + mMaxPlayers + " }";
    }



}