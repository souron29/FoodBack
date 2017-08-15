package GeneralCode;

import android.content.Context;

import CustomerList.CustomerDriver;
import Review.ReviewDriver;

public class AsyncFunctions {
    private Context mContext;

    public AsyncFunctions(Context context) {
        this.mContext = context;

    }

    public void loadCustomers(final OnGetWorkListener fragment) {
        AdvanceFunctions.loader loader = new AdvanceFunctions.loader(mContext) {
            @Override
            protected void onStart() {
                fragment.workSomething(OnGetWorkListener.RESET);
                CustomerDriver.createCustomerList(mContext);
            }

            @Override
            protected void onFinish() {
                fragment.workSomething(OnGetWorkListener.RESTART);
            }

            @Override
            protected void onReset() {
                fragment.workSomething(OnGetWorkListener.RESTART);
            }
        };
        loader.start(1);
    }

    public void loadReviews(final OnGetWorkListener fragment) {
        AdvanceFunctions.loader loader = new AdvanceFunctions.loader(mContext) {
            @Override
            protected void onStart() {
                fragment.workSomething(OnGetWorkListener.RESET);
                ReviewDriver.createReviewList(mContext);
            }

            @Override
            protected void onFinish() {
                fragment.workSomething(OnGetWorkListener.RESTART);
            }

            @Override
            protected void onReset() {
                fragment.workSomething(OnGetWorkListener.RESTART);
            }
        };
        loader.start(2);
    }
}
