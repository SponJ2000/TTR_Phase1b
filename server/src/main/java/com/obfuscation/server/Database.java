package com.obfuscation.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import communication.ActiveUser;
import communication.Game;
import communication.Player;
import communication.Result;

/**
 * Created by jalton on 10/3/18.
 */

public class Database {

    /*
    We will need:
    Game list (game id, players, etc)
    Login info database
    operations to:
    register user
    login
    new game
    join game
    leave game
    started games
    rejoin
     */

    Map<String, String> loginInfo;
    List<Game> gameList;
    List<ActiveUser> activeUsers;

    private static Database db = new Database();

    public static Database getInstance() {
        return db;
    }

    private Database() {
        loginInfo = new HashMap<>();
        gameList = new ArrayList<>();
        activeUsers = new ArrayList<>();
    }

    Result register(String id, String password){
        if(!loginInfo.containsKey(id)){
            //Add user and password
            loginInfo.put(id, password);

            //create authToken, use UUID.randomUUID().toString()
            String authToken = "1234";

            //return result
            return new Result(true, authToken, null);
        }
        else{
            return new Result(false, null, "Error: Username already exists");
        }
    }

    Result login(String id, String password){
        if(loginInfo.containsKey(id)){
            if(loginInfo.get(id).equals(password)){
                //Generate authToken
                String authToken = "1234";

                //Create and return result object
                return new Result(true, authToken, null);
            }
            else{
                return new Result(false, null, "Error: Invalid password");
            }
        }
        else {
            return new Result(false, null, "Error: Invalid username");
        }
    }

    Result newGame(Game game){

        if(game.getMaxPlayers() < 2 || game.getMaxPlayers() > 5){
            return new Result(false, null, "Error: Invalid max players");
        }

        //Generate gameID
        String gameID = "G1234";

        game.setGameID(gameID);

        gameList.add(game);

        return new Result(true, gameList, null);
    }


}
