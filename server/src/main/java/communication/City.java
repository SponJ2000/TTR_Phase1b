package communication;

/**
 * Created by jalton on 10/24/18.
 */

public class City {
    private String name;

    public City(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;

        City city = (City) o;

        return name.equals(city.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
