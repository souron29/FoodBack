package GeneralCode;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class AdvanceFragment extends Fragment implements OnBackPressListener {
    FragmentManager parentFM, childFM;
    int view_id, menu_id = 0;
    private View fragmentView;

    public View getFragmentView() {
        return fragmentView;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view_id = setFragmentLayoutID();
        fragmentView = inflater.inflate(view_id, container, false);
        menu_id = setOptionsMenu();
        if (menu_id != 0) {
            setHasOptionsMenu(true);
        }

        initializeViews();
        afterViewsInitialized();
        return fragmentView;
    }

    @Override
    public void Reset() {
        fragmentReset();
    }

    @Override
    public boolean onBackPressed(Fragment parent) {
        setFragmentManagers();
        int childCount = childFM != null ? childFM.getBackStackEntryCount() : 0;
        int siblingCount = parentFM != null ? parentFM.getBackStackEntryCount() : 0;

        if (childCount == 0) {
            if (siblingCount == 0) return false;
            else {
                int currentIndex = getFragIndexAtBAckStack(parentFM, this);
                if (currentIndex < parentFM.getBackStackEntryCount() - 1) {
                    AdvanceFragment siblingFrag;
                    siblingFrag = (AdvanceFragment) parentFM.findFragmentByTag(parentFM.getBackStackEntryAt(currentIndex + 1).getName());
                    return siblingFrag.onBackPressed(this);

                } else {

                    this.fragmentReset();
                    parentFM.popBackStackImmediate();
                    if (parent != null)
                        ((OnBackPressListener) parent).Reset();
                    return true;
                }

            }

        } else {
            AdvanceFragment childFrag = (AdvanceFragment) childFM.findFragmentByTag(childFM.getBackStackEntryAt(0).getName());
            if (!childFrag.onBackPressed(this)) {
                childFM.popBackStackImmediate();
                ((AdvanceFragment) childFM.findFragmentByTag(childFM.getBackStackEntryAt(childFM.getBackStackEntryCount() - 1).getName())).fragmentReset();
            }
            return true;
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(menu_id, menu);

    }

    /*public boolean onBackPressed(AdvanceFragment prevFragmnet) {
        setFragmentManagers();
        int childCount = childFM != null ? childFM.getBackStackEntryCount() : 0;
        int siblingCount = parentFM != null ? parentFM.getBackStackEntryCount() : 0;

        if (childCount == 0) {
            if (siblingCount == 0) return false;
            else {
                int currentIndex = getFragIndexAtBAckStack(parentFM, this);
                if (currentIndex < parentFM.getBackStackEntryCount() - 1) {
                    AdvanceFragment siblingFrag;
                    siblingFrag = (AdvanceFragment) parentFM.findFragmentByTag(parentFM.getBackStackEntryAt(currentIndex + 1).getName());
                    return siblingFrag.onBackPressed(this);

                } else {

                    this.fragmentReset();
                    parentFM.popBackStackImmediate();
                    if (prevFragmnet != null)
                        prevFragmnet.fragmentReset();
                    return true;
                }

            }

        } else {
            AdvanceFragment childFrag = (AdvanceFragment) childFM.findFragmentByTag(childFM.getBackStackEntryAt(0).getName());
            if (!childFrag.onBackPressed(this)) {
                childFM.popBackStackImmediate();
                ((AdvanceFragment) childFM.findFragmentByTag(childFM.getBackStackEntryAt(childFM.getBackStackEntryCount() - 1).getName())).fragmentReset();
            }
            return true;
        }

    }*/

    private int getFragIndexAtBAckStack(FragmentManager fm, AdvanceFragment fragment) {
        String fragmentName = fragment.getClass().getSimpleName();
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            if (fm.getBackStackEntryAt(i).getName().equalsIgnoreCase(fragmentName)) {
                return i;
            }
        }
        return 0;
    }

    private void setFragmentManagers() {
        this.parentFM = this.getFragmentManager();
        this.childFM = this.getChildFragmentManager();
    }

    protected abstract int setFragmentLayoutID();

    protected abstract void initializeViews();

    protected abstract void afterViewsInitialized();

    protected abstract int setOptionsMenu();

    public abstract void fragmentReset();

    protected abstract void hide();

    protected <T extends View> T $(int view_id) {
        return fragmentView.findViewById(view_id);
    }
}