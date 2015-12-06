package polyhack.purplesquadmonopoly.cityquest;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import polyhack.purplesquadmonopoly.cityquest.model.BaseFragment;
import polyhack.purplesquadmonopoly.cityquest.model.Journey;
import polyhack.purplesquadmonopoly.cityquest.model.view.EmptyRecyclerView;
import polyhack.purplesquadmonopoly.cityquest.service.CityQuestService;
import polyhack.purplesquadmonopoly.cityquest.service.ServiceGenerator;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends BaseFragment {

    private String TAG = this.getClass().getCanonicalName();

    @Bind(R.id.rv_fl)
    EmptyRecyclerView mRecyclerView;

    public ListFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setActionBarTitle("Choose your adventure!");

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        final JourneyAdapter adapter = new JourneyAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Journey journey = adapter.getJourneyByPosition(position);
                        switchToDetailFragment(journey);
                    }
                })
        );

        final CityQuestService service = ServiceGenerator.createService(CityQuestService.class);
        final Call<List<Journey>> notesCall = service.getAllJournies();
        notesCall.enqueue(new Callback<List<Journey>>() {
            @Override
            public void onResponse(Response<List<Journey>> response, Retrofit retrofit) {
                adapter.animateTo(response.body());
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

    private void switchToDetailFragment(Journey journey) {
        getActivity().getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                        R.anim.enter_from_left, R.anim.exit_to_left)
                .replace(R.id.main_frame, DetailFragment.newInstance(journey))
                .addToBackStack(null)
                .commit();
    }

}
