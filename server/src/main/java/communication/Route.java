package communication;

/**
 * Created by jalton on 10/24/18.
 */

public class Route {
    private City city1;
    private City city2;
    private Integer length;
    private GameColor cardColor;
    private Player claimedBy = null;
    private double[] midPoint;

    public Route(City city1, City city2, int length, GameColor cardColor) {
        this.city1 = city1;
        this.city2 = city2;
        this.length = length;
        this.cardColor = cardColor;

        midPoint = new double[] {0.0,0.0};

        midPoint[0] = (city1.getLat()+city2.getLat())/2.0;
        midPoint[1] = (city1.getLng()+city2.getLng())/2.0;
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

    public Integer getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public GameColor getCardColor() {
        return cardColor;
    }

    public void setCardColor(GameColor cardColor) {
        this.cardColor = cardColor;
    }

    public Player getClaimedBy() {
        return claimedBy;
    }

    public void setClaimedBy(Player claimedBy) {
        this.claimedBy = claimedBy;
    }

    public double[] getMidPoint() {
        return midPoint;
    }
}
