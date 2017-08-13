package GeneralCode;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class AdvanceViewHolder extends RecyclerView.ViewHolder {
    private View view;

    public AdvanceViewHolder(View itemView) {
        super(itemView);
        this.view = itemView;
    }

    protected <T extends View> T $(int view_id) {
        return view.findViewById(view_id);
    }
}
