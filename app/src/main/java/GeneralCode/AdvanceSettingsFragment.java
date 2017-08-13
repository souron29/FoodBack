package GeneralCode;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.preference.TwoStatePreference;
import android.support.annotation.Nullable;

import java.util.Map;
import java.util.Objects;

import Settings.KeyVariables;

public abstract class AdvanceSettingsFragment extends PreferenceFragment implements OnBackPressListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private FragmentManager parentFM, childFM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(setSettingsResource());

    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        onSharedPreferenceChanged(getPreferenceScreen().getSharedPreferences(), null);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Change the app based on settings
        ((AdvanceActivity) getActivity()).loadSettings(getPreferenceScreen().getSharedPreferences());
        // Set Summary of all settings programmatically
        processSettings(key);
    }

    @Override
    public boolean onBackPressed(Fragment frag) {
        setFragmentManagers();
        ((AdvanceActivity) getActivity()).resetParentFragment();
        parentFM.popBackStackImmediate();
        return true;

    }

    @Override
    public void Reset() {
        fragmentReset();
    }

    private int getFragIndexAtBAckStack(FragmentManager fm, AdvanceSettingsFragment fragment) {
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

    protected abstract int setSettingsResource();

    protected void processAllSettings() {
        Map<String, ?> allEntries = getPreferenceScreen().getSharedPreferences().getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            processSettings(entry.getKey());
        }
    }

    void processSettings(String key) {

        DialogPreference dialogPreference = null;
        TwoStatePreference switchPref = null;
        boolean dialogSetting = false, switchSetting = false;
        try {
            dialogPreference = (DialogPreference) findPreference(key);
            dialogSetting = true;
        } catch (ClassCastException e) {
            dialogSetting = false;
            try {
                switchPref = (TwoStatePreference) findPreference(key);
                switchSetting = true;
            } catch (ClassCastException e1) {
                switchSetting = false;
            }
        }
        if (dialogSetting) {

            if (dialogPreference instanceof EditTextPreference) {
                // For edit text preferences
                if (((EditTextPreference) dialogPreference).getText() != null) {
                    // to ensure we don't get a null value
                    // set first value by default
                    int inputType = ((EditTextPreference) dialogPreference).getEditText().getInputType();
                    // check for password fields
                    if (CommonFunctions.isPassword(inputType)) {
                        if (((EditTextPreference) dialogPreference).getText().isEmpty())
                            dialogPreference.setSummary("");
                        else dialogPreference.setSummary("Set");
                    } else
                        dialogPreference.setSummary(((EditTextPreference) dialogPreference).getText());
                }
            }
        } else if (switchSetting) {
            if (Objects.equals(key, KeyVariables.KEY_PIN_ENABLED)) {
                if (switchPref.isChecked())
                    findPreference(KeyVariables.KEY_PIN_CODE).setEnabled(true);
                else findPreference(KeyVariables.KEY_PIN_CODE).setEnabled(false);

            }
        }
    }

    protected abstract void fragmentReset();

}
