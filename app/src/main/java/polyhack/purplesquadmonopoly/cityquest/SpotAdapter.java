package polyhack.purplesquadmonopoly.cityquest;

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
import polyhack.purplesquadmonopoly.cityquest.model.Spot;
import polyhack.purplesquadmonopoly.cityquest.model.VisitedSpot;

/**
 * Created by Ovi on 06-Dec-15.
 */
public class SpotAdapter  extends RecyclerView.Adapter<SpotAdapter.ViewHolder>{

    public static final String VISITED = "Visited";
    public static final String NOT_VISITED = "Not yet visited";

    private List<VisitedSpot> mSpots;
    private Context mContext;

    public SpotAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.spot_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final VisitedSpot visitedSpot = mSpots.get(i);
        final Spot spot = visitedSpot.getSpot();
        if (spot == null) {
            if (i == 0) {
                viewHolder.mSpotTextOver.setText("start");
                viewHolder.mLinkImgUpView.setVisibility(View.GONE);
            }
            if (i == mSpots.size() - 1) {
                viewHolder.mSpotTextOver.setText("finish");
                viewHolder.mLinkImgDownView.setVisibility(View.GONE);
            }
            viewHolder.mSpotImageView.setImageResource(R.drawable.round_shape_accent);
            viewHolder.mVisitedTextView.setVisibility(View.GONE);
        } else {
            viewHolder.mSpotTextView.setText(spot.getName());
            String visited = visitedSpot.isVisited() ? VISITED : NOT_VISITED;
            viewHolder.mVisitedTextView.setText(visited);
            int visibility = visitedSpot.isVisited() ? View.GONE : View.VISIBLE;
            viewHolder.mSpotOverlayView.setVisibility(visibility );
            CircleTransform circleTransform = new CircleTransform();
            Picasso.with(mContext).load(spot.getImgUrl()).transform(circleTransform)
                    .centerCrop().fit().into(viewHolder.mSpotImageView);
        }
    }

    @Override
    public int getItemCount() {
        if (mSpots == null) {
            return 0;
        }
        return mSpots.size();
    }

    public void animateTo(List<VisitedSpot> spots) {
        this.mSpots = new ArrayList<>(spots);

        mSpots.add(0, new VisitedSpot());
        mSpots.add(new VisitedSpot());

        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.spot_image_view)
        ImageView mSpotImageView;

        @Bind(R.id.spot_name_text_view)
        TextView mSpotTextView;

        @Bind(R.id.spot_overlay_view)
        ImageView mSpotOverlayView;

        @Bind(R.id.text_spot)
        TextView mSpotTextOver;

        @Bind(R.id.link_up_view)
        View mLinkImgUpView;

        @Bind(R.id.link_down_view)
        View mLinkImgDownView;

        @Bind(R.id.spot_visited_text_view)
        TextView mVisitedTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
