package model;


import android.view.Display;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import communication.Card;
import communication.CardColor;
import communication.Game;
import communication.GameMap;
import communication.Message;
import communication.Player;
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
                    mFaceCards.add(new Card(CardColor.RED));
                    break;
                case 1:
                    mFaceCards.add(new Card(CardColor.PURPLE));
                    break;
                case 2:
                    mFaceCards.add(new Card(CardColor.BLUE));
                    break;
                case 3:
                    mFaceCards.add(new Card(CardColor.GREEN));
                    break;
                case 4:
                    mFaceCards.add(new Card(CardColor.YELLOW));
                    break;
                case 5:
                    mFaceCards.add(new Card(CardColor.ORANGE));
                    break;
                case 6:
                    mFaceCards.add(new Card(CardColor.BLACK));
                    break;
                case 7:
                    mFaceCards.add(new Card(CardColor.WHITE));
                    break;
                case 8:
                    mFaceCards.add(new Card(CardColor.LOCOMOTIVE));
                    break;
                default:
            }
        }


//        mCurrentGame = new Game("new republic (the game id)", mHost.getPlayerName(), fakePlayers, 5);
    }

    public static ModelFacade getInstance() {
        if (modelFacade == null) {
            modelFacade = new ModelFacade();
        }
        return modelFacade;
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

    public void JoinGame(Game game) {
        GenericTask genericTask = new GenericTask("JoinGame");
        genericTask.execute(ModelRoot.getInstance().getUserName(), game.getGameID(), ModelRoot.getInstance().getAuthToken());
        ModelRoot.getInstance().setGame(game);
    }

    public void CreateGame(Game game) {
        GenericTask genericTask = new GenericTask("CreateGame");
        genericTask.execute(game, ModelRoot.getInstance().getAuthToken());
        ModelRoot.getInstance().setGame(game);
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
        return ModelRoot.getInstance().getGame().getPlayers();
    }

    @Override
    public void addPoints(int p) {
        getPlayer().setPoint(p);
    }

    @Override
    public void useCards(CardColor color, int number) {
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
        List<Player> players = ModelRoot.getInstance().getGame().getPlayers();
        players.get(1).setTrainCarNum(12);
        players.get(1).setCardNum(24);
        players.get(1).setPoint(32);
    }

    @Override
    public void chooseTickets(List<Ticket> tickets) {
        GenericTask genericTask = new GenericTask("ChooseTicket");

        genericTask.execute(ModelRoot.getInstance().getGame().getGameID(),ModelRoot.getInstance().getAuthToken(), tickets);
        ModelRoot.getInstance().setTicketsWanted((ArrayList<Ticket>) tickets);
    }

    @Override
    //ask for three new tickext to choose from server
    public void updateChoiceTickets() {
        GenericTask genericTask = new GenericTask("GetTickets");
        genericTask.execute(ModelRoot.getInstance().getGame().getGameID(), ModelRoot.getInstance().getAuthToken());

    }


    @Override
    //update the list of cards user has
    public void updateCards() {
//not for this phase
    }

    @Override
    //whenever we choose a new cards
    public void updateFaceCards() {
        ModelRoot.getInstance().getGame().takeCard(0);
    }

    @Override
    public void chooseCard(int index) {
        getPlayer().addCard(ModelRoot.getInstance().getGame().takeCard(index));
//        GenericTask genericTask = new GenericTask("DrawTrainCard");
//        genericTask.execute(index, ModelRoot.getInstance().getAuthToken());
    }

    @Override
    public void updateMessages() {
//not for this phase
    }

    //Called by presenter
    public boolean UpdateState(State state) {
        ModelRoot.getInstance().setState(state);
        return true;
    }

    public ArrayList<Game> GetGameList() {
        return ModelRoot.getInstance().getGameList();
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

    public Player getPlayer() {
        Game g = ModelRoot.getInstance().getGame();
        if (g != null) {
            Player p = g.getUserPlayer(ModelRoot.getInstance().getUserName());
            return p;
        }
        return null;
    }

    @Override
    public List<Ticket> getTickets() {
        return getPlayer().getTicketToChoose();
    }

    @Override
    public List<Ticket> getChoiceTickets() {
        return ModelRoot.getInstance().getGame().getUserPlayer(ModelRoot.getInstance().getUserName()).getTicketToChoose();
    }

    @Override
    public List<Message> getMessages() {
        return GetCurrentGame().getMessages();
    }

    public boolean isGameStarted() {
        return ModelRoot.getInstance().getGame().isStarted();
    }

}
