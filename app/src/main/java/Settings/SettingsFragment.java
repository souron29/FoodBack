package Settings;


import com.dexlabs.foodback.R;

import GeneralCode.AdvanceActivity;
import GeneralCode.AdvanceSettingsFragment;

public class SettingsFragment extends AdvanceSettingsFragment {
    @Override
    public void onResume() {
        super.onResume();
        processAllSettings();
        ((AdvanceActivity) getActivity()).hide();
        String temp = $("string", "action_settings");
        ((AdvanceActivity) getActivity()).getSupportActionBar().setTitle(temp);
    }

    @Override
    protected int setSettingsResource() {
        return R.xml.preferences;
    }


    @Override
    protected void fragmentReset() {

    }

    @Override
    public void onPause() {
        super.onPause();
        ((AdvanceActivity) getActivity()).show();
    }
}
