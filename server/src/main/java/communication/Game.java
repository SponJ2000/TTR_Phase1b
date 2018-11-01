package communication;

import com.obfuscation.server.GenericCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jalton on 10/1/18.
 */

public class Game {
    private String mGameID;
    private String mHost;
    private int mMaxPlayers;
    private ArrayList<Player> mPlayers;
    private ArrayList<Player> mAbsentPlayers;
    private ArrayList<Message> messages;
    private boolean misStarted;

    private ArrayList<Card> trainCards;
    private ArrayList<Ticket> tickets;
    private ArrayList<Card> faceUpTrainCarCards;

    private GameMap mMap;
    //Client only data member-----------------------------------------
    private int state;


    //stop delete this data member, client need this
    private int ticketsRemainNum;
    private int currentPlayerIndex = 0;

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }
    //----------------------------------------------------------------

    //Server only data member ----------------------------------------
    private HashMap<Integer, GenericCommand> stateCommandMap;

    //----------------------------------------------------------------

    public int getTicketsRemainNum() {
        return ticketsRemainNum;
    }

    public void setTicketsRemainNum(int ticketsRemainNum) {
        this.ticketsRemainNum = ticketsRemainNum;
    }

    public ArrayList<Card> getFaceUpTrainCarCards() {
        return faceUpTrainCarCards;
    }

    public void setFaceUpTrainCarCards(ArrayList<Card> faceUpTrainCarCards) {
        this.faceUpTrainCarCards = faceUpTrainCarCards;
    }

    public ArrayList<Card> getTrainCards() {
        return trainCards;
    }

    public void setTrainCards(ArrayList<Card> trainCards) {
        this.trainCards = trainCards;
    }

    public Player getUserPlayer(String userName) {
        for(Player p: mPlayers) {
            if (p.getPlayerName().equals(userName)){
                return p;
            }
        }
        return null;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }



    public Game(String mGameID, String mUsername, ArrayList<Player> mPlayers, int mMaxPlayers) {
        this.mGameID = mGameID;
        this.mHost = mUsername;
        this.mPlayers = mPlayers;
        this.mMaxPlayers = mMaxPlayers;
        this.mAbsentPlayers = new ArrayList<>();
        this.misStarted = false;
        tickets = new ArrayList<Ticket>();
        mMap = new GameMap();
        messages = new ArrayList<Message>();
    }

    public Game() {
        mPlayers = new ArrayList<Player>();
        mAbsentPlayers = new ArrayList<Player>();
        messages = new ArrayList<Message>();

        trainCards = new ArrayList<Card>();
        tickets = new ArrayList<Ticket>();
    }

    public void insertMessage(Message m) {
        messages.add(m);
    }

    public Player getPlayerbyID(String playerID) {
        for(Player p: mPlayers) {
            if (p.getId().equals(playerID)) {
                return p;
            }
        }
        return null;
    }

    public GameMap getmMap() {
        return mMap;
    }

    public void setmMap(GameMap mMap) {
        this.mMap = mMap;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickes(ArrayList<Ticket> tickes) {
        this.tickets = tickes;
    }

    public String getGameID() {
        return mGameID;
    }

    public void setGameID(String mGameID) {
        this.mGameID = mGameID;
    }

    public String getHost() {
        return mHost;
    }

    public void setHost(String host) {
        this.mHost = host;
    }

    public List<Player> getPlayers() {
        return mPlayers;
    }

    public void setPlayers(ArrayList<Player> mPlayers) {
        this.mPlayers = mPlayers;
    }

    public int getPlayerCount() {
        return this.mPlayers.size();
    }

    public int getMaxPlayers() {
        return mMaxPlayers;
    }

    public void setMaxPlayers(int mMaxPlayers) {
        this.mMaxPlayers = mMaxPlayers;
    }

    public int getState(){
        return state;
    }

    public void setState(int nState){
        state = nState;
    }

    public void stateIncreament(){
        state++;
    }

    public void increaseGameState(){
        state++;
    }

    public Result addPlayer(communication.Player player){
        if (misStarted) {
            if (mPlayers.contains(player)) {
                return rejoinGame(player);
            } else return new Result(false, null, "Error: game has started");
        }
        else if (mPlayers.size() < mMaxPlayers) {
            if (!mPlayers.contains(player)) {
                mPlayers.add(player);
                return new Result(true, true, null);
            }
            else return new Result(false, null, "Error: player already in game");
        }
        else return new Result(false, null, "Error: game is full");
    }

    public Result removePlayer(communication.Player player){
        if (!mPlayers.contains(player)) return new Result(false, false, "Error: Player not in game");

        if (misStarted){
            mAbsentPlayers.add(player);
        }
        else mPlayers.remove(player);
        return new Result(true, true, null);
    }

    public Result rejoinGame(communication.Player player) {
        if(!mPlayers.contains(player)) return new Result(false, null, "Error: Player not found");
        else if (mAbsentPlayers.contains(player)) {
            mAbsentPlayers.remove(player);
        }

        return new Result(true, true, null);
    }

    public void startGame(){
        misStarted = true;
    }

    public boolean isStarted() {
        return misStarted;
    }

    public void moveToNextTurn() {
        currentPlayerIndex++;
        currentPlayerIndex %= getPlayers().size();
    }

    @Override
    public String toString() {
        return "{ " + mGameID + ", " + mHost + ", " + mPlayers.size() + ", " + mMaxPlayers + " }";
    }
}
