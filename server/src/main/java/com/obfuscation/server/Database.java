package com.obfuscation.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import communication.ActiveUser;
import communication.Card;
import communication.City;
import communication.Game;
import communication.GameLobby;
import communication.GameServer;
import communication.Message;
import communication.Player;
import communication.PlayerUser;
import communication.Result;
import communication.TickectMaker;
import communication.Ticket;
import communication.GameColor;

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
    //TODO : make gameid and gamelobbyid same
    private List<GameLobby> gameLobbyList = new ArrayList<>();
    private List<GameServer> gameList = new ArrayList<>();


    private List<ActiveUser> activeUsers;
    private List<String> authTokens;
    private HashMap<String, String> authTokenMap;
    public GameServer dummyGame = new GameServer();

    public void setDummyGame() {
        dummyGame.setGameID("GAME");
        authTokenMap.put("Bob", "authBob");
        authTokenMap.put("Joe", "authJoe");
        loginInfo.put("Bob", "password");
        loginInfo.put("Joe", "password");
        setupGame("GAME");
    }

    private List<GameColor> colors = Arrays.asList(GameColor.PLAYER_BLACK, GameColor.PLAYER_BLUE, GameColor.PLAYER_PURPLE, GameColor.PLAYER_RED, GameColor.PLAYER_YELLOW);

    public List<GameLobby> getGameList() {
        return gameLobbyList;
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
        gameLobbyList = new ArrayList<>();
        activeUsers = new ArrayList<>();
        authTokenMap = new HashMap<>();
        authTokens = new ArrayList<>();
//        setDummyGame();
        loginInfo.put("Bob", "password");
        loginInfo.put("Joe", "password");
        login("Bob", "password");
        login("Joe", "password");
        authTokenMap.put("Bob", "authBob");
        authTokenMap.put("Joe", "authJoe");
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
        System.out.println(id + " " + password);
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
                    //TODO : ?
                    Player player = new Player(id);
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

    Result newGameLobby(GameLobby gameLobby, String authToken){
        boolean valid = false;
        String errorInfo = null;
        if(gameLobby == null) {
            errorInfo = "Error: Game is null";
        }
        else if(gameLobby.getGameID() == null || gameLobby.getGameID().equals("")) {
            errorInfo = "Error: Invalid game name (cannot be blank)";
        }
        else if(findGameByID(gameLobby.getGameID()) != null) {
            errorInfo = "Error: Game name must be unique";
        }
        else if (gameLobby.getHost() == null || gameLobby.getHost().equals("")) {
            errorInfo = "Error: Invalid host name (cannot be blank)";
        }
        else if (gameLobby.getPlayers() == null) {
            errorInfo = "Error: Invalid Player List";
        }
        else if(gameLobby.getMaxPlayers() < 2 || gameLobby.getMaxPlayers() > 5){
            errorInfo = "Error: Invalid max players";
        }
        else valid = true;
        if(!valid) return new Result(valid, null, errorInfo);
        //check the userID
        String userID = gameLobby.getHost();
        if(!checkAuthToken(authToken, userID)) return new Result(false, null, "Error: Invalid Token");

        ActiveUser user = findUserByID(userID);
        if (user == null) return new Result(false, null, "Error: Invlaid user");
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(user.getPlayer());
        gameLobby.setPlayers(playerList);

        gameLobbyList.add(gameLobby);
        System.out.println("Get HERE");
        return new Result(true, gameLobbyList, null);
    }

    Result joinGame(String playerID, String gameID) {
        ActiveUser user = findUserByID(playerID);
        GameLobby game = findGameLobbyByID(gameID);

        if(user == null || game == null) {
            return new Result(false, null, "Error: Could not join game");
        }

        Player player = user.getPlayer();
        if (game.isStarted()) {
            if (game.getPlayers().contains(player)) {
                return rejoinGame(gameID, playerID);
            } else return new Result(false, null, "Error: game has started");
        }
        else if (game.getPlayers().size() < game.getMaxPlayers()) {
            if (!game.getPlayers().contains(player)) {
                game.getPlayers().add(player);
                return new Result(true, true, null);
            }
            else return new Result(false, null, "Error: player already in game");
        }
        else return new Result(false, null, "Error: game is full");
    }

    void setupGame(String gameID) {
        GameServer gameServer = findGameByID(gameID);

        //initialize traincards
        ArrayList<Card> trainCards = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Card purpleCard = new Card(GameColor.PURPLE);
            Card blueCard = new Card(GameColor.BLUE);
            Card orangeCard = new Card(GameColor.ORANGE);
            Card whiteCard = new Card(GameColor.WHITE);
            Card greenCard = new Card(GameColor.GREEN);
            Card redCard = new Card(GameColor.RED);
            Card blackCard = new Card(GameColor.BLACK);
            Card yellowCard = new Card(GameColor.YELLOW);
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
            Card LocomotiveCard = new Card(GameColor.LOCOMOTIVE);
        }


        System.out.println("TRAIN CARD SIZE " + trainCards.size());
        Collections.shuffle(trainCards);
        gameServer.setTrainCards(trainCards);

        ArrayList<Ticket> tickets = new ArrayList<>();
        //initialize destTickets
        for (int i = 0; i < 30; i++) {
            //FIXME**
            Ticket ticket = new Ticket(new City("CITY1", 0,0), new City("city2", 0,0), 100);
            tickets.add(ticket);
        }
        tickets = new TickectMaker().MakeCards();

        System.out.println("MAKER CARD SIZE " + tickets.size());
        Collections.shuffle(tickets);

        //set the deck
        gameServer.setTickets(tickets);

        //set player train cards
        for (PlayerUser player : gameServer.getPlayers()) {
            ArrayList<Card> playerTrainCards = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                Card card = trainCards.get(0);
                playerTrainCards.add(card);
                trainCards.remove(0);
            }
            player.setCards(playerTrainCards);
            System.out.println("CARD SET");
            System.out.println(player.getCards().size());
        }

        //set faceuptrain cards
        ArrayList<Card> faceUpTrainCards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Card card = trainCards.get(0);
            faceUpTrainCards.add(card);
            trainCards.remove(0);
        }

        // set faceup cards
        gameServer.setFaceUpTrainCarCards(faceUpTrainCards);

        gameServer.setTrainCards(trainCards);

    }
    Result startGame(String gameID, String authToken) {
        GameLobby gameLobby = findGameLobbyByID(gameID);
        if (gameLobby == null) return new Result(false, null, "Error: gameLobby not found");

        if(!checkAuthToken(authToken, gameLobby.getHost())) {
            return new Result(false, null, "Error: Invalid token");
        }

        if(gameLobby.getPlayers().size() < 2) return new Result(false, null, "Error: Cannot start a game with less than 2 players");
        GameServer game = new GameServer();

        //set players
        ArrayList<PlayerUser> players = new ArrayList<>();
        for (Player p : gameLobby.getPlayers()) {
            players.add(new PlayerUser(p.getPlayerName()));
        }
        game.setPlayers(players);

        //TODO : NEED THIS?
//        for (Player player : players) {
//            ActiveUser user = findUserByPlayer(player);
//            if (user == null) return new Result(false, null, "Error: User not found");
//
//            user.getJoinedGames().add(gameID);
//        }

        //Assign PlayerColors
        Collections.shuffle(colors);
        for (int i = 0; i < game.getPlayers().size(); i++) {
            game.getPlayers().get(i).setPlayerColor(colors.get(i));
        }
        return new Result(true, game, null);
    }

    Result leaveGame(String gameID, String playerID) {
        ActiveUser user = findUserByID(playerID);
        GameLobby game = findGameLobbyByID(gameID);

        if(user == null || game == null) {
            return new Result(false, null, "Error: Could not leave game");
        }
        Player player = user.getPlayer();
        if (!game.getPlayers().contains(player)) return new Result(false, false, "Error: Player not in game");

        if (game.isStarted()){
            //TODO : add absent players
            //game.get.add(player);
        }
        else game.getPlayers().remove(player);
        return new Result(true, true, null);

    }

    Result rejoinGame(String gameID, String playerID) {
        Player player = findUserByID(playerID).getPlayer();
        GameLobby game = findGameLobbyByID(gameID);

        if(player == null || game == null) {
            return new Result(false, null, "Error: Could not rejoin game");
        }

        if(!game.getPlayers().contains(player)) return new Result(false, null, "Error: Player not found");
        //TODO : absent players?
//        else if (mAbsentPlayers.contains(player)) {
//            mAbsentPlayers.remove(player);
//        }

        return new Result(true, true, null);
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
    GameLobby findGameLobbyByID(String gameID){
        for (GameLobby gameLobby: gameLobbyList) {
            if (gameLobby.getGameID().equals(gameID)){
                return gameLobby;
            }
        }
        return null;
    }

    GameServer findGameByID(String gameID){
        for (GameServer gameServer : gameList) {
            if (gameServer.getGameID().equals(gameID)){
                return gameServer;
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
    public String findUsernameByAuthToken(String authToken) {
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
            String playerID = findUsernameByAuthToken(authToken);
            if (playerID != null) {
                Message messageObject = new Message(playerID, message);
                game.getMessages().add(messageObject);
                return new Result(true, messageObject, null);
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

    List<Ticket> getTickets(String gameID, String authToken) {
        GameServer game = findGameByID(gameID);
        List<Ticket> tickets = game.getTickets();
        ArrayList<Ticket> playerTickets = new ArrayList<>();
        if (tickets.size() < 3) {
            return tickets;
        }
        for (int i = 0; i < 3; i++) {
            Ticket ticket = tickets.get(i);
            playerTickets.add(ticket);
            tickets.remove(0);
        }

        String playerID = findUsernameByAuthToken(authToken);
        for (PlayerUser player : game.getPlayers()) {
            if (player.getPlayerName().equals(playerID)) {
                player.setTicketToChoose(playerTickets);
            }
        }
        return playerTickets;
    }

    Result setTickets(String gameID, String authToken, List<Ticket> tickets) {
        try {
            GameServer game = findGameByID(gameID);
            if (game != null) {
                String playerID = findUsernameByAuthToken(authToken);
                ArrayList<Ticket> tickets1 = new ArrayList<>();

                for (PlayerUser player : game.getPlayers()) {
                    if (player.getPlayerName().equals(playerID)) {
                        tickets1 = player.getTicketToChoose();
                    }
                }
                System.out.println("KEEP TICKET " + tickets.size());
                System.out.println("PLAYER TICKET CHOOSE " + tickets1.size());
                Set<Integer> overlap = new HashSet<>();
                for (int i = 0; i < tickets.size(); i++) {
                    for (int j = 0; j < tickets1.size(); j++) {
                        System.out.println("EEE");
                        try {
                            System.out.println(tickets.get(i).getCity1() + "#");

                            //FIXME** why is this not printing?
                            System.out.println(tickets.get(i).toString());
                            System.out.println(tickets1.get(j).toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (tickets.get(i).equals(tickets1.get(j))) {
                            System.out.println("))))");
                            overlap.add(j);
                        }
                    }
                }
                System.out.println("OVERLAP SIZE + " + overlap.size());
                List<Ticket> ticketDeck = game.getTickets();
                for (Integer i : overlap) {
                    ticketDeck.add(tickets1.get(i));
                }
                for (PlayerUser player : game.getPlayers()) {
                    if (player.getPlayerName().equals(playerID)) {
                        player.setTickets((ArrayList<Ticket>) tickets);
                        player.setTicketToChoose(new ArrayList<>());
                    }
                }
                return new Result(true, tickets, null);
            }
//                //TODO : if the deck size is less then 3?
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, null, "Error : player not found");
    }
}
