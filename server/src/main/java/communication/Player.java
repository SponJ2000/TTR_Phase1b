package communication;

import java.util.ArrayList;

/**
 * Created by jalton on 10/1/18.
 */

public class Player {
    private String id;
    private String playerName;
    private Integer point;
    private ArrayList<Ticket> tickets;
    private ArrayList<Card> cards;
    private ArrayList<String> claimedRoutesID;

    private ArrayList<Ticket> ticketToChoose;
    private ArrayList<Card> cardToChoose;

    private Integer ticketNum;
    private Integer trainCarNum;
    private Integer cardNum;
    private PlayerColor playerColor;

    private Player() {
        ticketToChoose = new ArrayList<>();
        claimedRoutesID = new ArrayList<>();

        trainCarNum = 40;
        point = 0;
        cardNum = 0;
        ticketNum = 3;
    }

    public Player(String playerName) {
        this.id = playerName;
        this.playerName = playerName;

        ticketToChoose = new ArrayList<>();
        claimedRoutesID = new ArrayList<>();

        trainCarNum = 40;
        point = 0;
        cardNum = 0;
        ticketNum = 3;
    }

    public Player(String id, String playerName) {
        this.id = id;
        this.playerName = playerName;

        ticketToChoose = new ArrayList<>();
        claimedRoutesID = new ArrayList<>();

        trainCarNum = 40;
        point = 0;
        cardNum = 0;
        ticketNum = 3;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

    public Integer getCardNum() {
        if(cards != null) {
            return cards.size();
        }
        return cardNum;
    }

    public void setCardNum(Integer cardNum) {
        this.cardNum = cardNum;
    }

    public Integer getTicketNum() {
        if(tickets != null) {
            return tickets.size();
        }
        return ticketNum;
    }

    public void setTicketNum(Integer ticketNum) {
        this.ticketNum = ticketNum;
    }

    public Integer getTrainCarNum() {
        return trainCarNum;
    }

    public void setTrainCarNum(Integer trainCarNum) {
        this.trainCarNum = trainCarNum;
    }

    public ArrayList<String> getClaimedRoutes() {
        return claimedRoutesID;
    }

    public void setClaimedRoutes(ArrayList<String> claimedRoutesID) {
        this.claimedRoutesID = claimedRoutesID;
    }

    public boolean addRouteAsClaimed(String r) {
        if (claimedRoutesID.contains(r)) {
            return false;
        }
        claimedRoutesID.add(r);
        return true;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void removeCard(int i) {
        cards.remove(i);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void addCards(ArrayList<Card> moreCards) {
        if(cards == null) {
            cards = new ArrayList<>();
        }
        cards.addAll(moreCards);
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public boolean useCards(CardColor color, int number) {
        int i = 0;
        while(i < cards.size() && number > 0) {
            if(cards.get(i).getColor().equals(color)) {
                cards.remove(i);
                --number;
            }else {
                ++i;
            }
        }
        if(number > 0) {
            return false;
        }
        return true;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void removeTicket(int i) {
        tickets.remove(i);
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public void addTickets(ArrayList<Ticket> moreTickets) {
        if(tickets == null) {
            tickets = new ArrayList<>();
        }
        tickets.addAll(moreTickets);
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    public ArrayList<Ticket> getTicketToChoose() {
        return ticketToChoose;
    }

    public void setTicketToChoose(ArrayList<Ticket> ticketToChoose) {
        this.ticketToChoose = ticketToChoose;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public void addPoint(int point) {
        this.point += point;
    }

    public String getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public ArrayList<Card> getCardToChoose() {
        return cardToChoose;
    }

    public void setCardToChoose(ArrayList<Card> cardToChoose) {
        this.cardToChoose = cardToChoose;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                ", playerName='" + playerName + '\'' +
                ", point=" + point +
                ", tickets=" + tickets +
                ", cards=" + cards +
                ", claimedRoutesID=" + claimedRoutesID +
                ", ticketToChoose=" + ticketToChoose +
                ", cardToChoose=" + cardToChoose +
                ", ticketNum=" + ticketNum +
                ", trainCarNum=" + trainCarNum +
                ", cardNum=" + cardNum +
                ", playerColor=" + playerColor +
                '}';
    }
}
