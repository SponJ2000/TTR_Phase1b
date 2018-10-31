package communication;

/**
 * Created by jalton on 10/24/18.
 */

public class Card {

    private CardColor color;

    public Card(CardColor color) {
        this.color = color;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

}
