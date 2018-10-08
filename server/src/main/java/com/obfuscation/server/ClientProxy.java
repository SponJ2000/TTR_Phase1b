package com.obfuscation.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import communication.Game;
import communication.IClient;
import communication.Result;
import communication.Update;

/**
 * Created by jalton on 10/1/18.
 */

public class ClientProxy implements IClient {

    int version;
    Map<String, Integer> gameList;

    private static ClientProxy instance = null;

    public static ClientProxy getInstance(){
        if (instance == null) {
            instance = new ClientProxy();
        }

        return instance;
    }

    private ClientProxy() {
        version = 0;
        gameList = new HashMap<>();
    }

    @Override
    public void updateGameList(String gameID) {
        version++;
        if (gameID != null) gameList.put(gameID, 0);
    }

    @Override
    public void updateGame(String gameID) {
        int v = gameList.get(gameID);
        v += 1;
        gameList.put(gameID, v);
    }

    public Result checkUpdates(String gameID){
        if (gameID == null) {
            return new Result(true, version, null);
        }
        else {
            if (gameList.get(gameID) == null) {
                return new Result(false, null, "Error: Game not found");
            }
            else return new Result(true, gameList.get(gameID), null);
        }
    }
}
