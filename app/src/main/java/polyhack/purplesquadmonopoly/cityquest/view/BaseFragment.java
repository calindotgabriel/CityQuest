package polyhack.purplesquadmonopoly.cityquest.view;

import android.app.Fragment;

import polyhack.purplesquadmonopoly.cityquest.activities.MainActivity;

/**
 * Created by motan on 06.12.2015.
 */
public class BaseFragment extends Fragment{

    /**
     * The fragment must have as a parent the MainActivity.
     * @param title title of the actionBar
     */
    protected void setActionBarTitle(String title) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.getSupportActionBar().setTitle(title);
    }

}
