package communication;

/**
 * Created by urimaj on 11/9/18.
 */

public class PlayerOpponent extends Player {

    private Integer ticketNum;
    private Integer trainCarNum;
    private Integer cardNum;


    public PlayerOpponent(String playerName, Integer ticketNum, Integer trainCarNum, Integer cardNum) {
        super(playerName);
        this.ticketNum = ticketNum;
        this.trainCarNum = trainCarNum;
        this.cardNum = cardNum;
    }

    public void setTrainCarNum(Integer trainCarNum) {
        this.trainCarNum = trainCarNum;
    }

    public void setCardNum(Integer cardNum) {
        this.cardNum = cardNum;
    }

    public void setTicketNum(Integer ticketNum) {
        this.ticketNum = ticketNum;
    }

    public Integer getTicketNum() {
        return ticketNum;
    }

    public Integer getCardNum() {
        return cardNum;
    }

    public Integer getTrainCarNum() {
        return trainCarNum;
    }
}
