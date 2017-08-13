package Feedback;


import android.content.ContentValues;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.dexlabs.extraloyaljuice.R;

import GeneralCode.AdvanceFragment;
import GeneralCode.CommonFunctions;
import database.DBMethods;
import database.DBSchema;

public class feedback_form extends AdvanceFragment {

    RatingBar ambiance, food, service;

    Button save_button;
    EditText suggestion;

    long customer_id;
    RatingBar.OnRatingBarChangeListener ratingListener = new RatingBar.OnRatingBarChangeListener() {
        @SuppressWarnings("deprecation")
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            if ((int) rating <= 2) {
                LayerDrawable layerDrawable = (LayerDrawable) ratingBar.getProgressDrawable();
                if (Build.VERSION.SDK_INT >= 23) {
                    layerDrawable.getDrawable(1).setTint(getActivity().getColor(R.color.rating_low));
                    layerDrawable.getDrawable(2).setTint(getActivity().getColor(R.color.rating_low));
                } else {
                    layerDrawable.getDrawable(1).setTint(getResources().getColor(R.color.rating_low));
                    layerDrawable.getDrawable(2).setTint(getResources().getColor(R.color.rating_low));
                }
            } else if ((int) rating <= 4) {
                LayerDrawable layerDrawable = (LayerDrawable) ratingBar.getProgressDrawable();
                if (Build.VERSION.SDK_INT >= 23) {
                    layerDrawable.getDrawable(1).setTint(getActivity().getColor(R.color.rating_medium));
                    layerDrawable.getDrawable(2).setTint(getActivity().getColor(R.color.rating_medium));
                } else {
                    layerDrawable.getDrawable(1).setTint(getResources().getColor(R.color.rating_medium));
                    layerDrawable.getDrawable(2).setTint(getResources().getColor(R.color.rating_medium));
                }
            } else {
                LayerDrawable layerDrawable = (LayerDrawable) ratingBar.getProgressDrawable();
                if (Build.VERSION.SDK_INT >= 23) {
                    layerDrawable.getDrawable(1).setTint(getActivity().getColor(R.color.rating_high));
                    layerDrawable.getDrawable(2).setTint(getActivity().getColor(R.color.rating_high));
                } else {
                    layerDrawable.getDrawable(1).setTint(getResources().getColor(R.color.rating_high));
                    layerDrawable.getDrawable(2).setTint(getResources().getColor(R.color.rating_high));
                }
            }
        }
    };

    public feedback_form() {
        // Required empty public constructor
    }

    private void save() {
        if (preSave()) {
            float ambiance_rating = ambiance.getRating();
            float food_rating = food.getRating();
            float service_rating = service.getRating();

            String feedback = suggestion.getText().toString();
            ContentValues cv = new ContentValues();
            cv.put(DBSchema.Reviews.CUSTOMER_ID, customer_id);
            cv.put(DBSchema.Reviews.AMBIANCE_RATING, ambiance_rating);
            cv.put(DBSchema.Reviews.FOOD_RATING, food_rating);
            cv.put(DBSchema.Reviews.SERVICE_RATING, service_rating);
            cv.put(DBSchema.Reviews.SUGGESTION, feedback);
            cv.put(DBSchema.Reviews.CREATION_DATE, CommonFunctions.getCurrentDate());
            DBMethods.connect(getActivity());
            DBMethods.insert(DBSchema.Reviews.TableName, cv);
            DBMethods.disconnect(null);


            getChildFragmentManager().beginTransaction().remove(this).commit();
            ((AdvanceFragment) getParentFragment()).fragmentReset();
        }
    }

    private boolean preSave() {
        Toast toast;
        if (ambiance.getRating() == 0 || food.getRating() == 0 || service.getRating() == 0) {
            toast = Toast.makeText(getActivity(), "Kindly review all the categories", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.START | Gravity.TOP, CommonFunctions.getDimensions(service, CommonFunctions.LEFT), CommonFunctions.getDimensions(service, CommonFunctions.BOTTOM));
            toast.show();
            return false;
        }

        return true;
    }

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
        return R.layout.fragment_feedback_form;
    }

    @Override
    protected void initializeViews() {
        ambiance = $(R.id.rating_ambiance);
        food = $(R.id.rating_food);
        service = $(R.id.rating_service);
        suggestion = $(R.id.edittext_feedback_form_suggestion);
        save_button = $(R.id.button_feedback_form_save);
    }

    @Override
    protected void afterViewsInitialized() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            customer_id = arguments.getLong("customer_id");
        }
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
        ambiance.setOnRatingBarChangeListener(ratingListener);
        food.setOnRatingBarChangeListener(ratingListener);
        service.setOnRatingBarChangeListener(ratingListener);
    }

    @Override
    protected void hide() {

    }

}
