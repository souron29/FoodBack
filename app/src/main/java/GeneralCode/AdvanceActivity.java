package GeneralCode;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import java.util.Objects;

import Settings.KeyVariables;

public abstract class AdvanceActivity<F extends Fragment> extends AppCompatActivity {

    private AdvanceFragment currentNavFragment = null;
    private AdvanceSettingsFragment currentOptionsFragment = null;

    public AdvanceActivity() {
    }

    void resetParentFragment() {

        if (currentNavFragment != null) {
            currentNavFragment.fragmentReset();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());

        ViewInitialization();
        init();
        AfterViewCreated();
    }

    private void init() {
        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);

        loadSettings(sharedPref);
    }

    protected void loadParentFragment(final F frag, final int container_id, final boolean removePrevious) {
        if (removePrevious) {
            if (currentNavFragment != null) {

                if (!Objects.equals(currentNavFragment.getClass().getSimpleName(), frag.getClass().getSimpleName())) {
                    getFragmentManager().beginTransaction().remove(currentNavFragment).commit();
                    if (currentOptionsFragment != null)
                        getFragmentManager().beginTransaction().remove(currentOptionsFragment).commit();
                    currentOptionsFragment = null;
                    getFragmentManager().beginTransaction().add(container_id, frag, frag.getClass().getSimpleName()).addToBackStack(frag.getClass().getSimpleName()).commit();
                } else if (currentOptionsFragment != null) {
                    getFragmentManager().beginTransaction().remove(currentOptionsFragment).commit();
                    currentOptionsFragment = null;
                    getFragmentManager().beginTransaction().add(container_id, frag, frag.getClass().getSimpleName()).addToBackStack(frag.getClass().getSimpleName()).commit();
                }
            } else {// first time fragment load
                if (currentOptionsFragment != null)
                    getFragmentManager().beginTransaction().remove(currentOptionsFragment).commit();
                getFragmentManager().beginTransaction().replace(container_id, frag, frag.getClass().getSimpleName()).addToBackStack(frag.getClass().getSimpleName()).commit();
            }
            currentNavFragment = (AdvanceFragment) frag;
        } else {
            if (currentOptionsFragment != null) {
                if (!Objects.equals(currentOptionsFragment.getClass().getSimpleName(), frag.getClass().getSimpleName())) {

                    getFragmentManager().beginTransaction().add(container_id, frag, frag.getClass().getSimpleName()).addToBackStack(frag.getClass().getSimpleName()).commit();
                }
            } else {
                // first time fragment load
                getFragmentManager().beginTransaction().replace(container_id, frag, frag.getClass().getSimpleName()).addToBackStack(frag.getClass().getSimpleName()).commit();
            }
            currentOptionsFragment = (AdvanceSettingsFragment) frag;
        }
    }

    protected void advanceBackPress() {
        if (currentNavFragment == null) super.onBackPressed();
        /*if (fragStack.isEmpty()) super.onBackPressed();*/
        else {
            if (currentOptionsFragment != null) {
                currentOptionsFragment.onBackPressed(null);
                currentOptionsFragment = null;
            } else if (!currentNavFragment.onBackPressed(null)) {

                super.onBackPressed();

            }
        }

    }

    protected void checkLock(final Runnable r) {
        final SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(this);
        if (pref.getBoolean(KeyVariables.KEY_PIN_ENABLED, false)) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Pin Code");
            final EditText input = new EditText(this);
            input.setHint("Enter Pin Code");
            input.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            alertDialog.setView(input);
            alertDialog.setPositiveButton("Check",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            int pin = Integer.valueOf(pref.getString(KeyVariables.KEY_PIN_CODE, "0"));
                            if (pin != 0) {
                                if (pin == Integer.valueOf(input.getText().toString())) r.run();
                            }
                        }
                    });
            alertDialog.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            alertDialog.show();

        } else r.run();

    }

    protected <T extends View> T $(int view_id) {
        return findViewById(view_id);
    }

    protected abstract int setLayout();

    protected abstract void ViewInitialization();

    protected abstract void AfterViewCreated();

    protected abstract void loadSettings(SharedPreferences sharedPref);

    public void hide() {
    }

    public void show() {
    }

    protected <T extends Object> T $(String resourceType, String resourceName) {
        return AdvanceFunctions.getResource(this, resourceType, resourceName);
    }
}