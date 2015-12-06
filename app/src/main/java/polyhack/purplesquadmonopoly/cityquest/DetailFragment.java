package polyhack.purplesquadmonopoly.cityquest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import polyhack.purplesquadmonopoly.cityquest.model.BaseFragment;
import polyhack.purplesquadmonopoly.cityquest.model.Journey;


public class DetailFragment extends BaseFragment {

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
        setActionBarTitle(mTargetJourney.getName());
    }

    private void fillFields(Journey targetJourney) {
        mDescriptionTextView.setText(targetJourney.getDesc());
        mNameTextView.setText(targetJourney.getName());
        mDistanceTextView.setText(targetJourney.getDistance() + " km");
        mDurationTextView.setText(targetJourney.getDuration() + " min");
        Picasso.with(getActivity()).load(targetJourney.getImgURL()).
                fit().centerCrop().into(mJourneyImageView);
    }

    @OnClick(R.id.go_btn)
    void onGoBtnPressed() {
        Intent intent = new Intent(getActivity(), MapActivity.class);
        startActivity(intent);
    }
}
