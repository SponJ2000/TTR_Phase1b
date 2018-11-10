package model;

import java.util.ArrayList;

import communication.GameClient;
import communication.GameLobby;
import communication.Ticket;

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
    private ArrayList<GameLobby> gameLobbies;
    private GameClient game;
    private GameLobby gameLobby;
    private DisplayState displayState;
    private String userName;

    private ArrayList<Ticket> ticketsWanted;

    public ModelRoot() {
        this.displayState = DisplayState.GAMELOBBYLIST;
    }

    public void setAuthToken (String s) {
        authToken = s;
    }

    public String getAuthToken () {
        return authToken;
    }

    public ArrayList<GameLobby> gameLobbies() {
        if (gameLobbies == null) {
            gameLobbies = new ArrayList<GameLobby>();
        }
        return gameLobbies;
    }

    public void setGame(GameClient game) {
        this.game = game;
    }

    public void setGame(GameLobby gameLobby) {
        GameClient gameClient = new GameClient(gameLobby.getGameID(),userName);
    }

    public ArrayList<GameLobby> getGameLobbies() {
        return gameLobbies;
    }

    public void setGameLobbies(ArrayList<GameLobby> gameLobbies) {
        this.gameLobbies = gameLobbies;
    }

    public GameClient getGame(){
        return game;
    }

    public GameLobby getGameLobbyById(String id) {
        for (GameLobby gl: gameLobbies) {
            if (gl.getGameID().equals(id)) {
                return gl;
            }
        }
        return null;
    }
    public DisplayState getDisplayState() {
        return displayState;
    }

    public void setDisplayState(DisplayState displayState) {
        this.displayState = displayState;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Ticket> getTicketsWanted() {
        if (ticketsWanted == null) {
            ticketsWanted = new ArrayList<Ticket>();
        }
        return ticketsWanted;
    }

    public void setTicketsWanted(ArrayList<Ticket> ticketsWanted) {
        this.ticketsWanted = ticketsWanted;
    }

    public GameLobby getGameLobby() {
        return gameLobby;
    }

    public void setGameLobby(GameLobby gameLobby) {
        this.gameLobby = gameLobby;
    }
}
