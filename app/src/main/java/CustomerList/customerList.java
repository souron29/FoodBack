package CustomerList;


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

public class customerList extends AdvanceFragment implements OnGetWorkListener {
    /*View view;*/
    RecyclerView mRecyclerView;
    CustomerAdapter mAdapter;

    public customerList() {
        // Required empty public constructor
    }


   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_customer_list, container, false);
        init(view);
        mAdapter = new CustomerAdapter(getActivity());

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        AsyncFunctions async = new AsyncFunctions(getActivity());
        async.loadCustomers(this);
        return view;
    }*/

    /*private void init(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_customer_list);
    }*/

    @Override
    public void workSomething(int work_id) {
        if (work_id == 1) mAdapter.reset();
    }

    /*@Override
    protected FragmentManager setParentFragManager() {
        return getFragmentManager();
    }

    @Override
    protected FragmentManager setChildFragManager() {
        return getChildFragmentManager();
    }*/

    @Override
    protected int setOptionsMenu() {
        return R.menu.menu_customer_list;
    }

    @Override
    public void fragmentReset() {
        AsyncFunctions asyncFunctions = new AsyncFunctions(getActivity());
        asyncFunctions.loadCustomers(this);
        mAdapter.restart();
    }

    @Override
    protected int setFragmentLayoutID() {
        return R.layout.fragment_customer_list;
    }

    @Override
    protected void initializeViews() {
        mRecyclerView = $(R.id.recycler_view_customer_list);
    }

    @Override
    protected void afterViewsInitialized() {
        mAdapter = new CustomerAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        AsyncFunctions async = new AsyncFunctions(getActivity());
        async.loadCustomers(this);

    }

    @Override
    protected void hide() {

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
                            DBMethods.delete(DBSchema.Customers.TableName, null);
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
}
