package model;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import communication.Card;
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
        genericTask.execute(ModelRoot.getInstance().getAuthToken(), ModelRoot.getInstance().getGame().getGameID(), Poller.gameVersion);
    }

    @Override
    public void sendMessage(Message message) {
        GenericTask genericTask = new GenericTask("SendMessage");
        genericTask.execute(ModelRoot.getInstance().getAuthToken(), ModelRoot.getInstance().getGame().getGameID(), message);
    }

    @Override
    public void chooseTickets(List<Ticket> tickets) {
        GenericTask genericTask = new GenericTask("ChooseTicket");

        ArrayList<Ticket> ticketsToChoose = ModelRoot.getInstance().getGame().getUserPlayer(ModelRoot.getInstance().getUserName()).getTicketToChoose();

        ArrayList<Integer> choosenTicketIndex = new ArrayList<Integer>();

        for(Ticket ticket: tickets) {
            for (int i = 0; i < ticketsToChoose.size(); i++) {
                if (ticketsToChoose.get(i).equals(ticket)) {
                    choosenTicketIndex.add(i);
                    break;
                }
            }
        }

        genericTask.execute(ModelRoot.getInstance().getAuthToken(), ModelRoot.getInstance().getGame().getGameID(), choosenTicketIndex);
        ModelRoot.getInstance().setTicketsWanted((ArrayList<Ticket>) tickets);
    }

    @Override
    public void updateCards() {

    }

    @Override
    public void updateFaceCards() {

    }

    @Override
    public void chooseCard(int index) {
        GenericTask genericTask = new GenericTask("DrawTrainCard");
        genericTask.execute(index, ModelRoot.getInstance().getAuthToken());
    }

    @Override
    public void updateMessages() {

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
    public void updateTickets() {

    }

    @Override
    public void updateChoiceTickets() {

    }

    @Override
    public boolean isMyTurn() {
        return false;
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public List<Card> getCards() {
        return getPlayer().getCards();
    }

    @Override
    public List<Card> getFaceCards() {
        return null;
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
}
