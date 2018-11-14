package communication;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameFactory {

    private static GameFactory SINGLETON;
    private static HashMap<String, City> cityMap;
    private static ArrayList<Ticket> allTickets;
    private static ArrayList<Route> allRoutes;

    private GameFactory() {
        cityMap = new HashMap<>();
        cityMap.put("Atlanta", new City("Atlanta", 33, -84));
        cityMap.put("Boston", new City("Boston", 42, -71));
        cityMap.put("Calgary", new City("Calgary", 51, -114));
        cityMap.put("Charleston", new City("Charleston", 32, -81));
        cityMap.put("Chicago", new City("Chicago", 41, -87));
        cityMap.put("Dallas", new City("Dallas", 32, -96));
        cityMap.put("Denver", new City("Denver", 39, -105));
        cityMap.put("Duluth", new City("Duluth", 46, -92));
        cityMap.put("El_Paso", new City("El_Paso", 31, -106));
        cityMap.put("Houston", new City("Houston", 29, -95));
        cityMap.put("Helena", new City("Helena", 46, -112));
        cityMap.put("Kansas_City", new City("Kansas_City", 39, -94));
        cityMap.put("Las_Vegas", new City("Las_Vegas", 36, -115));
        cityMap.put("Little_Rock", new City("Little_Rock", 34, -92));
        cityMap.put("Los_Angeles", new City("Los_Angeles", 34, -118));
        cityMap.put("Miami", new City("Miami", 25, -80));
        cityMap.put("Montreal", new City("Montreal", 45, -73));
        cityMap.put("Nashville", new City("Nashville", 36, -86));
        cityMap.put("New_Orleans", new City("New_Orleans", 29, -90));
        cityMap.put("New_York", new City("New_York", 40, -73));
        cityMap.put("Omaha", new City("Omaha", 41, -95));
        cityMap.put("Oklahoma_City", new City("Oklahoma_City", 35, -97));
        cityMap.put("Phoenix", new City("Phoenix", 33, -112));
        cityMap.put("Pittsburgh", new City("Pittsburgh", 40, -79));
        cityMap.put("Portland", new City("Portland", 45, -122));
        cityMap.put("Raleigh", new City("Raleigh", 35, -78));
        cityMap.put("Saint_Louis", new City("Saint_Louis", 38, -90));
        cityMap.put("Salt_Lake_City", new City("Salt_Lake_City", 40, -111));
        cityMap.put("San_Francisco", new City("San_Francisco", 37, -122));
        cityMap.put("Santa_Fe", new City("Santa_Fe", 35, -105));
        cityMap.put("Sault_Ste_Marie", new City("Sault_Ste_Marie", 46, -84));
        cityMap.put("Seattle", new City("Seattle", 47, -122));
        cityMap.put("Toronto", new City("Toronto", 43, -79));
        cityMap.put("Vancouver", new City("Vancouver", 49, -123));
        cityMap.put("Washington", new City("Washington", 38, -77));
        cityMap.put("Winnipeg", new City("Winnipeg", 49, -97));

        allTickets = new ArrayList<>();
//        Denver to El Paso (4)
        allTickets.add(new Ticket(cityMap.get("Denver"), cityMap.get("El_Paso"), 4));
//        Kansas City to Houston (5)
        allTickets.add(new Ticket(cityMap.get("Kansas_City"), cityMap.get("Houston"), 5));
//        New York to Atlanta (6)
        allTickets.add(new Ticket(cityMap.get("New_York"), cityMap.get("Atlanta"), 6));
//        Chicago to New Orleans (7), Calgary to Salt Lake City (7)
        allTickets.add(new Ticket(cityMap.get("Chicago"), cityMap.get("New_Orleans"), 7));
        allTickets.add(new Ticket(cityMap.get("Calgary"), cityMap.get("Salt_Lake_City"), 7));
//        Helena to Los Angeles (8), Duluth to Houston (8), Sault Ste Marie to Nashville (8)
        allTickets.add(new Ticket(cityMap.get("Helena"), cityMap.get("Los_Angeles"), 8));
        allTickets.add(new Ticket(cityMap.get("Duluth"), cityMap.get("Houston"), 8));
        allTickets.add(new Ticket(cityMap.get("Sault_Ste_Marie"), cityMap.get("Nashville"), 8));
//        Montreal to Atlanta (9), Sault Ste. Marie to Oklahoma City (9), Seattle to Los Angeles (9), Chicago to Santa Fe (9)
        allTickets.add(new Ticket(cityMap.get("Montreal"), cityMap.get("Atlanta"), 9));
        allTickets.add(new Ticket(cityMap.get("Sault_Ste_Marie"), cityMap.get("Oklahoma_City"), 9));
        allTickets.add(new Ticket(cityMap.get("Seattle"), cityMap.get("Los Angeles"), 9));
        allTickets.add(new Ticket(cityMap.get("Chicago"), cityMap.get("Santa_Fe"), 9));
//        Duluth to El Paso (10), Toronto to Miami (10)
        allTickets.add(new Ticket(cityMap.get("Duluth"), cityMap.get("El_Paso"), 10));
        allTickets.add(new Ticket(cityMap.get("Toronto"), cityMap.get("Miami"), 10));
//        Portland to Phoenix(11), Dallas to New York City (11), Denver to Pittsburgh (11), Winnipeg to Little Rock (11)
        allTickets.add(new Ticket(cityMap.get("Portland"), cityMap.get("Phoenix"), 11));
        allTickets.add(new Ticket(cityMap.get("Dallas"), cityMap.get("New_York"), 11));
        allTickets.add(new Ticket(cityMap.get("Denver"), cityMap.get("Pittsburgh"), 11));
        allTickets.add(new Ticket(cityMap.get("Winnipeg"), cityMap.get("Little_Rock"), 11));
//        Winnipeg to Houston (12), Boston to Miami (12)
        allTickets.add(new Ticket(cityMap.get("Winnipeg"), cityMap.get("Houston"), 12));
        allTickets.add(new Ticket(cityMap.get("Boston"), cityMap.get("Miami"), 12));
//        Vancouver to Santa Fe (13), Calgary to Phoenix(13), Montreal to New Orleans (13)
        allTickets.add(new Ticket(cityMap.get("Vancouver"), cityMap.get("Santa_Fe"), 13));
        allTickets.add(new Ticket(cityMap.get("Calgary"), cityMap.get("Phoenix"), 13));
        allTickets.add(new Ticket(cityMap.get("Montreal"), cityMap.get("New_Orleans"), 13));
//        Los Angeles to Chicago (16)
        allTickets.add(new Ticket(cityMap.get("Los_Angeles"), cityMap.get("Chicago"), 16));
//        San Francisco to Atlanta (17), Portland to Nashville (17)
        allTickets.add(new Ticket(cityMap.get("San_Francisco"), cityMap.get("Atlanta"), 17));
        allTickets.add(new Ticket(cityMap.get("Portland"), cityMap.get("Nashville"), 17));
//        Vancouver to Montréal (20), Los Angeles to Miami (20)
        allTickets.add(new Ticket(cityMap.get("Vancouver"), cityMap.get("Montreal"), 20));
        allTickets.add(new Ticket(cityMap.get("Los_Angeles"), cityMap.get("Miami"), 20));
//        Los Angeles to New York City (21)
        allTickets.add(new Ticket(cityMap.get("Los_Angeles"), cityMap.get("New_York"), 21));
//        Seattle to New York (22)
        allTickets.add(new Ticket(cityMap.get("Seattle"), cityMap.get("New_York"), 22));

        allRoutes = new ArrayList<>();
        allRoutes.add(new Route(cityMap.get("Vancouver"), cityMap.get("Calgary"), 3, GameColor.GREY));
    }

    public GameFactory getInstance() {
        if(SINGLETON == null) {
            SINGLETON = new GameFactory();
        }
        return SINGLETON;
    }

    public HashMap<String, City> getCities() {
        return cityMap;
    }

    public Map<GameColor, Integer> getCards() {
        Map<GameColor, Integer> map = new HashMap<>();

        for(GameColor color:GameColor.values()) {
            map.put(color, 12);
        }
        map.put(GameColor.LOCOMOTIVE, 14);

        return map;
    }

    public ArrayList<Ticket> getTickets() {
        return allTickets;
    }

    public ArrayList<Route> getRoutes() {
        return allRoutes;
    }

}
