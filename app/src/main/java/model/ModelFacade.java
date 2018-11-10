package model;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import communication.Card;
import communication.GameClient;
import communication.GameColor;
import communication.Game;
import communication.GameLobby;
import communication.GameMap;
import communication.Message;
import communication.Player;
import communication.PlayerUser;
import communication.Result;
import communication.Route;
import communication.Ticket;
import server.Poller;
import task.GenericTask;

/**
 * Created by hao on 10/3/18.
 */

public class ModelFacade implements IGameModel {

    private static ModelFacade modelFacade;

    private List<Card> mFaceCards;

    private ModelFacade() {
        Player mHost = new Player("Bob (the host)");
        List<Player> fakePlayers = new ArrayList<>();
        fakePlayers.add(mHost);
        fakePlayers.add( new Player("player 2") );
        fakePlayers.add( new Player("player 3") );
        fakePlayers.add( new Player("player 4") );

        mFaceCards = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            int rando = ThreadLocalRandom.current().nextInt(0,9);

            switch (rando) {
                case 0:
                    mFaceCards.add(new Card(GameColor.RED));
                    break;
                case 1:
                    mFaceCards.add(new Card(GameColor.PURPLE));
                    break;
                case 2:
                    mFaceCards.add(new Card(GameColor.BLUE));
                    break;
                case 3:
                    mFaceCards.add(new Card(GameColor.GREEN));
                    break;
                case 4:
                    mFaceCards.add(new Card(GameColor.YELLOW));
                    break;
                case 5:
                    mFaceCards.add(new Card(GameColor.ORANGE));
                    break;
                case 6:
                    mFaceCards.add(new Card(GameColor.BLACK));
                    break;
                case 7:
                    mFaceCards.add(new Card(GameColor.WHITE));
                    break;
                case 8:
                    mFaceCards.add(new Card(GameColor.LOCOMOTIVE));
                    break;
                default:
            }
        }


//        mCurrentGame = new Game("new republic (the game id)", mHost.getPlayerName(), fakePlayers, 5);
    }

    @Override
    public Result claimRoute(Route route, Player player) {
        return null;
    }

    public static ModelFacade getInstance() {
        if (modelFacade == null) {
            modelFacade = new ModelFacade();
        }
        return modelFacade;
    }

    @Override
    public void setMyTurn(boolean isTurn) {

    }

    @Override
    public int getDeckSize() {
        return 30;
    }

    public void Login(String userName, String password){
        GenericTask genericTask = new GenericTask("Login");
        genericTask.execute(userName,password);
        ModelRoot.getInstance().setUserName(userName);
    }

    public void Register(String userName, String password) {
        GenericTask genericTask = new GenericTask("Register");
        genericTask.execute(userName,password);
        ModelRoot.getInstance().setUserName(userName);
    }

    public void JoinGameLobby(GameLobby gameLobby) {
        GenericTask genericTask = new GenericTask("JoinGameLobby");
        genericTask.execute(ModelRoot.getInstance().getUserName(), gameLobby.getGameID(), ModelRoot.getInstance().getAuthToken());
        ModelRoot.getInstance().setGameLobby(gameLobby);
    }

    public void CreateGameLobby(GameLobby gameLobby) {
        GenericTask genericTask = new GenericTask("CreateGameLobby");
        genericTask.execute(gameLobby, ModelRoot.getInstance().getAuthToken());
        ModelRoot.getInstance().setGameLobby(gameLobby);
    }

    public void LeaveGame(Game game) {
        GenericTask genericTask = new GenericTask("LeaveGame");
        genericTask.execute(ModelRoot.getInstance().getUserName(), game.getGameID(), ModelRoot.getInstance().getAuthToken());
    }

    public void StartGame(Game game) {
        GenericTask genericTask = new GenericTask("StartGame");
        genericTask.execute(game.getGameID(), ModelRoot.getInstance().getAuthToken());
    }

    public void UpdateGameList() {
        GenericTask genericTask = new GenericTask("GetGameList");
        genericTask.execute(ModelRoot.getInstance().getAuthToken());
    }

    public void UpdateGame() {
        GenericTask genericTask = new GenericTask("GetGame");
        genericTask.execute(ModelRoot.getInstance().getGame().getGameID(),ModelRoot.getInstance().getAuthToken());
    }

    public void CheckGameList() {
        GenericTask genericTask = new GenericTask("CheckGameList");
        genericTask.execute(ModelRoot.getInstance().getAuthToken());
    }

    public void CheckGame() {
        GenericTask genericTask = new GenericTask("CheckGame");
        genericTask.execute(ModelRoot.getInstance().getAuthToken(), ModelRoot.getInstance().getGame().getGameID(), Poller.gameVersion);
    }

    @Override
    public void sendMessage(Message message) {
        GenericTask genericTask = new GenericTask("SendMessage");
        genericTask.execute(ModelRoot.getInstance().getAuthToken(), ModelRoot.getInstance().getGame().getGameID(), message);
    }


    @Override
    public List<Player> getPlayers() {
        ModelRoot mr = ModelRoot.getInstance();
        if(mr.getDisplayState() == DisplayState.LOBBY) {
            return mr.getGameLobby().getPlayers();
        }
        else
            return null;
    }



    @Override
    public void addPoints(int p) {
        getPlayer().setPoint(p);
    }

    @Override
    public void useCards(GameColor color, int number) {
        getPlayer().useCards(color, number);
    }

    @Override
    public void addTickets(List<Ticket> tickets) {
        getPlayer().addTickets((ArrayList<Ticket>) tickets);
    }

    @Override
    public void removeTicket(int index) {
        getPlayer().removeTicket(index);
    }

    @Override
    public void updateOpponent() {
//        List<Player> players = ModelRoot.getInstance().getGame().getPlayers();
//        players.get(1).setTrainCarNum(12);
//        players.get(1).setCardNum(24);
//        players.get(1).setPoint(32);
    }

    @Override
    public void chooseTickets(List<Ticket> tickets) {
        System.out.println("called choose ticket");
        System.out.println(tickets.size());
        if (getTickets() != null)
        System.out.println("ticket size is: " + getTickets().size());
        GenericTask genericTask = new GenericTask("ReturnTickets");

        genericTask.execute(ModelRoot.getInstance().getGame().getGameID(),ModelRoot.getInstance().getAuthToken(), tickets);
        ModelRoot.getInstance().setTicketsWanted((ArrayList<Ticket>) tickets);


        getPlayer().addTickets((ArrayList<Ticket>)tickets);
    }

    @Override
    //ask for three new tickext to choose from server
    public void updateChoiceTickets() {


        GenericTask genericTask = new GenericTask("GetTickets");
        genericTask.execute(ModelRoot.getInstance().getGame().getGameID(), ModelRoot.getInstance().getAuthToken());
        System.out.println("called it onece once once");

    }


    @Override
    //update the list of cards user has
    public void updateCards() {
//not for this phase
//        return ModelRoot.getInstance().getGame().getTrainCards();
    }

    @Override
    //whenever we choose a new cards
    public void updateFaceCards() {
        ModelRoot.getInstance().getGame().UserTakeFaceUpCard(0);
    }

    @Override
    public void chooseCard(int index) {
        ModelRoot.getInstance().getGame().UserTakeFaceUpCard(index);
        GenericTask genericTask = new GenericTask("DrawTrainCard");
        genericTask.execute(index, ModelRoot.getInstance().getAuthToken());
    }

    @Override
    public void updateMessages() {
//not for this phase
    }

    //Called by presenter
    public boolean UpdateState(DisplayState displayState) {
        ModelRoot.getInstance().setDisplayState(displayState);
        return true;
    }

    public ArrayList<GameLobby> GetGameLobbyList() {
        return ModelRoot.getInstance().getGameLobbies();
    }

    public Game GetCurrentGame() {
        return ModelRoot.getInstance().getGame();
    }

    public String GetUserName() {
        return ModelRoot.getInstance().getUserName();
    }

    @Override
    public GameMap getMap() {
        return ModelRoot.getInstance().getGame().getmMap();
    }

    @Override
    //ask server
    public void updateTickets() {
//not for this phase
    }


    @Override
    public boolean isMyTurn() {
        return false;
    }

    @Override
    public String getUserName() {
        return ModelRoot.getInstance().getUserName();
    }

    @Override
    public List<Card> getCards() {
        return getPlayer().getCards();
    }

    @Override
    public List<Card> getFaceCards() {
        return ModelRoot.getInstance().getGame().getFaceUpTrainCarCards();
    }

    public PlayerUser getPlayer() {
        GameClient g = ModelRoot.getInstance().getGame();
        if (g != null) {
            PlayerUser p = g.getPlayerUser();
            return p;
        }
        return null;
    }

    @Override
    public List<Ticket> getTickets() {
        return getPlayer().getTickets();
    }

    @Override
    public List<Ticket> getChoiceTickets() {
        if (ModelRoot.getInstance().getGame() != null){
            if (ModelRoot.getInstance().getGame().getPlayerUser()!= null) {
                return ModelRoot.getInstance().getGame().getPlayerUser().getTicketToChoose();
            }
        }
        return null;
    }

    @Override
    public List<Message> getMessages() {
        return GetCurrentGame().getMessages();
    }

    public boolean isGameStarted() {
        return ModelRoot.getInstance().getGameLobby().isStarted();
    }




}
