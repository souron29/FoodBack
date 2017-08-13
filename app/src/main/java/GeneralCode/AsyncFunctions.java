package GeneralCode;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.dexlabs.extraloyaljuice.MainActivity;

import CustomerList.CustomerDriver;
import Review.ReviewDriver;

public class AsyncFunctions {
    private Context mContext;
    private LoaderManager lm;

    public AsyncFunctions(Context context) {
        this.mContext = context;
        lm = ((MainActivity) mContext).getLoaderManager();
    }

    public void loadCustomers(Fragment fragment) {

        final Fragment frag = fragment;
        LoaderManager.LoaderCallbacks<Object> customer_list_loader = new LoaderManager.LoaderCallbacks<Object>() {

            @Override
            public Loader<Object> onCreateLoader(int i, Bundle bundle) {
                ((OnGetWorkListener) frag).workSomething(0);
                return new AsyncTaskLoader<Object>(mContext) {
                    @Override
                    public Object loadInBackground() {
                        CustomerDriver.createCustomerList(mContext);
                        return 1;
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<Object> loader, Object o) {
                ((OnGetWorkListener) frag).workSomething(1);
            }

            @Override
            public void onLoaderReset(Loader<Object> loader) {
                ((OnGetWorkListener) frag).workSomething(0);
            }
        };
        lm.restartLoader(1, null, customer_list_loader).forceLoad();
    }

    public void loadReviews(AdvanceFragment fragment) {
        final Fragment frag = fragment;
        LoaderManager.LoaderCallbacks<Object> review_list_loader = new LoaderManager.LoaderCallbacks<Object>() {

            @Override
            public Loader<Object> onCreateLoader(int i, Bundle bundle) {
                ((OnGetWorkListener) frag).workSomething(0);
                return new AsyncTaskLoader<Object>(mContext) {
                    @Override
                    public Object loadInBackground() {
                        ReviewDriver.createReviewList(mContext);
                        return 1;
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<Object> loader, Object o) {
                ((OnGetWorkListener) frag).workSomething(1);
            }

            @Override
            public void onLoaderReset(Loader<Object> loader) {
                ((OnGetWorkListener) frag).workSomething(0);
            }
        };
        lm.restartLoader(2, null, review_list_loader).forceLoad();
    }
}
