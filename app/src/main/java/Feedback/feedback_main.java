package Feedback;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dexlabs.foodback.R;

import GeneralCode.AdvanceFragment;
import database.DBMethods;

public class feedback_main extends AdvanceFragment {

    Button button_feedback_form;
    View view;
    long customer_id;
    private Fragment frag;

    public feedback_main() {
        // Required empty public constructor
    }


    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        button_feedback_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Customer Number");
                final EditText input = new EditText(getActivity());
                input.setHint("Enter Phone Number");
                input.setInputType(InputType.TYPE_CLASS_PHONE);
                alertDialog.setView(input);
                alertDialog.setPositiveButton("Check",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DBMethods.connect(getActivity());
                                customer_id = DBMethods.checkNumber(input.getText().toString());
                                DBMethods.disconnect(null);
                                if (customer_id >= 0) {
                                    frag = new feedback_form();
                                    Bundle args = new Bundle();
                                    args.putLong("customer_id", customer_id);
                                    frag.setArguments(args);
                                    getChildFragmentManager().beginTransaction().replace(R.id.feedback_main_container, frag, "feedback_form").addToBackStack("feedback_form").commit();
                                    button_feedback_form.setVisibility(View.GONE);
                                } else {
                                    // New Customer Found
                                    String number = input.getText().toString();
                                    frag = new new_customer();
                                    Bundle args = new Bundle();
                                    args.putString("number", number);
                                    frag.setArguments(args);
                                    getChildFragmentManager().beginTransaction().replace(R.id.feedback_main_container, frag, "feedback_new_customer").addToBackStack("feedback_new_customer").commit();
                                    button_feedback_form.setVisibility(View.GONE);
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

                alertDialog.setView(input);

            }
        });
        return view;
    }*/

    @Override
    protected void initializeViews() {
        button_feedback_form = $(R.id.feedback_main_button_feed); /*view.findViewById()*/
    }

    @Override
    protected void afterViewsInitialized() {
        button_feedback_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Customer Number");
                final EditText input = new EditText(getActivity());
                input.setHint("Enter Phone Number");
                input.setInputType(InputType.TYPE_CLASS_PHONE);
                alertDialog.setView(input);
                alertDialog.setPositiveButton("Check",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DBMethods.connect(getActivity());
                                customer_id = DBMethods.checkNumber(input.getText().toString());
                                DBMethods.disconnect(null);
                                if (customer_id >= 0) {
                                    frag = new feedback_form();
                                    Bundle args = new Bundle();
                                    args.putLong("customer_id", customer_id);
                                    frag.setArguments(args);
                                    getChildFragmentManager().beginTransaction().replace(R.id.feedback_main_container, frag, "feedback_form").addToBackStack("feedback_form").commit();
                                    hide();
                                } else {
                                    // New Customer Found
                                    String number = input.getText().toString();
                                    frag = new new_customer();
                                    Bundle args = new Bundle();
                                    args.putString("number", number);
                                    frag.setArguments(args);
                                    getChildFragmentManager().beginTransaction().replace(R.id.feedback_main_container, frag, "feedback_new_customer").commit();
                                    hide();
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

                /*alertDialog.setView(input);*/

            }
        });
    }

    @Override
    protected void hide() {
        button_feedback_form.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    void startFeedbackForm(String number) {
        Fragment fragForm = new feedback_form();
        DBMethods.connect(getActivity());
        customer_id = DBMethods.checkNumber(number);
        DBMethods.connect(getActivity());
        if (customer_id > -1) {
            Bundle args = new Bundle();
            args.putLong("customer_id", customer_id);
            fragForm.setArguments(args);
        }
        getChildFragmentManager().beginTransaction().replace(R.id.feedback_main_container, fragForm, "feedback_form").addToBackStack("feedback_form").commit();
    }

    @Override
    protected int setOptionsMenu() {
        return 0;
    }

    @Override
    public void fragmentReset() {
        button_feedback_form.setVisibility(View.VISIBLE);
    }

    @Override
    protected int setFragmentLayoutID() {
        return R.layout.fragment_feedback_main;
    }

}
