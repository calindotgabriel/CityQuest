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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
