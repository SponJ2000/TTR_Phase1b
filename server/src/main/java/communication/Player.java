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

    private Integer ticketNum;
    private Integer trainCarNum;
    private color playerColor;


    public color getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(color playerColor) {
        this.playerColor = playerColor;
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

    public String getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }
}
