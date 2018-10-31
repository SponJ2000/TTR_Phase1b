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
    private PlayerColor playerPlayerColor;


    public PlayerColor getPlayerPlayerColor() {
        return playerPlayerColor;
    }

    public void setPlayerPlayerColor(PlayerColor playerPlayerColor) {
        this.playerPlayerColor = playerPlayerColor;
    }

    public Player(String id, String playerName) {
        this.id = id;
        this.playerName = playerName;
        tickets = new ArrayList<Ticket>();
        ticketToChoose = new ArrayList<Ticket>();
        cards = new ArrayList<Card>();
        claimedRoutesID = new ArrayList<String>();
    }

    public Player(String playerName) {
        this.id = null;
        this.playerName = playerName;
        tickets = new ArrayList<Ticket>();
        ticketToChoose = new ArrayList<Ticket>();
        cards = new ArrayList<Card>();
        claimedRoutesID = new ArrayList<String>();
    }


    public Integer getTicketNum() {
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

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void addTickets(ArrayList<Ticket> moreTickets) {
        this.tickets.addAll(moreTickets);
    }
    public ArrayList<Ticket> getTicketToChoose() {
        return ticketToChoose;
    }

    public void setTicketToChoose(ArrayList<Ticket> ticketToChoose) {
        this.ticketToChoose = ticketToChoose;
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
}
