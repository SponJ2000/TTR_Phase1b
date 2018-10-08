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
 * Database class that stores information such as gameVersion
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

    private Map<String, String> loginInfo;
    private List<Game> gameList;
    private List<ActiveUser> activeUsers;
    private List<String> authTokens;
    private HashMap<String, String> authTokenMap;

    public List<Game> getGameList() {
        return gameList;
    }

    public List<ActiveUser> getActiveUsers() {
        return activeUsers;
    }

    private static Database db = new Database();

    public static Database getInstance() {
        return db;
    }

    private Database() {
        loginInfo = new HashMap<>();
        gameList = new ArrayList<>();
        activeUsers = new ArrayList<>();
        authTokenMap = new HashMap<>();
    }

    Result register(String id, String password){
        if(!loginInfo.containsKey(id)){
            //Add user and password
            loginInfo.put(id, password);
            String authToken = generateAuthToken();
            authTokenMap.put(id, authToken);
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
                String authToken = generateAuthToken();
                authTokenMap.put(id, authToken);

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
        String userID = game.getHost();
        if(!checkAuthToken(authToken, userID)) return new Result(false, null, "Error: Invalid Token");

        //check if the number of players are right
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
        Game game = findGameByID(gameID);
        if (game == null) return new Result(false, null, "Error: game not found");

        if(!checkAuthToken(authToken, game.getHost())) {
            return new Result(false, null, "Error: Invalid token");
        }

        game.startGame();
        List<Player> players = game.getPlayers();
        for (Player player : players) {
            ActiveUser user = findUserByPlayer(player);
            if (user == null) return new Result(false, null, "Error: User not found");

            user.getJoinedGames().add(gameID);
        }

        return new Result(true, true, null);
    }

    //TODO : do we need authtoken?
    Result leaveGame(String gameID, String playerID) {
        Player player = findPlayerByID(playerID);
        Game game = findGameByID(gameID);

        if(player == null || game == null) {
            return new Result(false, null, "Error: Could not leave game");
        }

        return game.removePlayer(player);

    }

    //TODO : do we need authtoken?
    Result rejoinGame(String gameID, String playerID) {
        Player player = findPlayerByID(playerID);
        Game game = findGameByID(gameID);

        if(player == null || game == null) {
            return new Result(false, null, "Error: Could not rejoin game");
        }

        return game.rejoinGame(player);
    }

    public Game getGame(String gameID) {
        return findGameByID(gameID);
    }

    /**
     * Helper function to find the player by its id
     * @param id
     * @return
     */
    Player findPlayerByID(String id){
        for (ActiveUser user : activeUsers) {
            if (user.getPlayer().getPlayerName().equals(id))
                    return user.getPlayer();
        }
        return null;
    }

    /**
     * Helper function to find the game by its id
     * @param gameID
     * @return
     */
    Game findGameByID(String gameID){
        for (Game game: gameList) {
            if (game.getGameID().equals(gameID)){
                return game;
            }
        }
        return null;
    }

    /**
     * Helper function to find the active user by the player object
     * @param player
     * @return
     */
    ActiveUser findUserByPlayer(Player player) {
        for (ActiveUser user: activeUsers) {
            if (user.getPlayer().equals(player)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Checking if the authToken belongs to the specified user. If not return false
     * @param authToken
     * @param userID
     * @return
     */
    public boolean checkAuthToken(String authToken, String userID) {
        if (authTokenMap.containsKey(userID) && authTokenMap.get(userID).equals(authToken)) return true;
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

    /**
     * Generates authToken for users. There are stores in a map where keys are the username and values are authToken Strings
     * @return
     */
    String generateAuthToken(){
        String authToken = UUID.randomUUID().toString();
        while (authTokens.contains(authToken)){
            authToken = UUID.randomUUID().toString();
        }
        authTokens.add(authToken);
        return authToken;
    }
}
