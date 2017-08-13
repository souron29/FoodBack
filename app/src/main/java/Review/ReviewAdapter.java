package Review;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dexlabs.extraloyaljuice.R;

import GeneralCode.AdvanceViewHolder;


class ReviewAdapter extends RecyclerView.Adapter {
    Context mContext;

    ReviewAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recycler_row_review_list, parent, false);
        rHolder holder = new rHolder(v);
        v.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
        rHolder h = (rHolder) holder;

        Long reviewID = ReviewDriver.reviewID.get(pos);
        ReviewStructure rs = ReviewDriver.reviewList.get(reviewID);
        h.date.setText(rs.getDate());
        h.name.setText(rs.getName());
        String temp = "Service:" + rs.getService_rating();
        h.service.setText(temp);
        temp = "Ambiance:" + rs.getAmbiance_rating();
        h.ambiance.setText(temp);
        temp = "Food:" + rs.getFood_rating();
        h.food.setText(temp);
    }

    @Override
    public int getItemCount() {
        return ReviewDriver.reviewID.size();
    }

    void reset() {
        notifyDataSetChanged();
    }

    private class rHolder extends AdvanceViewHolder {
        TextView name, date, ambiance, food, service;

        public rHolder(View itemView) {
            super(itemView);
            name = $(R.id.textview_rr_review_name);
            date = $(R.id.textview_rr_review_date);
            ambiance = $(R.id.textview_rr_review_ambiance);
            food = $(R.id.textview_rr_review_food);
            service = $(R.id.textview_rr_review_service);
        }
    }
}
