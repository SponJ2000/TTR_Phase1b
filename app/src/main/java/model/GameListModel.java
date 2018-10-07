package model;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by hao on 10/5/18.
 */

public class GameListModel extends Observable {

    ArrayList<Game> games;

    public GameListModel() {
        this.games = new ArrayList<Game>();
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
        setChanged();
        notifyObservers();
    }


}
