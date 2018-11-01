package communication;

/**
 * Created by jalton on 10/24/18.
 */

public class Card {

    private GameColor color;

    public Card(GameColor color) {
        this.color = color;
    }

    public GameColor getColor() {
        return color;
    }

    public void setColor(GameColor color) {
        this.color = color;
    }

}
