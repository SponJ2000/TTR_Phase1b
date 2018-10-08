package com.obfuscation.server;

import java.util.HashMap;
import java.util.Map;

import communication.IClient;
import communication.Result;

/**
 * Created by jalton on 10/1/18.
 */

public class ClientProxy implements IClient {

    int version;
    Map<String, Integer> gameVersion;

    private static ClientProxy instance = null;

    public static ClientProxy getInstance(){
        if (instance == null) {
            instance = new ClientProxy();
        }

        return instance;
    }

    private ClientProxy() {
        version = 0;
        gameVersion = new HashMap<>();
    }

    @Override
    public void updateGameList(String gameID) {
        version++;
        if (gameID != null) gameVersion.put(gameID, 0);
    }

    @Override
    public void updateGame(String gameID) {
        int v = gameVersion.get(gameID);
        v += 1;
        gameVersion.put(gameID, v);
    }

    public Result checkUpdates(String gameID){
        if (gameID == null) {
            return new Result(true, version, null);
        }
        else {
            if (gameVersion.get(gameID) == null) {
                return new Result(false, null, "Error: Game not found");
            }
            else return new Result(true, gameVersion.get(gameID), null);
        }
    }
}
