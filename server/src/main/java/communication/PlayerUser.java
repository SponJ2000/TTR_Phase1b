package communication;

import java.util.ArrayList;

/**
 * Created by urimaj on 11/9/18.
 */

public class PlayerUser extends Player implements IPlayer{

    private ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    private ArrayList<Card> cards = new ArrayList<Card>();
    private ArrayList<Ticket> ticketToChoose = new ArrayList<Ticket>();
    private ArrayList<Card> cardToChoose = new ArrayList<Card>();

    public PlayerUser() {

    }

    public PlayerUser(String playerName) {
        super(playerName);
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
        System.out.println("Tickets number changed : " + tickets.size());
    }

    public void setTickets(ArrayList<Ticket> tickets) {

        this.tickets = tickets;
        System.out.println("Tickets number changed : " + tickets.size());
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

    public boolean useCards(GameColor color, int number) {
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

    public Integer getCardNum() {
        if(cards != null) {
            return cards.size();
        }
        return 0;
    }

    public Integer getTicketNum() {
        if(tickets != null) {
            return tickets.size();
        }
        return 0;
    }

    public ArrayList<Ticket> getTicketToChoose() {
        return ticketToChoose;
    }

    public void setTicketToChoose(ArrayList<Ticket> ticketToChoose) {
        this.ticketToChoose = ticketToChoose;
    }

    public ArrayList<Card> getCardToChoose() {
        return cardToChoose;
    }

    public void setCardToChoose(ArrayList<Card> cardToChoose) {
        this.cardToChoose = cardToChoose;
    }

    @Override
    public PlayerIdentity getIdentity() {
        return PlayerIdentity.USER;
    }
}
