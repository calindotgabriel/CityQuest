package polyhack.purplesquadmonopoly.cityquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import polyhack.purplesquadmonopoly.cityquest.model.Adventure;
import polyhack.purplesquadmonopoly.cityquest.model.BaseFragment;
import polyhack.purplesquadmonopoly.cityquest.model.Journey;
import polyhack.purplesquadmonopoly.cityquest.model.Spot;
import polyhack.purplesquadmonopoly.cityquest.model.VisitedSpot;
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

    private ArrayList<Spot> mSpots = new ArrayList<>();
    private List<VisitedSpot> mSpotsVisited;
    private AdventurePersistence mAdventurePersistence;

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

        mAdventurePersistence = new AdventurePersistence(getActivity());

        final Bundle args = getArguments();
        if (args != null) {
            Journey journey = (Journey) args.getSerializable(KEY_JOURNEY);
            this.setTargetedNote(journey);
            populateSpots(journey.getId(), UserManagement.getUser(getActivity()).get_id());
        }

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mSpotRecyclerView.setLayoutManager(layoutManager);
        adapter = new SpotAdapter(getActivity());
        mSpotRecyclerView.setAdapter(adapter);

    }

    private void populateSpots(String journeyId, String userId) {
        final CityQuestService service = ServiceGenerator.createService(CityQuestService.class);
        final Call<List<VisitedSpot>> spotsCall = service.getSpotsForUserJourney(userId, journeyId);
        spotsCall.enqueue(new Callback<List<VisitedSpot>>() {
            @Override
            public void onResponse(Response<List<VisitedSpot>> response, Retrofit retrofit) {
                mSpotsVisited = response.body();
                convertToSpot();
                adapter.animateTo(mSpotsVisited);
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

    //TODO: refactor spots
    private void convertToSpot() {
        for (VisitedSpot visitedSpot : mSpotsVisited) {
            mSpots.add(visitedSpot.getSpot());
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
        mDistanceTextView.setText(targetJourney.getDistance() + " m");
        mDurationTextView.setText(targetJourney.getDuration() + " min");
        Picasso.with(getActivity()).load(targetJourney.getImgURL()).
                fit().centerCrop().into(mJourneyImageView);
    }

    @OnClick(R.id.go_btn)
    void onGoBtnPressed() {

        if (mAdventurePersistence.isAnotherAdventureStarted()) {
            Log.v(TAG, "other spots already persisted");
            final Journey activeJourney = mAdventurePersistence.getActiveAdventure().journey;
            if (mTargetJourney.getId().equals(activeJourney.getId())) {
                Log.v(TAG, "Already doing this journey, should go to map");
                gotToMap();
            } else {
                final String activeJourneyName = activeJourney.getName();
                Log.v(TAG, "Doing " + activeJourneyName + ", different from the one clicked!");
                buildDialog("Changed your mind?",
                            "You're already doing " + activeJourneyName + ", sure you wanna do " + mTargetJourney.getName() + " instead?",
                            "Yes",
                            "No");
            }

        } else {
            buildDialog("Fair warning",
                    "If you choose to go on this adventure, you can't choose another until it's finished. Are you sure?",
                    "Yes",
                    "I'll see others too");
        }
    }

    private void buildDialog(String title, String content, String positive, String negative) {
        new MaterialDialog.Builder(getActivity())
                .title(title)
                .content(content)
                .positiveText(positive)
                .negativeText(negative)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        mAdventurePersistence.save(new Adventure(mTargetJourney, mSpots));
                        gotToMap();
                    }
                })
                .show();
    }

    private void gotToMap() {
        Intent intent = new Intent(getActivity(), MapActivity.class);
        intent.putParcelableArrayListExtra(KEY_SPOTS, mSpots);
        //todo redundant, can get from persistence!
        startActivity(intent);
    }
}
