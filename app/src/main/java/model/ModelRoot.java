package model;

import java.util.ArrayList;
import java.util.List;

import communication.Game;
import communication.Player;

/**
 * Created by hao on 10/5/18.
 */

public class ModelRoot {

    private static ModelRoot modelRoot;

    public static ModelRoot getInstance(){
        if (modelRoot == null) {
            modelRoot = new ModelRoot();
        }

        return modelRoot;
    }

    private String authToken;
    private ArrayList<Game> gameList;
    private Game game;
    private State state;
    private String userName;

    public ModelRoot() {
        this.state = State.GAMELIST;
    }

    public void setAuthToken (String s) {
        authToken = s;
    }

    public String getAuthToken () {
        return authToken;
    }

    public ArrayList<Game> getGameList() {
        if (gameList == null) {
            gameList = new ArrayList<Game>();
        }
        return gameList;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setGameList(ArrayList<Game> games) {
        this.gameList = games;
    }

    public Game getGame(){
        return game;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
