package communication;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by urimaj on 11/9/18.
 */

public class GameServer extends Game {

    private ArrayList<Card> trainCards = new ArrayList<>();
    private ArrayList<Ticket> tickets = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private ArrayList<PlayerUser> players = new ArrayList<>();

    public ArrayList<Card> getTrainCards() {
        return trainCards;
    }

    public void setTrainCards(ArrayList<Card> trainCards) {
        this.trainCards = trainCards;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public ArrayList<PlayerUser> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<PlayerUser> players) {
        this.players = players;
    }

    public ArrayList<Player> getmAbsentPlayers() {
        return mAbsentPlayers;
    }

    public void setmAbsentPlayers(ArrayList<Player> mAbsentPlayers) {
        this.mAbsentPlayers = mAbsentPlayers;
    }

    public GameServer() {
        setGameID(UUID.randomUUID().toString());
    }

    public int getPlayerCount() {
        return players.size();
    }



    public void moveToNextTurn() {
        currentPlayerIndex++;
        currentPlayerIndex %= getPlayers().size();
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public Card takeCard(int index) {
        Card card;
        if(index < 0) {
            card = trainCards.get(0);
            trainCards.remove(0);
        }else {
            card = getFaceUpTrainCarCards().get(index);
            getFaceUpTrainCarCards().remove(index);
            getFaceUpTrainCarCards().add(trainCards.get(0));
            trainCards.remove(0);
        }
        return card;
    }
    private ArrayList<Player> mAbsentPlayers;

    public int getDeckSize() {
        return 1;
    }

    public PlayerUser getPlayerbyUserName(String username) {
        for(PlayerUser p: players) {
            if (p.getPlayerName().equals(username)) {
                return p;
            }
        }
        return null;
    }

    //todo: set a random gameID
}
