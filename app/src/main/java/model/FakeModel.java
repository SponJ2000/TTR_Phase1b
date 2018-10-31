package model;

import com.obfuscation.ttr_phase1b.activity.PresenterFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import communication.Card;
import communication.CardColor;
import communication.City;
import communication.Game;
import communication.GameMap;
import communication.Message;
import communication.Player;
import communication.PlayerColor;
import communication.Ticket;

public class FakeModel implements IGameModel {

    private static FakeModel model;

    private String authToken;
    private ArrayList<Game> gameList;
    private Game game;
    private State state;
    private String userName;

    private List<Ticket> allTickets;
    private int allTickInd;
    private List<Ticket> userTickets;
    private List<Ticket> choiceTickets;

    private List<Card> faceupCards;
    private List<Card> userCards;

    private List<Message> messages;

    private Player mPlayer;

    private FakeModel() {

        Player mHost = new Player("Bob (the host)");

        List<Player> fakePlayers = new ArrayList<>();
        fakePlayers.add(mHost);
        fakePlayers.add( new Player("player 2") );
        fakePlayers.add( new Player("player 3") );
        fakePlayers.add( new Player("player 4") );

        mPlayer = mHost;

        initPlayer();

        userName = "Bob";
        game = new Game();
        state = State.GAME;

        userTickets = new ArrayList<>();
        choiceTickets = null;
        allTickets = new ArrayList<>();
        City city1, city2;
        city1 = new City("New York City");
        city2 = new City("Los Angeles");
        allTickets.add(new Ticket(city1, city2, 22));
        city1 = new City("New York City");
        city2 = new City("Florida");
        allTickets.add(new Ticket(city1, city2, 12));
        city1 = new City("Seattle");
        city2 = new City("Los Angeles");
        allTickets.add(new Ticket(city1, city2, 8));
        city1 = new City("Kansas");
        city2 = new City("Los Angeles");
        allTickets.add(new Ticket(city1, city2, 15));
        city1 = new City("Los Angeles");
        city2 = new City("Florida");
        allTickets.add(new Ticket(city1, city2, 19));
        city1 = new City("Seattle");
        city2 = new City("Kansas");
        allTickets.add(new Ticket(city1, city2, 14));
        city1 = new City("New York City");
        city2 = new City("Kansas");
        allTickets.add(new Ticket(city1, city2, 9));
        allTickInd = 0;

        messages = new ArrayList<>();
        messages.add(new Message("angie", "hello people of earth"));
        messages.add(new Message("harry", "who are you?"));
        messages.add(new Message("angie", "death in a box"));
        messages.add(new Message("harry", "?\n...okay..."));
        messages.add(new Message(userName, "whatsup my homies!"));


        faceupCards = new ArrayList<>();
        faceupCards.add(new Card(CardColor.BLACK));
        faceupCards.add(new Card(CardColor.BLACK));
        faceupCards.add(new Card(CardColor.PURPLE));
        faceupCards.add(new Card(CardColor.RED));
        faceupCards.add(new Card(CardColor.YELLOW));

        userCards = new ArrayList<>();
        userCards.add(new Card(CardColor.LOCOMOTIVE));
        userCards.add(new Card(CardColor.WHITE));
        userCards.add(new Card(CardColor.BLACK));
        userCards.add(new Card(CardColor.GREEN));
    }

    public static FakeModel getInstance() {
        if(model == null) {
            model = new FakeModel();
        }
        return model;
    }

    private void initPlayer(){
        PlayerColor color = PlayerColor.BLACK;

        int rando = ThreadLocalRandom.current().nextInt(0,5);
        switch (rando) {
            case 0:
                color = PlayerColor.RED;
                break;
            case 1:
                color = PlayerColor.PURPLE;
                break;
            case 2:
                color = PlayerColor.YELLOW;
                break;
            case 3:
                color = PlayerColor.BLACK;
                break;
            case 4:
                color = PlayerColor.BLUE;
                break;
        }

        mPlayer.setPlayerColor(color);
    }

    @Override
    public Player getPlayer() {
        return mPlayer;
    }

    @Override
    public int getDeckSize() {
        return game.getDeckSize();
    }

    //  metadata
    @Override
    public boolean isMyTurn() {
        return false;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public GameMap getMap() {
        return null;
    }

    //  tickets
    @Override
    public void updateTickets() {
        PresenterFacade.getInstance().updatePresenter(true);
    }

    @Override
    public void updateChoiceTickets() {
        PresenterFacade.getInstance().updatePresenter(true);
    }

    @Override
    public List<Ticket> getTickets() {
        return userTickets;
    }

    @Override
    public List<Ticket> getChoiceTickets() {
        choiceTickets = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            choiceTickets.add(allTickets.get(allTickInd));
            ++allTickInd;
            allTickInd = allTickInd % allTickets.size();
        }
        return choiceTickets;
    }

    @Override
    public void chooseTickets(List<Ticket> tickets) {
        for (Ticket ticket: tickets) {
            userTickets.add(ticket);
        }
        choiceTickets = null;
    }

    //  cards
    @Override
    public void updateCards() {
        PresenterFacade.getInstance().updatePresenter(true);
    }

    @Override
    public void updateFaceCards() {
        PresenterFacade.getInstance().updatePresenter(true);
    }

    @Override
    public List<Card> getFaceCards() {
        return faceupCards;
    }

    @Override
    public List<Card> getCards() {
        return userCards;
    }

    @Override
    public void chooseCard(int index) {
        if(index > 0) {
            userCards.add(faceupCards.get(index));
            faceupCards.remove(index);
            faceupCards.add(new Card(CardColor.GREEN));
        }else {
            userCards.add(new Card(CardColor.ORANGE));
        }
    }

    //  messages
    @Override
    public void updateMessages() {
        PresenterFacade.getInstance().updatePresenter(true);
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public void sendMessage(Message message) {
        messages.add(message);
        PresenterFacade.getInstance().updatePresenter(null);
    }

}
