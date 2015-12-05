package polyhack.purplesquadmonopoly.cityquest;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import polyhack.purplesquadmonopoly.cityquest.model.view.EmptyRecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

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

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        final JourneyAdapter adapter = new JourneyAdapter(getActivity(), MockFactory.getJourneys());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);

    }
}
