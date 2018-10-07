package model;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Game extends Observable {

    private String mGameID;
    private String mUsername;
    private int mMaxPlayers;
    private List<Player> mPlayers;

    public Game() {
        this.mGameID = null;
        this.mUsername = null;
        this.mPlayers = null;
        this.mMaxPlayers = 0;
    }

    public Game(String mGameID, String mUsername, List<Player> mPlayers, int mMaxPlayers) {
        this.mGameID = mGameID;
        this.mUsername = mUsername;
        this.mPlayers = mPlayers;
        this.mMaxPlayers = mMaxPlayers;
    }

    public String getGameID() {
        return mGameID;
    }

    public void setGameID(String mGameID) {
        this.mGameID = mGameID;

        setChanged();
        notifyObservers();
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
        setChanged();
        notifyObservers();
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
        setChanged();
        notifyObservers();
    }

    public String toString() {
        return "{ " + mGameID + ", " + mUsername + ", " + mPlayers.size() + ", " + mMaxPlayers + " }";
    }



}
