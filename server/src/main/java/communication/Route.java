package communication;

/**
 * Created by jalton on 10/24/18.
 */

public class Route {
    private City city1;
    private City city2;
    private int length;
    private CardColor cardColor;
    private Player claimedBy = null;

    public Route() {
    }

    public City getCity1() {
        return city1;
    }

    public void setCity1(City city1) {
        this.city1 = city1;
    }

    public City getCity2() {
        return city2;
    }

    public void setCity2(City city2) {
        this.city2 = city2;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public Player getClaimedBy() {
        return claimedBy;
    }

    public void setClaimedBy(Player claimedBy) {
        this.claimedBy = claimedBy;
    }
}
