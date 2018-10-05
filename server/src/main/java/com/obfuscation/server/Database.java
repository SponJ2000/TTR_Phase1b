package com.obfuscation.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    List<String> authTokens;

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

            return login(id, password);
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

                //Add Player to Active Users
                Player player = new Player(generateID(true), id);
                ActiveUser user = new ActiveUser(player, authToken);
                activeUsers.add(user);

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

    Result newGame(Game game, String authToken){

        if(!chechAuthToken(authToken)) return new Result(false, null, "Error: Invalid Token");

        if(game.getMaxPlayers() < 2 || game.getMaxPlayers() > 5){
            return new Result(false, null, "Error: Invalid max players");
        }

        //Generate gameID
        String gameID = generateID(false);

        game.setGameID(gameID);

        gameList.add(game);

        return new Result(true, gameList, null);
    }

    Result joinGame(String playerID, String gameID) {

        Player player = findPlayerByID(playerID);
        Game game = findGameByID(gameID);

        if(player == null || game == null) {
            return new Result(false, null, "Error: Could not join game");
        }

        return game.addPlayer(player);
    }

    Result startGame(String gameID, String authToken) {

        if(!chechAuthToken(authToken)){
            return new Result(false, null, "Error: Invalid token");
        }

        Game game = findGameByID(gameID);
        if (game == null) return new Result(false, null, "Error: game not found");

        game.startGame();
        List<Player> players = game.getPlayers();
        for (Player player : players) {
            ActiveUser user = findUserByPlayer(player);
            if (user == null) return new Result(false, null, "Error: User not found");

            user.getJoinedGames().add(gameID);
        }

        return new Result(true, true, null);
    }

    Result leaveGame(String gameID, String playerID) {
        Player player = findPlayerByID(playerID);
        Game game = findGameByID(gameID);

        if(player == null || game == null) {
            return new Result(false, null, "Error: Could not leave game");
        }

        return game.removePlayer(player);

    }

    Result rejoinGame(String gameID, String playerID) {
        Player player = findPlayerByID(playerID);
        Game game = findGameByID(gameID);

        if(player == null || game == null) {
            return new Result(false, null, "Error: Could not rejoin game");
        }

        return game.rejoinGame(player);
    }



    Player findPlayerByID(String id){
        for (ActiveUser user : activeUsers) {
            if (user.getPlayer().getPlayerName().equals(id))
                    return user.getPlayer();
        }

        return null;
    }

    Game findGameByID(String gameID){

        for (Game game: gameList) {
            if (game.getGameID().equals(gameID)){
                return game;
            }
        }
        return null;
    }

    ActiveUser findUserByPlayer(Player player) {
        for (ActiveUser user: activeUsers) {
            if (user.getPlayer().equals(player)) {
                return user;
            }
        }
        return null;
    }

    boolean chechAuthToken(String authToken) {
        if (authTokens.contains(authToken)){
            return true;
        }
        return false;
    }

    /**
     * Creates and retuns a unique, random ID
     * @param isPlayer  if true, returns player ID. If false, returns game ID
     * @return
     */
    String generateID(boolean isPlayer) {
        if(isPlayer) {
            return "P" + UUID.randomUUID().toString();
        }
        else return "G" + UUID.randomUUID().toString();
    }

    String generateAuthToken(){

        String authToken = UUID.randomUUID().toString();
        while (authTokens.contains(authToken)){
            authToken = UUID.randomUUID().toString();
        }
        authTokens.add(authToken);
        return authToken;
    }
}
