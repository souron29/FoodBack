package Settings;


import com.dexlabs.extraloyaljuice.R;

import GeneralCode.AdvanceSettingsFragment;

public class SettingsFragment extends AdvanceSettingsFragment {
    @Override
    public void onResume() {
        super.onResume();
        processAllSettings();
    }

    @Override
    protected int setSettingsResource() {
        return R.xml.preferences;
    }


    @Override
    protected void fragmentReset() {

    }
}
