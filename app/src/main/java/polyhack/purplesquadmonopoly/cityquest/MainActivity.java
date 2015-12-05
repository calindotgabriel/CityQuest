package polyhack.purplesquadmonopoly.cityquest;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_logout:
                LoginManager.getInstance().logOut();
                UserManagement.removeUser(this);
                startLoginActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startLoginActivity() {
        Intent startActivityIntent = new Intent(this, LoginActivity.class);
        startActivity(startActivityIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
