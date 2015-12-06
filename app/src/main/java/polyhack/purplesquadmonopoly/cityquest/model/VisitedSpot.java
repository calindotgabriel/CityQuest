package polyhack.purplesquadmonopoly.cityquest.model;

/**
 * Created by Ovi on 06-Dec-15.
 */
public class VisitedSpot {

    private Spot spot;
    private boolean visited;

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
