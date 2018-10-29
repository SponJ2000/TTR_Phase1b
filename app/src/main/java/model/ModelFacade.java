package model;


import java.util.ArrayList;
import java.util.List;

import communication.Card;
import communication.DestinationTicketCard;
import communication.Game;
import communication.GameMap;
import communication.Message;
import communication.Player;
import communication.Ticket;
import communication.TrainCarCard;
import server.ServerProxy;
import task.GenericTask;

/**
 * Created by hao on 10/3/18.
 */

public class ModelFacade implements IGameModel {

    private static ModelFacade modelFacade;



    private ModelFacade() {
        Player mHost = new Player("Bob (the host)");
        List<Player> fakePlayers = new ArrayList<>();
        fakePlayers.add(mHost);
        fakePlayers.add( new Player("player 2") );
        fakePlayers.add( new Player("player 3") );
        fakePlayers.add( new Player("player 4") );
//        mCurrentGame = new Game("new republic (the game id)", mHost.getPlayerName(), fakePlayers, 5);
    }

    public static ModelFacade getInstance() {
        if (modelFacade == null) {
            modelFacade = new ModelFacade();
        }
        return modelFacade;
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
        genericTask.execute(ModelRoot.getInstance().getAuthToken(), ModelRoot.getInstance().getGame().getGameID());
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
    public List<Card> getCards() {
        return getPlayer().getCards();
    }

    @Override
    public Player getPlayer() {
        Player player = ModelRoot.getInstance().getGame().getUserPlayer();
        if (player == null) {
            player = new Player(ModelRoot.getInstance().getUserName());
        }
        return player;
    }

    @Override
    public List<Ticket> getTickets() {
        return getPlayer().getTickets();
    }

    @Override
    public List<Message> getMessages() {
        return GetCurrentGame().getMessages();
    }
}
