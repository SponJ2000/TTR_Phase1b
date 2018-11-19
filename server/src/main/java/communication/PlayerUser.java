package communication;

import java.util.ArrayList;

/**
 * Created by urimaj on 11/9/18.
 */

public class PlayerUser extends Player implements IPlayer{

    private ArrayList<Ticket> tickets;
    private ArrayList<Card> cards;
    private ArrayList<Ticket> ticketToChoose;
    private ArrayList<Card> cardToChoose;

    public PlayerUser() {
        tickets = new ArrayList<>();
        cards = new ArrayList<>();
        ticketToChoose = new ArrayList<>();
        cardToChoose = new ArrayList<>();
    }

    public PlayerUser(String playerName) {
        super(playerName);
        tickets = new ArrayList<>();
        cards = new ArrayList<>();
        ticketToChoose = new ArrayList<>();
        cardToChoose = new ArrayList<>();
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
        tickets.addAll(moreTickets);
//        System.out.println("Tickets number changed : " + tickets.size());
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
//        System.out.println("Tickets number changed : " + tickets.size());
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
        cards.addAll(moreCards);
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public boolean useCards(GameColor color, int number) {
        int i, j = 0;
        for(i = 0; i < cards.size(); i++) {
            if(cards.get(i).getColor().equals(color)) {
                ++j;
            }
        }
        if(j >= number) {
            i = 0;
            while(i < cards.size() && number > 0) {
                if(cards.get(i).getColor().equals(color)) {
                    cards.remove(i);
                    --number;
                }else {
                    ++i;
                }
            }
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int getCardNum() {
        return cards.size();
    }

    @Override
    public int getTicketNum() {
        return tickets.size();
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
