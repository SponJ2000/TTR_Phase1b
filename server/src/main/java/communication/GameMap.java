package communication;

import java.util.ArrayList;
import java.util.List;

import static communication.GameColor.*;

/**
 * Created by jalton on 10/24/18.
 */

public class GameMap {
    private List<City> cities;
    private List<Route> routes;

    public GameMap() {

        City Atlanta = new City("Atlanta", 33, -84);
        City Boston = new City("Boston", 42, -71);
        City Calgary = new City("Calgary", 51, -114);
        City Charleston = new City("Charleston", 32, -81);
        City Chicago = new City("Chicago", 41, -87);
        City Dallas = new City("Dallas", 32, -96);
        City Denver = new City("Denver", 39, -105);
        City Duluth = new City("Duluth", 46, -92);
        City El_Paso = new City("El_Paso", 31, -106);
        City Houston = new City("Houston", 29, -95);
        City Helena = new City("Helena", 46, -112);
        City Kansas_City = new City("Kansas_City", 39, -94);
        City Las_Vegas = new City("Las_Vegas", 36, -115);
        City Little_Rock = new City("Little_Rock", 34, -92);
        City Los_Angeles = new City("Los_Angeles", 34, -118);
        City Miami = new City("Miami", 25, -80);
        City Montreal = new City("Montreal", 45, -73);
        City Nashville = new City("Nashville", 36, -86);
        City New_Orleans = new City("New_Orleans", 29, -90);
        City New_York = new City("New_York", 40, -73);
        City Omaha = new City("Omaha", 41, -95);
        City Oklahoma_City = new City("Oklahoma_City", 35, -97);
        City Phoenix = new City("Phoenix", 33, -112);
        City Pittsburgh = new City("Pittsburgh", 40, -79);
        City Portland = new City("Portland", 45, -122);
        City Raleigh = new City("Raleigh", 35, -78);
        City Saint_Louis = new City("Saint_Louis", 38, -90);
        City Salt_Lake_City = new City("Salt_Lake_City", 40, -111);
        City San_Francisco = new City("San_Francisco", 37, -122);
        City Santa_Fe = new City("Santa_Fe", 35, -105);
        City Sault_Ste_Marie = new City("Sault_Ste._Marie", 46, -84);
        City Seattle = new City("Seattle", 47, -122);
        City Toronto = new City("Toronto", 43, -79);
        City Vancouver = new City("Vancouver", 49, -123);
        City Washington = new City("Washington", 38, -77);
        City Winnipeg = new City("Winnipeg", 49, -97);

        cities = new ArrayList<>();
        cities.add(Atlanta);
        cities.add(Boston);
        cities.add(Calgary);
        cities.add(Charleston);
        cities.add(Chicago);
        cities.add(Dallas);
        cities.add(Denver);
        cities.add(Duluth);
        cities.add(El_Paso);
        cities.add(Houston);
        cities.add(Helena);
        cities.add(Kansas_City);
        cities.add(Las_Vegas);
        cities.add(Little_Rock);
        cities.add(Los_Angeles);
        cities.add(Miami);
        cities.add(Montreal);
        cities.add(Nashville);
        cities.add(New_Orleans);
        cities.add(New_York);
        cities.add(Omaha);
        cities.add(Oklahoma_City);
        cities.add(Phoenix);
        cities.add(Pittsburgh);
        cities.add(Portland);
        cities.add(Raleigh);
        cities.add(Saint_Louis);
        cities.add(Salt_Lake_City);
        cities.add(San_Francisco);
        cities.add(Santa_Fe);
        cities.add(Sault_Ste_Marie);
        cities.add(Seattle);
        cities.add(Toronto);
        cities.add(Vancouver);
        cities.add(Washington);
        cities.add(Winnipeg);

        routes = new ArrayList<>();

        routes.add(new Route(Atlanta, Charleston, 2, GREY));
        routes.add(new Route(Atlanta, Miami, 5, BLUE));
        routes.add(new Route(Atlanta, Nashville, 1, GREY));
        routes.add(new Route(Atlanta, New_Orleans, 2, ORANGE));

        routes.add(new Route(Pittsburgh, Washington, 2, GREY));
        routes.add(new Route(Pittsburgh, Raleigh, 2, GREY));
        routes.add(new Route(Pittsburgh, Toronto, 2, GREY));

    }

    public List<City> getCities() {
        return cities;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void claimRoute(Route route, Player player) {
        Route r = routes.get(routes.indexOf(route));

        r.setClaimedBy(player);
        r.setCardColor(player.getPlayerColor());
    }
}
