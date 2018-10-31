package communication;

/**
 * Created by jalton on 10/24/18.
 */

public class Card {
    public TrainCarCardColor getColor() {
        return color;
    }

    public void setColor(TrainCarCardColor color) {
        this.color = color;
    }

    private TrainCarCardColor color;

    public Card(TrainCarCardColor color) {
        this.color = color;
    }

}
