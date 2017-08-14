package Review;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dexlabs.foodback.R;

import GeneralCode.AdvanceFragment;
import GeneralCode.AsyncFunctions;
import GeneralCode.OnGetWorkListener;
import database.DBMethods;
import database.DBSchema;

public class ReviewList extends AdvanceFragment implements OnGetWorkListener {
    private RecyclerView mRecyclerView;
    private ReviewAdapter mAdapter;
    private TextView defaultScreen;

    public ReviewList() {
        // Required empty public constructor
    }

    @Override
    protected int setOptionsMenu() {
        return R.menu.menu_review_list;
    }

    @Override
    public void fragmentReset() {
        AsyncFunctions asyncFunctions = new AsyncFunctions(getActivity());
        asyncFunctions.loadReviews(this);
        mAdapter.reset();
    }

    @Override
    protected int setFragmentLayoutID() {
        return R.layout.fragment_review_list;
    }

    @Override
    protected void initializeViews() {
        mRecyclerView = $(R.id.recycler_view_review_list);
        defaultScreen = $(R.id.tv_frag_def);
    }

    @Override
    protected void afterViewsInitialized() {
        mAdapter = new ReviewAdapter(getActivity(), this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        AsyncFunctions async = new AsyncFunctions(getActivity());

        async.loadReviews(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_clear_all) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setTitle("Clear All Contents");
            String yes_no = $("string", "yes");
            alertDialog.setPositiveButton(yes_no,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            DBMethods.connect(getActivity());
                            DBMethods.delete(DBSchema.Reviews.TableName, null);
                            fragmentReset();
                            DBMethods.disconnect(null);
                        }
                    });
            yes_no = $("string", "no");
            alertDialog.setNegativeButton(yes_no,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void workSomething(int work_id) {
        if (work_id == RESTART) mAdapter.reset();
        if (work_id == SHOW) show();
        if (work_id == HIDE) hide();
    }

    void show() {
        defaultScreen.setVisibility(View.VISIBLE);
    }

    @Override
    protected void hide() {
        defaultScreen.setVisibility(View.GONE);
    }
}
