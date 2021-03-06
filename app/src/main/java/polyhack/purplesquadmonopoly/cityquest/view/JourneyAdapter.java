package polyhack.purplesquadmonopoly.cityquest.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import polyhack.purplesquadmonopoly.cityquest.R;
import polyhack.purplesquadmonopoly.cityquest.model.Journey;

/**
 * Created by motan on 04.12.2015.
 */
public class JourneyAdapter extends RecyclerView.Adapter<JourneyAdapter.ViewHolder> {

    private List<Journey> mJournies;
    private Context mContext;

    public JourneyAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public JourneyAdapter(Context context, List<Journey> journeys) {
        this.mContext = context;
        this.mJournies = new ArrayList<>(journeys);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.rv_journey_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Journey journey = mJournies.get(position);
        if (journey.isActive()) {
            holder.activeTextView.setVisibility(View.VISIBLE);
        } else {
            holder.activeTextView.setVisibility(View.GONE);
        }
        holder.mNameTv.setText(journey.getName());
        holder.mDescTv.setText(journey.getDesc()); //TODO
        Picasso.with(mContext).load(journey.getImgURL()).into(holder.mImgVw);
    }

    @Override
    public int getItemCount() {
        if (mJournies == null) {
            return 0;
        }
        return mJournies.size();
    }

    public void animateTo(List<Journey> journies) {
        this.mJournies = journies;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.rvi_name)
        TextView mNameTv;

        @Bind(R.id.rvi_desc)
        TextView mDescTv;

        @Bind(R.id.rvi_img)
        ImageView mImgVw;

        @Bind(R.id.active_journey_text_view)
        TextView activeTextView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public Journey getJourneyByPosition(int position) {
        if (position < mJournies.size()) {
            return mJournies.get(position);
        }
        return null;
    }

}
