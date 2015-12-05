package polyhack.purplesquadmonopoly.cityquest;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by motan on 05.12.2015.
 */
public class Location implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

    private String TAG = this.getClass().getCanonicalName();
    private static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 200;

    private LocationRequest mLocationRequest;

    private GoogleApiClient mGoogleApiClient;
    private Activity mActivity;
    private LocationListener mLocationListener;
    private android.location.Location mLastLocation;

    public Location(Activity mActivity) {
        this.mActivity = mActivity;
        if (checkGooglePlayServices()) {
            buildGoogleApiClient();
        }
    }

    private boolean checkGooglePlayServices() {
        int checkGooglePlayServices = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(mActivity);
        if (checkGooglePlayServices != ConnectionResult.SUCCESS) {
        /*
        * Google Play Services is missing or update is required
		*  return code could be
		* SUCCESS,
		* SERVICE_MISSING, SERVICE_VERSION_UPDATE_REQUIRED,
		* SERVICE_DISABLED, SERVICE_INVALID.
		*/
            GooglePlayServicesUtil.getErrorDialog(checkGooglePlayServices,
                    mActivity, REQUEST_CODE_RECOVER_PLAY_SERVICES).show();

            return false;
        }
        return true;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void connectAPI() {
        if (!mGoogleApiClient.isConnecting() &&
                !mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    public void createLocationRequest(int interval, int fastestInterval) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(interval);
        mLocationRequest.setFastestInterval(fastestInterval);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void startLocationUpdates(LocationListener locationListener) {
        this.mLocationListener = locationListener;
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, locationListener);
    }

    protected void stopLocationUpdates() {
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, mLocationListener);
        }
    }

    public void disconnectAPI() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.v(TAG, "Location API connected");
        createLocationRequest(20000, 5000);
        startLocationUpdates(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        mLastLocation = location;
    }

    public android.location.Location getLastKnown() {
        return mLastLocation;
    }
}
