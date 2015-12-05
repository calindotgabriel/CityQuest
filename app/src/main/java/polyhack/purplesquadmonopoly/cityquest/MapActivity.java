package polyhack.purplesquadmonopoly.cityquest;

import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private String TAG = this.getClass().getCanonicalName();

    private boolean cameraOnMyLocation = false;

    private GoogleMap mMap;
    private LatLng mInitialLocation;
    private GeofenceStore mGeofenceStore;

    private List<Geofence> mGeofences;
    private List<LatLng> mGeofenceCoordinates;
    private List<Float> mGeofenceRadius;

    private LatLng COORDS = new LatLng(46.7551639, 23.5875443);
    private Float RADIUS = 5000f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        mGeofences = new ArrayList<>();
        mGeofenceCoordinates = new ArrayList<>();
        mGeofenceRadius = new ArrayList<>();

        mGeofenceCoordinates.add(COORDS);
        mGeofenceRadius.add(RADIUS);


        mGeofences.add(new Geofence.Builder()
                .setRequestId("MyLocationFence")
                .setCircularRegion(COORDS.latitude, COORDS.longitude, RADIUS)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                // Tells how much time a user can be tagged as 'in the area'
                .setLoiteringDelay(30000)
                .setTransitionTypes(
                        Geofence.GEOFENCE_TRANSITION_ENTER
                                | Geofence.GEOFENCE_TRANSITION_DWELL
                                | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());

        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        mGeofenceStore = new GeofenceStore(this, mGeofences, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.v(TAG, "Location Information\n"
                        + "==========\n"
                        + "Provider:\t" + location.getProvider() + "\n"
                        + "Lat & Long:\t" + location.getLatitude() + ", "
                        + location.getLongitude() + "\n"
                        + "Altitude:\t" + location.getAltitude() + "\n"
                        + "Bearing:\t" + location.getBearing() + "\n"
                        + "Speed:\t\t" + location.getSpeed() + "\n"
                        + "Accuracy:\t" + location.getAccuracy() + "\n");
                Log.v(TAG, "Total of " + mGeofences.size() + " geofences.");

                if (!cameraOnMyLocation) {
                    mInitialLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mInitialLocation, 12f));
                    cameraOnMyLocation = true;
                }
            }
        });

        for (int i = 0; i < mGeofences.size(); i++) {
            drawGeofenceCircle(mGeofenceCoordinates.get(i), mGeofenceRadius.get(i));
            mMap.addMarker(new MarkerOptions()
                    .position(mGeofenceCoordinates.get(i))
                    .title(mGeofences.get(i).getRequestId()));

        }
    }

    private Circle drawGeofenceCircle(LatLng coordinates, float radius) {
        return mMap.addCircle(new CircleOptions().center(coordinates)
                .radius(radius)
                .fillColor(0x40ff0000)
                .strokeColor(Color.TRANSPARENT).strokeWidth(2));
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGeofenceStore.disconnect();
    }
}
