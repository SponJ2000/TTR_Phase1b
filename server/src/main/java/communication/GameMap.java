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
        cities = GameFactory.getInstance().getCities();
        routes = GameFactory.getInstance().getRoutes();
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
        r.setColor(player.getPlayerColor());
    }

    public Route getRouteByRouteId(String routeID) {
        for(Route r: routes) {
            if (r.getRouteID().equals(routeID)) {
                return r;
            }
        }
        return null;
    }
}
