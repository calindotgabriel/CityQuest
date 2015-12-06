package polyhack.purplesquadmonopoly.cityquest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by motan on 06.12.2015.
 */
public class Adventure implements Serializable {

    public Journey journey;
    public List<Spot> spots;

    public Adventure(Journey targetedJourney, ArrayList<Spot> spots) {
        this.journey = targetedJourney;
        this.spots = spots;
    }
}
