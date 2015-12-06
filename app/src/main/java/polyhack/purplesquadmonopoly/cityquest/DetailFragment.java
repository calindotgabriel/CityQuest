package polyhack.purplesquadmonopoly.cityquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import polyhack.purplesquadmonopoly.cityquest.model.BaseFragment;
import polyhack.purplesquadmonopoly.cityquest.model.Journey;
import polyhack.purplesquadmonopoly.cityquest.model.Spot;
import polyhack.purplesquadmonopoly.cityquest.service.CityQuestService;
import polyhack.purplesquadmonopoly.cityquest.service.ServiceGenerator;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;



public class DetailFragment extends BaseFragment {

    public static final String TAG = DetailFragment.class.getSimpleName();
    public static final String KEY_JOURNEY = "serializable_journey_key";
    public static final String KEY_SPOTS = "spots";
    private Journey mTargetJourney;
    private SpotAdapter adapter;

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

    @Bind(R.id.spot_recycler_view)
    RecyclerView mSpotRecyclerView;

    private ArrayList<Spot> mSpots;

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
            populateSpots(journey.get_id());
        }

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mSpotRecyclerView.setLayoutManager(layoutManager);
        adapter = new SpotAdapter(getActivity());
        mSpotRecyclerView.setAdapter(adapter);

    }

    private void populateSpots(String journeyId) {
        final CityQuestService service = ServiceGenerator.createService(CityQuestService.class);
        final Call<List<Spot>> spotsCall = service.getSpotsForJourney(journeyId);
        spotsCall.enqueue(new Callback<List<Spot>>() {
            @Override
            public void onResponse(Response<List<Spot>> response, Retrofit retrofit) {
                mSpots = new ArrayList<Spot>(response.body());
                adapter.animateTo(mSpots);
            }

            @Override
            public void onFailure(Throwable t) {
                final String errorMessage = "Could not contact server: " + t.getMessage();
                Toast.makeText(getActivity(), errorMessage,
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, errorMessage);
            }
        });
    }

    public void setTargetedNote(Journey targetJourney) {
        this.mTargetJourney = targetJourney;
        fillFields(targetJourney);
        setActionBarTitle(mTargetJourney.getName());
    }

    private void fillFields(Journey targetJourney) {
        mDescriptionTextView.setText(targetJourney.getDesc());
        mNameTextView.setText(targetJourney.getName());
        mDistanceTextView.setText(targetJourney.getDistance() + " m");
        mDurationTextView.setText(targetJourney.getDuration() + " min");
        Picasso.with(getActivity()).load(targetJourney.getImgURL()).
                fit().centerCrop().into(mJourneyImageView);
    }

    @OnClick(R.id.go_btn)
    void onGoBtnPressed() {
        Intent intent = new Intent(getActivity(), MapActivity.class);
        intent.putParcelableArrayListExtra(KEY_SPOTS, mSpots);
        startActivity(intent);
    }
}
