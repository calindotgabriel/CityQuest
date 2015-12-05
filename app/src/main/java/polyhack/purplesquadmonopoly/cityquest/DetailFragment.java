package polyhack.purplesquadmonopoly.cityquest;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import polyhack.purplesquadmonopoly.cityquest.model.Journey;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    public static final String KEY_JOURNEY = "serializable_journey_key";
    private Journey mTargetJourney;

    @Bind(R.id.journey_iv)
    ImageView mJourneyImageView;

    @Bind(R.id.description_tv)
    TextView mDescriptionTextView;

    @Bind(R.id.name_tv)
    TextView mNameTextView;

    @Bind(R.id.distance_tv)
    TextView mDistanceTextView;

    @Bind(R.id.duration_tv)
    TextView mDurationTextView;

    public static DetailFragment newInstance(Journey journey) {
        DetailFragment fragment = new DetailFragment();
        if (journey != null) {
            Bundle args = new Bundle();
            args.putSerializable(KEY_JOURNEY, journey);
            fragment.setArguments(args);
        }
        return fragment;
    }

    public DetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Bundle args = getArguments();
        if (args != null) {
            Journey journey = (Journey) args.getSerializable(KEY_JOURNEY);
            this.setTargetedNote(journey);
        }
    }

    public void setTargetedNote(Journey targetJourney) {
        this.mTargetJourney = targetJourney;
        fillFields(targetJourney);
    }

    private void fillFields(Journey targetJourney) {
        mDescriptionTextView.setText(targetJourney.getDesc());
        mNameTextView.setText(targetJourney.getName());
        mDistanceTextView.setText(targetJourney.getDistance() + " km");
        mDurationTextView.setText(targetJourney.getDuration() + " min");
        Picasso.with(getActivity()).load(targetJourney.getImgURL()).
                fit().centerCrop().into(mJourneyImageView);
    }
}
