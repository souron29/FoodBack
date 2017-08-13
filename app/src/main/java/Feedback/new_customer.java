package Feedback;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.dexlabs.extraloyaljuice.R;

import java.util.Calendar;

import GeneralCode.AdvanceFragment;
import GeneralCode.CommonFunctions;
import GeneralCode.CommonVariables;
import database.DBMethods;
import database.DBSchema;

public class new_customer extends AdvanceFragment {

    private Button datepick, save;
    private EditText edit_cust_name;
    private EditText edit_cust_phone;
    /*View view;*/


    public new_customer() {
        // Required empty public constructor
    }


    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_customer, container, false);

        init(view);
        initArgs(getArguments());
        return view;
    }*/

    /*private void initArgs(Bundle args) {
        if (args != null) {
            String number = args.getString("number");
            edit_cust_phone.setText(number);
        }
    }*/

    /*private void init(View view) {
        datepick = view.findViewById(R.id.feedback_form_button_date);
        save = view.findViewById(R.id.feedback_form_button_save);

        edit_cust_name = view.findViewById(R.id.feedback_cust_name_edit);
        edit_cust_phone = view.findViewById(R.id.feedback_cust_ph_edit);
        datepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPreSave())
                    save();
            }
        });
    }*/

    private boolean onPreSave() {
        String name = edit_cust_name.getText().toString();
        String number = edit_cust_phone.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter the Name of Customer", Toast.LENGTH_LONG).show();
            return false;
        }
        if (number.isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter the Phone Number of Customer", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!CommonFunctions.isPhoneNumber(number)) {
            Toast.makeText(getActivity(), "Please Enter a valid Phone Number", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!CommonVariables.dateset) {
            Toast.makeText(getActivity(), "Please set your Birth Date", Toast.LENGTH_LONG).show();
            return false;
        }
        if (Integer.valueOf(CommonVariables.currentYear) - CommonVariables.BYear < 15) {
            Toast.makeText(getActivity(), "You sure you are THAT young!! Adults only!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    void save() {
        String name = edit_cust_name.getText().toString();
        String number = CommonFunctions.clipString(edit_cust_phone.getText().toString(), new String[]{"+91"});
        ContentValues cv = new ContentValues();
        cv.put("customer_name", name);
        cv.put("customer_number", number);
        cv.put("customer_birthday", CommonVariables.createBirthDate());
        DBMethods.connect(getActivity());
        DBMethods.insert(DBSchema.Customers.TableName, cv);
        DBMethods.disconnect(null);

        getChildFragmentManager().beginTransaction().remove(this).commit();
        ((feedback_main) getParentFragment()).startFeedbackForm(number);

    }

    /*@Override
    public void BackPressReset() {
        ((feedback_main) getParentFragment()).reset();
    }*/

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
        return 0;
    }

    @Override
    public void fragmentReset() {
        /*((feedback_main) getParentFragment()).reset();*/
    }

    @Override
    protected int setFragmentLayoutID() {
        return R.layout.fragment_new_customer;
    }

    @Override
    protected void initializeViews() {
        datepick = $(R.id.feedback_form_button_date);
        save = $(R.id.feedback_form_button_save);

        edit_cust_name = $(R.id.feedback_cust_name_edit);
        edit_cust_phone = $(R.id.feedback_cust_ph_edit);
    }

    @Override
    protected void afterViewsInitialized() {
        Bundle args = getArguments();
        if (args != null) {
            String number = args.getString("number");
            edit_cust_phone.setText(number);
        }
        datepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
                ((DatePickerFragment) newFragment).setDateButton(datepick);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPreSave())
                    save();
            }
        });
    }

    @Override
    protected void hide() {

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        Button date;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            return dialog;
        }

        public void setDateButton(Button date) {
            this.date = date;
        }

        public void onDateSet(DatePicker view, int syear, int smonth, int sday) {
            CommonVariables.BDay = sday;
            CommonVariables.BMonth = smonth + 1;
            CommonVariables.BYear = syear;
            CommonVariables.dateset = true;
            this.date.setText(CommonVariables.createBirthDate());
        }
    }


}
