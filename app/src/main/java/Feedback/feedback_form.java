package Feedback;


import android.content.ContentValues;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.dexlabs.foodback.R;

import GeneralCode.AdvanceActivity;
import GeneralCode.AdvanceFragment;
import GeneralCode.AdvanceFunctions;
import GeneralCode.CommonFunctions;
import database.DBMethods;
import database.DBSchema;

public class feedback_form extends AdvanceFragment {

    RatingBar ambiance, food, service;

    Button save_button;
    TextInputEditText suggestion;
    TextInputLayout wrapper;
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
                if (ambiance.getRating() < 3 || service.getRating() < 3 || food.getRating() < 3) {
                    wrapper.setHint(AdvanceFunctions.getResource(getActivity(), "string", "ff_suggestion_poor").toString());
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
                if (ambiance.getRating() > 2 && service.getRating() > 2 && food.getRating() > 2) {
                    wrapper.setHint(AdvanceFunctions.getResource(getActivity(), "string", "ff_suggestion_okay").toString());
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
                if (ambiance.getRating() > 2 && service.getRating() > 2 && food.getRating() > 2) {
                    wrapper.setHint(AdvanceFunctions.getResource(getActivity(), "string", "ff_suggestion_okay").toString());
                }
            }
        }
    };

    @Override
    protected int setFragmentLayoutID() {
        return R.layout.fragment_feedback_form;
    }

    @Override
    protected void initializeViews() {
        ambiance = $(R.id.rating_ambiance);
        food = $(R.id.rating_food);
        service = $(R.id.rating_service);
        suggestion = $(R.id.et_feedback_form_suggestion);
        save_button = $(R.id.bt_feedback_form_save);
        wrapper = $(R.id.til_feedback_form);
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
        ((AdvanceActivity) getActivity()).show();
    }

    @Override
    protected void hide() {

    }
    /*@Override
    public boolean onBackPressed(Fragment parent) {
        return false;
    }*/
}
