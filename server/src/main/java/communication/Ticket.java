package communication;

/**
 * Created by jalton on 10/24/18.
 */

public class Ticket {
    private City city1;
    private City city2;
    private int value;

    public Ticket(City city1, City city2, int value) {
        this.city1 = city1;
        this.city2 = city2;
        this.value = value;
    }

    public City getCity1() {
        return city1;
    }

    public City getCity2() {
        return city2;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "city1=" + city1 +
                ", city2=" + city2 +
                ", value=" + value +
                '}';
    }
}
