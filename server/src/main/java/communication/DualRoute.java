package communication;

/**
 * Created by jalton on 11/14/18.
 *
 * Dual route is the same as route, but it has a special sibling member. This is used to check
 * whether a player has already claimed one route.
 */

public class DualRoute extends Route {

    private DualRoute sibling = null;

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
    }

    public void setSibling(DualRoute sibling) {
        this.sibling = sibling;
    }
}
