package polyhack.purplesquadmonopoly.cityquest;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by motan on 05.12.2015.
 */
public class GeofenceStore implements GoogleApiClient.ConnectionCallbacks, ResultCallback<Status>, GoogleApiClient.OnConnectionFailedListener {

    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private GoogleApiClient mGoogleApiClient;

    private LocationListener mLocationListener;

    private PendingIntent mPendingIntent;

    private List<Geofence> mGeofences;

    private LocationRequest mLocationRequest;


    public GeofenceStore(Context mContext, List<Geofence> mGeofences, LocationListener locationListener) {
        this.mContext = mContext;
        this.mGeofences = mGeofences;
        this.mLocationListener = locationListener;

        // Build a new GoogleApiClient, specify that we want to use LocationServices
        // by adding the API to the client, specify the connection callbacks are in
        // this class as well as the OnConnectionFailed method.
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mGoogleApiClient.connect();
    }

    public void connectGoogleApiIfNeeded() {
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.v(TAG, "API Connected");
        toggleGeofencing();
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, mLocationListener);
    }

    public void toggleGeofencing() {
        // Creates a request with given geofences
        GeofencingRequest mGeofencingRequest =
                new GeofencingRequest.Builder().addGeofences(mGeofences).build();

        mPendingIntent = createRequestPendingIntent();

        // Submitting the request to monitor geofences.
        PendingResult<Status> pendingResult = LocationServices.GeofencingApi
                .addGeofences(mGoogleApiClient, mGeofencingRequest,
                        mPendingIntent);
        // Set the result callbacks listener to this class.
        pendingResult.setResultCallback(this);
    }

    /**
     * This creates a PendingIntent that is to be fired when geofence transitions
     * take place. In this instance, we are using an IntentService to handle the
     * transitions.
     *
     * @return A PendingIntent that will handle geofence transitions.
     */
    private PendingIntent createRequestPendingIntent() {
        if (mPendingIntent == null) {
            Log.v(TAG, "Creating PendingIntent");
            Intent intent = new Intent(mContext, GeofenceIntentService.class);
            mPendingIntent = PendingIntent.getService(mContext, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }

        return mPendingIntent;
    }


    @Override
    public void onConnectionSuspended(int i) { }


    @Override
    public void onResult(Status result) {
        if (result.isSuccess()) {
            Log.v(TAG, "Success!");
        } else if (result.hasResolution()) {
            // TODO Handle resolution
        } else if (result.isCanceled()) {
            Log.v(TAG, "Canceled");
        } else if (result.isInterrupted()) {
            Log.v(TAG, "Interrupted");
        } else {

        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.v(TAG, "Connection Failed");
    }


    public void disconnect() {
        mGoogleApiClient.disconnect();
    }
}
