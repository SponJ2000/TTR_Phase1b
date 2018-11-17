package communication;

/**
 * Created by jalton on 11/14/18.
 *
 * Dual route is the same as route, but it has a special sibling member. This is used to check
 * whether a player has already claimed one route.
 */

public class DualRoute extends Route {

    private String sibling = null;

    public String getSibling() {
        return sibling;
    }

    private String dualRouteID = null;

    public String getDualRouteID() {
        return dualRouteID;
    }

    public void setDualRouteID(String dualRouteID) {
        this.dualRouteID = dualRouteID;
    }

    public DualRoute(City city1, City city2, int length, GameColor color, boolean first) {
        super(city1, city2, length, color);
        if (first) {
            double[] mid = getMidPoint();
            mid[0] += 1;
            mid[1] += 1;
            setMidPoint(mid);
        }
        else {
            double[] mid = getMidPoint();
            mid[0] -= 1;
            mid[1] -= 1;
            setMidPoint(mid);
        }
        if (first) {
            dualRouteID = city1.getName() + "-" + city2.getName();
        }
        else {
            dualRouteID = city1.getName() + "-" + city2.getName() + "2";
        }

    }

    public void setSibling(String sibling) {
        this.sibling = sibling;
    }
}
