package polyhack.purplesquadmonopoly.cityquest;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import polyhack.purplesquadmonopoly.cityquest.utils.PermissionUtils;

/**
 * Created by motan on 05.12.2015.
 */
public class MainActivity extends AppCompatActivity {

    private String TAG = this.getClass().getCanonicalName();

    private Location mLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocation = new Location(this);
        mLocation.getLastKnown();

        final ListFragment listFragment = new ListFragment();
        final FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_frame, listFragment)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        PermissionUtils.checkGPSPermission(this);
        mLocation.connectAPI();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mLocation.stopLocationUpdates();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocation.disconnectAPI();
    }

}
