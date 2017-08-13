package Review;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.dexlabs.extraloyaljuice.R;

import GeneralCode.AdvanceFragment;
import GeneralCode.AsyncFunctions;
import GeneralCode.OnGetWorkListener;
import database.DBMethods;
import database.DBSchema;

public class ReviewList extends AdvanceFragment implements OnGetWorkListener {
    private RecyclerView mRecyclerView;
    private ReviewAdapter mAdapter;

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
    }

    @Override
    protected void afterViewsInitialized() {
        mAdapter = new ReviewAdapter(getActivity());
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

            alertDialog.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            DBMethods.connect(getActivity());
                            DBMethods.delete(DBSchema.Reviews.TableName, null);
                            fragmentReset();
                            DBMethods.disconnect(null);
                        }
                    });
            alertDialog.setNegativeButton("No",
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
    protected void hide() {

    }

    @Override
    public void workSomething(int work_id) {

        if (work_id == 1) mAdapter.reset();
    }
}
