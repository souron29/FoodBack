package GeneralCode;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import Settings.KeyVariables;

public abstract class AdvanceActivity<F extends Fragment> extends AppCompatActivity {

    private AdvanceFragment currentNavFragment = null;
    private AdvanceSettingsFragment currentOptionsFragment = null;
    private Context mContext;

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
        mContext = this;
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
                    Log.i("TAG", "loadParentFragment: ");
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

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle("Pin Code");
            final EditText input = new EditText(this);
            input.setHint("Enter Pin Code");
            input.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            alertBuilder.setView(input);
            alertBuilder.setCancelable(false);
            alertBuilder.setPositiveButton("Check", null/*
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            int pin;
                            try {
                                pin = Integer.valueOf(pref.getString(KeyVariables.KEY_PIN_CODE, "0"));
                                if (pin != 0) {
                                    if (pin == Integer.valueOf(input.getText().toString())) r.run();
                                }
                            } catch (NumberFormatException e) {
                                AdvanceFunctions.shortToast(mContext, "Please Enter Numeric Code");
                            }
                        }
                    }*/);
            alertBuilder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            AlertDialog al = alertBuilder.create();
            al.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(final DialogInterface dialog) {
                    Log.i("TAG", "onShow: ");
                    Button pb = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                    pb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.i("TAG", "onClick: ");
                            int pin;
                            try {
                                pin = Integer.valueOf(pref.getString(KeyVariables.KEY_PIN_CODE, "0"));
                                if (pin != 0) {

                                    if (pin == Integer.valueOf(input.getText().toString())) r.run();
                                    dialog.dismiss();
                                }
                            } catch (NumberFormatException e) {
                                AdvanceFunctions.shortToast(mContext, "Please Enter Numeric Code");
                                input.setText("");
                                Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                                long[] pattern = {0, 3000, 3000};
                                v.vibrate(pattern, -1);
                            }
                        }
                    });
                }
            });
            al.show();
        } else r.run();

    }

    protected <T extends View> T $(int view_id) {
        return (T) findViewById(view_id);
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