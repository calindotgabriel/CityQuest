package polyhack.purplesquadmonopoly.cityquest.utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import polyhack.purplesquadmonopoly.cityquest.model.Spot;

/**
 * Created by motan on 06.12.2015.
 */
public class AdapterUtils {

    public static List<LatLng> waypointsForSpots(List<Spot> spots) {
        List<LatLng> result = new ArrayList<>();
        for (Spot spot : spots) {
            result.add(spot.getLatLng());
        }
        return result;
    }
}
