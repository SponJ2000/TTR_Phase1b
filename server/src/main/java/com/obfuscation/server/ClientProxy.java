package com.obfuscation.server;

import java.util.ArrayList;
import java.util.List;

import communication.Game;
import communication.IClient;
import communication.Result;
import communication.Update;

/**
 * Created by jalton on 10/1/18.
 */

public class ClientProxy implements IClient {

    boolean updates;
    List<String> gameList;

    private static ClientProxy instance = null;

    public static ClientProxy getInstance(){
        if (instance == null) {
            instance = new ClientProxy();
        }

        return instance;
    }

    private ClientProxy() {
        updates = false;
        gameList = new ArrayList<>();
    }

    @Override
    public void updateGameList() {
        updates = true;
    }

    @Override
    public void updatePlayerList(String gameID) {
        updates = true;
        gameList.add(gameID);
    }

    public Result checkUpdates(){
        if(updates) {
            Update update = new Update(updates, gameList);
            updates = false;
            gameList = new ArrayList<>();
            return new Result(true, update, null);
        }
        Update update = new Update(updates, null);
        return new Result(true, update, null);
    }
}
