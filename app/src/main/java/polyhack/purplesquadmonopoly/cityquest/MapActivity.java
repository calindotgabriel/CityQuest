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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;

import java.util.ArrayList;
import java.util.List;

import polyhack.purplesquadmonopoly.cityquest.model.MapBoxOnlineTileProvider;
import polyhack.purplesquadmonopoly.cityquest.model.Spot;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final int ACCENT_COLOR_40 = 0x40FF4081;

    private String TAG = this.getClass().getCanonicalName();

    private boolean cameraOnMyLocation = false;

    private GoogleMap mMap;
    private LatLng mInitialLocation;
    private GeofenceStore mGeofenceStore;

    private List<Geofence> mGeofences;
    private ArrayList<Spot> mSpots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        buildGeofences();

        mapFragment.getMapAsync(this);
    }

    private void buildGeofences() {
        mGeofences = new ArrayList<>();
        mSpots = getIntent().getParcelableArrayListExtra(DetailFragment.KEY_SPOTS);


        for (Spot spot : mSpots) {
            mGeofences.add(new Geofence.Builder()
                    .setRequestId(spot.getName())
                    .setCircularRegion(spot.getLat(), spot.getLng(), (float) spot.getRadius() * 10)
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    // Tells how much time a user can be tagged as 'in the area'
                    .setLoiteringDelay(30000)
                    .setTransitionTypes(
                            Geofence.GEOFENCE_TRANSITION_ENTER
                                    | Geofence.GEOFENCE_TRANSITION_DWELL
                                    | Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build());
        }


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
//        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

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

        for (Spot spot : mSpots) {
            final LatLng latLng = spot.getLatLng();
            drawGeofenceCircle(latLng, (float) spot.getRadius());
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
//                    .icon(BitmapDescriptorFactory.defaultMarker()) TODO design new marker
                    .title(spot.getName()));
        }
    }

    private Circle drawGeofenceCircle(LatLng coordinates, float radius) {
        return mMap.addCircle(new CircleOptions().center(coordinates)
                .radius(radius * 10) //todo
                .fillColor(ACCENT_COLOR_40)
                .strokeColor(Color.TRANSPARENT)
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGeofenceStore.disconnect();
    }
}
