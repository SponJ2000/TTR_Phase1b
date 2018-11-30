package communication;

/**
 * Created by jalton on 11/14/18.
 *
 * Dual route is the same as route, but it has a special sibling member. This is used to check
 * whether a player has already claimed one route.
 */

public class DualRoute extends Route {

    private String sibling;
    private String dualRouteID;
    private boolean isSibClaimed;

    private double[] start;
    private double[] end;

    public DualRoute(City city1, City city2, int length, GameColor color, boolean first, double[] offset) {
        super(city1, city2, length, color);
        setDual(true);
        sibling = null;
        isSibClaimed = false;

        start = new double[2];
        start[0] = city1.getLat();
        start[1] = city1.getLng();
        end = new double[2];
        end[0] = city2.getLat();
        end[1] = city2.getLng();

        double[] mid = getMidPoint();

        if(offset != null) {
            if (first) {
                start[0] += offset[0];
                end[0] += offset[0];
                mid[0] += offset[0];

                start[1] += offset[1];
                end[1] += offset[1];
                mid[1] += offset[1];
                setMidPoint(mid);
            }
            else {
                start[0] -= offset[0];
                end[0] -= offset[0];
                mid[0] -= offset[0];

                start[1] -= offset[1];
                end[1] -= offset[1];
                mid[1] -= offset[1];
                setMidPoint(mid);
            }
        }

        if (first) {
            dualRouteID = city1.getName() + "-" + city2.getName();
        }
        else {
            dualRouteID = city1.getName() + "-" + city2.getName() + "2";
        }

    }

    @Override
    public double[] getStartPos() {
        return start;
    }

    @Override
    public double[] getEndPos() {
        return end;
    }

    public void setSibling(String sibling) {
        this.sibling = sibling;
    }

    public String getSibling() {
        return sibling;
    }

    public String getDualRouteID() {
        return dualRouteID;
    }

    public void setDualRouteID(String dualRouteID) {
        this.dualRouteID = dualRouteID;
    }

    public void setSibClaimed(boolean b) {
        isSibClaimed = b;
    }

    public boolean isSibClaimed() {
        return isSibClaimed;
    }

}
