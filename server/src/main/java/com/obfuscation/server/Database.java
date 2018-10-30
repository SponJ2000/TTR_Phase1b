package com.obfuscation.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import communication.ActiveUser;
import communication.Card;
import communication.City;
import communication.Game;
import communication.Message;
import communication.Player;
import communication.Result;
import communication.Ticket;
import communication.TrainCarCardColor;
import communication.color;

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
    private List<color> colors = Arrays.asList(color.BLACK, color.BLUE, color.PURPLE, color.RED, color.YELLOW);

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
        authTokens = new ArrayList<>();
    }

    Result register(String id, String password){
        if(id == null || id.equals("")) return new Result(false, null, "Error: Invalid username (cannot be blank)");
        if(password == null || password.equals("")) return new Result(false, null, "Error: Invalid password (cannot be blank");

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
                if(findUserByID(id) != null) {
                    ActiveUser user = findUserByID(id);
                    user.setAuthToken(authToken);
                }
                else {
                    Player player = new Player(generateID(true), id);
                    ActiveUser user = new ActiveUser(player, authToken);
                    activeUsers.add(user);
                }
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


        boolean valid = false;
        String errorInfo = null;
        if(game == null) {
            errorInfo = "Error: Game is null";
        }
        else if(game.getGameID() == null || game.getGameID().equals("")) {
            errorInfo = "Error: Invalid game name (cannot be blank)";
        }
        else if(findGameByID(game.getGameID()) != null) {
            errorInfo = "Error: Game name must be unique";
        }
        else if (game.getHost() == null || game.getHost().equals("")) {
            errorInfo = "Error: Invalid host name (cannot be blank)";
        }
        else if (game.getPlayers() == null) {
            errorInfo = "Error: Invalid Player List";
        }
        else if(game.getMaxPlayers() < 2 || game.getMaxPlayers() > 5){
            errorInfo = "Error: Invalid max players";
        }
        else valid = true;
        if(!valid) return new Result(valid, null, errorInfo);
        //check the userID
        String userID = game.getHost();
        if(!checkAuthToken(authToken, userID)) return new Result(false, null, "Error: Invalid Token");

        ActiveUser user = findUserByID(userID);
        if (user == null) return new Result(false, null, "Error: Invlaid user");
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(user.getPlayer());
        game.setPlayers(playerList);

        gameList.add(game);
        System.out.println("Get HERE");
        return new Result(true, gameList, null);
    }

    Result joinGame(String playerID, String gameID) {


        ActiveUser user = findUserByID(playerID);
        Game game = findGameByID(gameID);

        if(user == null || game == null) {
            return new Result(false, null, "Error: Could not join game");
        }

        return game.addPlayer(user.getPlayer());
    }

    void setupGame(String gameID) {
        Game game = getGame(gameID);

        //initialize traincards
        ArrayList<Card> trainCards = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Card purpleCard = new Card(TrainCarCardColor.PURPLE);
            Card blueCard = new Card(TrainCarCardColor.BLUE);
            Card orangeCard = new Card(TrainCarCardColor.ORANGE);
            Card whiteCard = new Card(TrainCarCardColor.WHITE);
            Card greenCard = new Card(TrainCarCardColor.GREEN);
            Card redCard = new Card(TrainCarCardColor.RED);
            Card blackCard = new Card(TrainCarCardColor.BLACK);
            Card yellowCard = new Card(TrainCarCardColor.YELLOW);
            trainCards.add(purpleCard);
            trainCards.add(blueCard);
            trainCards.add(orangeCard);
            trainCards.add(whiteCard);
            trainCards.add(greenCard);
            trainCards.add(redCard);
            trainCards.add(blackCard);
            trainCards.add(yellowCard);
        }
        for (int i = 0; i < 14; i++) {
            Card LocomotiveCard = new Card(TrainCarCardColor.LOCOMOTIVE);
        }
        Collections.shuffle(trainCards);
        game.setTrainCards(trainCards);

        ArrayList<Ticket> tickets = new ArrayList<>();
        //initialize destTickets
        for (int i = 0; i < 30; i++) {
            //FIXME**
            Ticket ticket = new Ticket(new City("CITY1"), new City("city2"), 100);
            tickets.add(ticket);
        }


        Collections.shuffle(tickets);

        //set player tickets
        for (Player player : game.getPlayers()) {
            ArrayList<Ticket> playerTickets = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Ticket ticket = tickets.get(0);
                playerTickets.add(ticket);
                tickets.remove(0);
            }
            player.setTickets(playerTickets);
        }

        //set the deck
        game.setTickes(tickets);

        //set player train cards
        for (Player player : game.getPlayers()) {
            ArrayList<Card> playerTrainCards = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                Card card = trainCards.get(0);
                playerTrainCards.add(card);
                playerTrainCards.remove(0);
            }
            player.setCards(playerTrainCards);
        }

        game.setTrainCards(trainCards);

    }
    Result startGame(String gameID, String authToken) {
        Game game = findGameByID(gameID);
        if (game == null) return new Result(false, null, "Error: game not found");

        if(!checkAuthToken(authToken, game.getHost())) {
            return new Result(false, null, "Error: Invalid token");
        }

        if(game.getPlayers().size() < 2) return new Result(false, null, "Error: Cannot start a game with less than 2 players");

        game.startGame();

        List<Player> players = game.getPlayers();
        for (Player player : players) {
            ActiveUser user = findUserByPlayer(player);
            if (user == null) return new Result(false, null, "Error: User not found");

            user.getJoinedGames().add(gameID);
        }

        //Assign colors
        Collections.shuffle(colors);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setPlayerColor(colors.get(i));
        }
        return new Result(true, game, null);
    }

    //TODO : do we need authtoken?
    Result leaveGame(String gameID, String playerID) {
        ActiveUser user = findUserByID(playerID);
        Game game = findGameByID(gameID);

        if(user == null || game == null) {
            return new Result(false, null, "Error: Could not leave game");
        }

        return game.removePlayer(user.getPlayer());

    }

    //TODO : do we need authtoken?
    Result rejoinGame(String gameID, String playerID) {
        Player player = findUserByID(playerID).getPlayer();
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
    ActiveUser findUserByID(String id){
        for (ActiveUser user : activeUsers) {
            if (user.getPlayer().getPlayerName().equals(id))
                    return user;
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
    public String findPlayerIDByAuthToken(String authToken) {
        for (Map.Entry<String, String> entry : authTokenMap.entrySet()) {
            if (authToken.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public String findAuthTokenByPlayerID(String playerID) {
        for (Map.Entry<String, String> entry : authTokenMap.entrySet()) {
            if (playerID.equals(entry.getKey())) {
                return entry.getValue();
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
        if (authToken.equals("masterKey")) return true;
        if (authTokenMap.containsKey(userID) && authTokenMap.get(userID).equals(authToken)) return true;
        return false;
    }

    /**
     * updating game message list
     * @param gameID
     * @param message
     * @param authToken
     * @return
     */
    public Result sendMessage(String gameID, String message, String authToken) {
        if (authTokenMap.containsValue(authToken) || authToken.equals("masterKey")) {
            Game game = findGameByID(gameID);
            String playerID = findPlayerIDByAuthToken(authToken);
            if (playerID != null) {
                Message messageObject = new Message(playerID, message);
                game.getMessages().add(messageObject);
                return new Result(false, messageObject, null);
            }
        }
        return new Result(false, null, "Error : Invalid auth_token");
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
